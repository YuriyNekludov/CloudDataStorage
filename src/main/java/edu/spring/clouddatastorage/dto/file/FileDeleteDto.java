package edu.spring.clouddatastorage.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDeleteDto {

    private String fileName;
    private String path;
    private Long userId;
    private Boolean isFolder;
}
