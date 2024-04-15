package edu.spring.clouddatastorage.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileRenameDto {

    private String oldName;
    private String newName;
    private String path;
    private Long userId;
    private Boolean isFolder;
}
