package edu.spring.clouddatastorage.dto.file;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadDto(MultipartFile[] file,
                            MultipartFile[] folder,
                            String path,
                            Long userId) {
}
