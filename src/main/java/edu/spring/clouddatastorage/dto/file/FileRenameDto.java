package edu.spring.clouddatastorage.dto.file;

public record FileRenameDto(String oldName,
                            String newName,
                            String path,
                            Long userId,
                            Boolean isFolder) {
}
