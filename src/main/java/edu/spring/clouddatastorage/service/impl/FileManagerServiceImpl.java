package edu.spring.clouddatastorage.service.impl;

import edu.spring.clouddatastorage.dao.MinioDao;
import edu.spring.clouddatastorage.dto.file.FileDeleteDto;
import edu.spring.clouddatastorage.dto.file.FileDtoRequest;
import edu.spring.clouddatastorage.dto.file.FileDtoResponse;
import edu.spring.clouddatastorage.dto.file.FileRenameDto;
import edu.spring.clouddatastorage.dto.file.FileUploadDto;
import edu.spring.clouddatastorage.dto.folder.FolderDto;
import edu.spring.clouddatastorage.service.FileManagerService;
import edu.spring.clouddatastorage.util.StringFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileManagerServiceImpl implements FileManagerService {

    private final MinioDao minioDao;

    @Override
    public List<FileDtoResponse> getFiles(FolderDto folderDto) {
        var folderName = StringFormatUtil.formatPathToFiles(folderDto.path(), folderDto.userId());
        return minioDao.findFiles(folderName);
    }

    @Override
    public FileDtoResponse getFile(FileDtoRequest fileDto) {
        var fileFullName = StringFormatUtil.formatPathForFileOperation(
                fileDto.path(),
                fileDto.fileName(),
                fileDto.userId());
        var folderName = StringFormatUtil.formatPathToFiles(fileDto.path(), fileDto.userId());
        return minioDao.findFile(folderName, fileFullName, fileDto.path());
    }

    @Override
    public List<FileDtoResponse> search(FileDtoRequest fileDto) {
        var rootFolder = StringFormatUtil.getUserRootFolderName(fileDto.userId());
        return minioDao.searchFiles(fileDto.fileName(), rootFolder);
    }

    @Override
    public void delete(FileDeleteDto fileDto) {
            var deletePath = StringFormatUtil.formatPathForDeleteFiles(
                    fileDto.getPath(),
                    fileDto.getFileName(),
                    fileDto.getUserId()
            );
            minioDao.deleteFile(deletePath);
    }

    @Override
    public void upload(FileUploadDto fileDto) {
        var pathForUpload = StringFormatUtil.formatPathToFiles(fileDto.getPath(), fileDto.getUserId());
        if (fileDto.getFolder() == null) {
            minioDao.uploadFile(pathForUpload, fileDto.getFile());
        } else {
            minioDao.uploadFile(pathForUpload, fileDto.getFolder());
        }
    }

    @Override
    public void rename(FileRenameDto fileDto) {
        var pathForRename = StringFormatUtil.formatPathForFileOperation(
                fileDto.getPath(),
                fileDto.getOldName(),
                fileDto.getUserId());
        var newName = StringFormatUtil.getNewFileNameWithExtension(
                fileDto.getOldName(),
                fileDto.getNewName());
        var pathWithNewName = StringFormatUtil.formatPathForFileOperation(
                fileDto.getPath(),
                newName,
                fileDto.getUserId());
        var pathToFile = StringFormatUtil.formatPathToFiles(
                fileDto.getPath(),
                fileDto.getUserId()
        );
        minioDao.renameFile(pathForRename, pathWithNewName, pathToFile);
    }
}
