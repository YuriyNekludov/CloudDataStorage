package edu.spring.clouddatastorage.service.impl;

import edu.spring.clouddatastorage.config.props.MinioProperties;
import edu.spring.clouddatastorage.dto.FileDtoResponse;
import edu.spring.clouddatastorage.service.FileManagerService;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileManagerServiceImpl implements FileManagerService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public void createNewFolder(String folderName) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(folderName)
                    .stream(new ByteArrayInputStream(new byte[] {}), 0, -1)
                    .build());
            log.info("New folder {} was created successfully", folderName);
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }

    public List<FileDtoResponse> getAllUsersFiles(Long id) {
        var defaultFolder = String.format("user-%d-files", id);
        try {
            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .prefix(defaultFolder)
                    .build());
            if (!result.iterator().hasNext()) {
                return emptyList();
            } else {
                List<FileDtoResponse> files = new ArrayList<>();
                for (Result<Item> itemResult : result) {
                    var item = itemResult.get();
                    var file = parseItemIntoFileDtoResponse(item);
                    files.add(file);
                }
                return files;
            }
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }

    private FileDtoResponse parseItemIntoFileDtoResponse(Item item) {
        var itemName = item.objectName();
        if (itemName.contains("/")) {
            var rootFolderName = itemName.split("/")[0];
            return FileDtoResponse.builder()
                    .fileName(rootFolderName)
                    .isFolder(true)
                    .build();
        } else return FileDtoResponse.builder()
                .fileName(itemName)
                .isFolder(false)
                .build();
    }
}
