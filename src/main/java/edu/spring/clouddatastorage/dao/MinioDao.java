package edu.spring.clouddatastorage.dao;

import edu.spring.clouddatastorage.dto.file.FileDtoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinioDao {

    List<FileDtoResponse> getFiles(String folder);

    void deleteFolder(String deletePath);

    void deleteFile(String deletePath);

    void createFolder(String folderName);

    void uploadFile(String pathForUpload, MultipartFile[] files);

    void renameFile(String pathForRename, String pathWithNewName);

    void renameFolder(String pathForRename, String pathWithNewName);

    FileDtoResponse getFile(String folderName, String fileFullName, String path);

    List<FileDtoResponse> searchFiles(String folderName);
}
