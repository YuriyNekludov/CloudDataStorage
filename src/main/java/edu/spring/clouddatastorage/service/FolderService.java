package edu.spring.clouddatastorage.service;

import edu.spring.clouddatastorage.dto.file.FileDeleteDto;
import edu.spring.clouddatastorage.dto.file.FileRenameDto;
import edu.spring.clouddatastorage.dto.folder.FolderCreateDto;
import edu.spring.clouddatastorage.dto.UserDtoResponse;

public interface FolderService {

    void createUserRootFolder(UserDtoResponse userDto);

    void create(FolderCreateDto folderDto);

    void delete(FileDeleteDto fileDto);

    void rename(FileRenameDto fileDto);
}
