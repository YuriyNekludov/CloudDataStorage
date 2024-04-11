package edu.spring.clouddatastorage.service;

import edu.spring.clouddatastorage.dto.folder.FolderCreateDto;
import edu.spring.clouddatastorage.dto.UserDtoResponse;

public interface FolderService {

    void createUserRootFolder(UserDtoResponse userDto);

    void createNewFolder(FolderCreateDto folderDto);
}
