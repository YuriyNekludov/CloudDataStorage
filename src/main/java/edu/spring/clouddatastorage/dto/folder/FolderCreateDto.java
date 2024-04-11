package edu.spring.clouddatastorage.dto.folder;

import lombok.Builder;

@Builder
public record FolderCreateDto(String path,
                              String folderName,
                              Long userId) {
}
