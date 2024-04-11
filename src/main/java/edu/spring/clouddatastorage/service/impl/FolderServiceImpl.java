package edu.spring.clouddatastorage.service.impl;

import edu.spring.clouddatastorage.config.props.MinioProperties;
import edu.spring.clouddatastorage.dto.folder.FolderCreateDto;
import edu.spring.clouddatastorage.dto.UserDtoResponse;
import edu.spring.clouddatastorage.service.FolderService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Log4j2
@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public void createUserRootFolder(UserDtoResponse userDto) {
        var userRootFolderName = String.format("user-%d-files", userDto.id());
        createFolder(userRootFolderName);
        log.info("Folder {} for user {} created successfully", userRootFolderName, userDto.id());
    }

    @Override
    public void createNewFolder(FolderCreateDto folderDto) {
        String newFolderName;
        if (folderDto.path() == null || folderDto.path().isEmpty()) {
            newFolderName = String.format("user-%d-files/%s/", folderDto.userId(), folderDto.folderName());
        } else
            newFolderName = String.format("user-%d-files/%s/%s/",
                    folderDto.userId(),
                    folderDto.path(),
                    folderDto.folderName());
        createFolder(newFolderName);
    }

    private void createFolder(String folderName) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(folderName + "/")
                    .stream(new ByteArrayInputStream(new byte[] {}), 0, -1)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }
}
