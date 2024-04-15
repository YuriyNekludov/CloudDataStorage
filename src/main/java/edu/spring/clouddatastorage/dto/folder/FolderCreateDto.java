package edu.spring.clouddatastorage.dto.folder;

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
public class FolderCreateDto {

    private String path;
    private String folderName;
    private Long userId;
}
