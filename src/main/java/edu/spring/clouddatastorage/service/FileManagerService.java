package edu.spring.clouddatastorage.service;

import edu.spring.clouddatastorage.dto.FileDtoResponse;

import java.util.List;

public interface FileManagerService {

    void createNewFolder(String folderName);

    List<FileDtoResponse> getAllUsersFiles(Long id);
}
