package edu.spring.clouddatastorage.dto.file;

import lombok.Builder;

@Builder
public record FileDtoResponse(String fileName,
                              String path,
                              Boolean isFolder,
                              String lastModified,
                              String url,
                              Long size) {
}
