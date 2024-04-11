package edu.spring.clouddatastorage.dto.folder;

import lombok.Builder;

@Builder
public record FolderDto(String path,
                        Long userId) {
}
