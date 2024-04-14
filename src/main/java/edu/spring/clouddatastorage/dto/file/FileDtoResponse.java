package edu.spring.clouddatastorage.dto.file;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record FileDtoResponse(String fileName,
                              String path,
                              Boolean isFolder,
                              String lastModified,
                              String url,
                              Long size) implements Serializable {
}
