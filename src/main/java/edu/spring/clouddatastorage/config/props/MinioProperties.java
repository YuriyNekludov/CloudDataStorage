package edu.spring.clouddatastorage.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String bucket;
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
