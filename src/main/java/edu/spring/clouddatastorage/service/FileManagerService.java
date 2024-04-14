package edu.spring.clouddatastorage.service;

import edu.spring.clouddatastorage.dto.file.FileDeleteDto;
import edu.spring.clouddatastorage.dto.file.FileDtoRequest;
import edu.spring.clouddatastorage.dto.file.FileDtoResponse;
import edu.spring.clouddatastorage.dto.file.FileRenameDto;
import edu.spring.clouddatastorage.dto.file.FileUploadDto;
import edu.spring.clouddatastorage.dto.folder.FolderDto;

import java.util.List;

public interface FileManagerService {

    List<FileDtoResponse> getFiles(FolderDto folderDto);

    void delete(FileDeleteDto fileDto);

    void upload(FileUploadDto fileDto);

    void rename(FileRenameDto fileDto);

    FileDtoResponse getFile(FileDtoRequest fileDto);

    List<FileDtoResponse> search(FileDtoRequest fileDto);
}
