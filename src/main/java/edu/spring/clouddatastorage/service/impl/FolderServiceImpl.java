package edu.spring.clouddatastorage.service.impl;

import edu.spring.clouddatastorage.dao.MinioDao;
import edu.spring.clouddatastorage.dto.file.FileDeleteDto;
import edu.spring.clouddatastorage.dto.file.FileRenameDto;
import edu.spring.clouddatastorage.dto.folder.FolderCreateDto;
import edu.spring.clouddatastorage.dto.UserDtoResponse;
import edu.spring.clouddatastorage.service.FolderService;
import edu.spring.clouddatastorage.util.StringFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final MinioDao minioDao;

    @Override
    public void createUserRootFolder(UserDtoResponse userDto) {
        var userRootFolderName = StringFormatUtil.getUserRootFolderName(userDto.id());
        minioDao.createFolder(userRootFolderName);
    }

    @Override
    public void create(FolderCreateDto folderDto) {
        String newFolderName = StringFormatUtil.getPathOrNameForFolders(
                folderDto.path(),
                folderDto.folderName(),
                folderDto.userId()
        );
        minioDao.createFolder(newFolderName);
    }

    @Override
    public void delete(FileDeleteDto fileDto) {
        var deletePath = StringFormatUtil.getPathOrNameForFolders(
                fileDto.path(),
                fileDto.fileName(),
                fileDto.userId()
        );
        minioDao.deleteFolder(deletePath);
    }

    @Override
    public void rename(FileRenameDto fileDto) {
        var pathForRename = StringFormatUtil.getPathOrNameForFolders(
                fileDto.path(),
                fileDto.oldName(),
                fileDto.userId()
        );
        var pathWithNewName = StringFormatUtil.getPathOrNameForFolders(
                fileDto.path(),
                fileDto.newName(),
                fileDto.userId()
        );
        minioDao.renameFolder(pathForRename, pathWithNewName);
    }
}
