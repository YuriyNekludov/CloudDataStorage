package edu.spring.clouddatastorage.dto.file;

import lombok.Builder;

@Builder
public record FileDtoRequest(String path,
                             String fileName,
                             Long userId) {
}
