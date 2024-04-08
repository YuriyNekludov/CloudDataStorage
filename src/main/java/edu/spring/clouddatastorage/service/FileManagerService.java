package edu.spring.clouddatastorage.service;

import edu.spring.clouddatastorage.config.props.MinioProperties;
import edu.spring.clouddatastorage.model.User;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileManagerService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public void createDefaultFolderForUser(User user) {
        var defaultFolderName = "user-" + user.getId() + "-files";
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(defaultFolderName)
                    .stream(new ByteArrayInputStream(new byte[] {}), 0, -1)
                    .build());
            log.info("New folder {} for user {} was created successfully", defaultFolderName, user.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        }
    }
}
