package edu.spring.clouddatastorage.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserDtoResponse(Long id,
                              String username) implements Serializable {
}
