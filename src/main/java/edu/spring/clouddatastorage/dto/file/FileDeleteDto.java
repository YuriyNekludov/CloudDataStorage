package edu.spring.clouddatastorage.dto.file;

import lombok.Builder;

@Builder
public record FileDeleteDto(String fileName,
                            String path,
                            Long userId,
                            Boolean isFolder) {
}
