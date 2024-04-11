package edu.spring.clouddatastorage.dto;

import lombok.Builder;

@Builder
public record UserDtoResponse(Long id,
                              String username) {
}
