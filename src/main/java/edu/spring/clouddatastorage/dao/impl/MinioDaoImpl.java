package edu.spring.clouddatastorage.dao.impl;

import edu.spring.clouddatastorage.config.props.MinioProperties;
import edu.spring.clouddatastorage.dao.MinioDao;
import edu.spring.clouddatastorage.dto.file.FileDtoResponse;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.SnowballObject;
import io.minio.UploadSnowballObjectsArgs;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class MinioDaoImpl implements MinioDao {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public List<FileDtoResponse> getFiles(String folder) {
        try {
            Iterable<Result<Item>> result = getListObjects(folder);
            if (!result.iterator().hasNext()) {
                return emptyList();
            } else {
                List<FileDtoResponse> files = new ArrayList<>();
                for (Result<Item> itemResult : result) {
                    var item = itemResult.get();
                    if (item.objectName().equals(folder))
                        continue;
                    var file = parseItemIntoFileDtoResponse(item, folder);
                    files.add(file);
                }
                return files;
            }
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }

    @Override
    public FileDtoResponse getFile(String folderName, String fileFullName, String path) {
        try {
            Iterable<Result<Item>> result = getListObjects(folderName);
            if (!result.iterator().hasNext()) {
                return null;
            }
            for (Result<Item> itemResult : result) {
                var item = itemResult.get();
                if (item.objectName().equals(fileFullName)) {
                    FileDtoResponse.FileDtoResponseBuilder builder = FileDtoResponse.builder();
                    builder.size(item.size());
                    builder.lastModified(String.valueOf(item.lastModified()));
                    builder.fileName(fileFullName.substring(fileFullName.lastIndexOf("/") + 1));
                    builder.url(
                            minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(minioProperties.getBucket())
                                    .object(item.objectName())
                                    .expiry(2, TimeUnit.HOURS)
                                    .build())
                    );
                    if (path != null)
                        builder.path(path);
                    return builder.build();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
        return null;
    }

    @Override
    public List<FileDtoResponse> searchFiles(String folderName) {
        return List.of();
    }

    @Override
    public void deleteFolder(String deletePath) {
        try {
            Iterable<Result<Item>> results = getListObjects(deletePath);
            for (Result<Item> result : results) {
                Item item = result.get();
                var objectName = item.objectName();
                if (item.isDir()) {
                    deleteFolder(objectName);
                    continue;
                }
                deleteFile(objectName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }

    @Override
    public void deleteFile(String deletePath) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(deletePath)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }

    @Override
    public void createFolder(String folderName) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(folderName + "/")
                    .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }

    @Override
    public void uploadFile(String pathForUpload, MultipartFile[] files) {
        try {
            List<SnowballObject> objects = new ArrayList<>();
            for (MultipartFile file : files) {
                objects.add(new SnowballObject(pathForUpload + file.getOriginalFilename(),
                        file.getInputStream(), file.getSize(), null));
            }
            minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .objects(objects)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }

    @Override
    public void renameFile(String pathForRename, String pathWithNewName) {
        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(pathWithNewName)
                    .source(CopySource.builder()
                            .bucket(minioProperties.getBucket())
                            .object(pathForRename)
                            .build())
                    .build());
            deleteFile(pathForRename);
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }

    @Override
    public void renameFolder(String pathForRename, String pathWithNewName) {
        try {
            Iterable<Result<Item>> results = getListObjects(pathForRename);
            for (Result<Item> result : results) {
                Item item = result.get();
                var objectName = item.objectName();
                var newName = objectName.replace(pathForRename, pathWithNewName);
                if (item.isDir())
                    renameFolder(objectName, newName);
                else
                    renameFile(objectName, newName);
            }
            deleteFolder(pathForRename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Iterable<Result<Item>> getListObjects(String path) {
        return minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(minioProperties.getBucket())
                .prefix(path)
                .build());
    }

    private FileDtoResponse parseItemIntoFileDtoResponse(Item item, String defaultFolderName) {
        var itemName = item.objectName().substring(defaultFolderName.length());
        if (item.isDir())
            return FileDtoResponse.builder()
                    .fileName(itemName.substring(0, itemName.length() - 1))
                    .isFolder(true)
                    .build();
        return FileDtoResponse.builder()
                .fileName(itemName)
                .isFolder(false)
                .build();
    }

    private List<FileDtoResponse> deepSearchFiles(String folderName, String fileName) {
        return null;
    }
}
