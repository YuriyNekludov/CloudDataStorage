package edu.spring.clouddatastorage.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileUploadDto {

    private MultipartFile[] file;
    private MultipartFile[] folder;
    private String path;
    private Long userId;
}
