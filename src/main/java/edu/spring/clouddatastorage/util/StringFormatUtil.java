package edu.spring.clouddatastorage.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringFormatUtil {

    public String getPathOrFolderNameForFiles(String path, Long id) {
        var rootFolder = getUserRootFolderName(id);
        return path == null || path.isEmpty()
                ? rootFolder + '/'
                : String.format("%s/%s/", rootFolder, path);
    }

    public String getPathForRenameOrGettingFile(String path, String fileName, Long id) {
        return getPathOrFolderNameForFiles(path, id) + fileName;
    }

    public String getPathForDeleteFiles(String path, String fileName, Long id) {
        var rootFolder = getUserRootFolderName(id);
        return path == null || path.isEmpty()
                ? String.format("%s/%s", rootFolder, fileName)
                : String.format("%s/%s/%s", rootFolder, path, fileName);
    }

    public String getNewFileName(String oldName, String newName) {
        var index = oldName.lastIndexOf('.');
        return newName + oldName.substring(index);
    }

    public String getUserRootFolderName(Long id) {
        return String.format("user-%d-files", id);
    }

    public String getPathOrNameForFolders(String path, String folderName, Long id) {
        var rootFolder = getUserRootFolderName(id);
        return path == null || path.isEmpty()
                ? String.format("%s/%s/", rootFolder, folderName)
                : String.format("%s/%s/%s/", rootFolder, path, folderName);
    }
}
