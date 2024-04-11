package edu.spring.clouddatastorage.dao;

import edu.spring.clouddatastorage.config.props.MinioProperties;
import edu.spring.clouddatastorage.dto.file.FileDtoResponse;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class MinioDao {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public List<FileDtoResponse> getFilesFromFolder(String folder) {
        try {
            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .prefix(folder)
                    .build());
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

    public void deleteFolder(String deletePath) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .prefix(deletePath)
                    .build());
            for (Result<Item> result : results) {
                Item item = result.get();
                String objectName = item.objectName();
                deleteFile(objectName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }

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
}
