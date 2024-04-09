package edu.spring.clouddatastorage.dto;

import lombok.Builder;

@Builder
public record FileDtoResponse(String fileName,
                              Boolean isFolder) {
}
