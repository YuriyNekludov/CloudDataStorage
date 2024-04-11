package edu.spring.clouddatastorage.service.impl;

import edu.spring.clouddatastorage.dao.MinioDao;
import edu.spring.clouddatastorage.dto.file.FileDeleteDto;
import edu.spring.clouddatastorage.dto.file.FileDtoResponse;
import edu.spring.clouddatastorage.dto.folder.FolderDto;
import edu.spring.clouddatastorage.service.FileManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileManagerServiceImpl implements FileManagerService {

    private final MinioDao minioDao;

    @Override
    public List<FileDtoResponse> getFiles(FolderDto folderDto) {
        String folderName = folderDto.path() == null || folderDto.path().isEmpty() ? String.format("user-%d-files/", folderDto.userId())
                : String.format("user-%d-files/%s/", folderDto.userId(), folderDto.path());
        return minioDao.getFilesFromFolder(folderName);
    }

    @Override
    public void delete(FileDeleteDto fileDto) {
        String fileName = fileDto.fileName();
        String deletePath;
        if (fileDto.path() == null || fileDto.path().isEmpty()) {
            deletePath = fileDto.isFolder() ? String.format("user-%d-files/%s/", fileDto.userId(), fileName)
                    : String.format("user-%d-files/%s", fileDto.userId(), fileName);
            minioDao.deleteFolder(deletePath);
        } else {
            deletePath = fileDto.isFolder() ? String.format("user-%d-files/%s/%s/", fileDto.userId(), fileDto.path(), fileName)
                    : String.format("user-%d-files/%s/%s", fileDto.userId(), fileDto.path(), fileName);
            minioDao.deleteFile(deletePath);
        }
    }
}
