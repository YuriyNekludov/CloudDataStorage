package edu.spring.clouddatastorage.util;

import edu.spring.clouddatastorage.dto.file.FileDtoResponse;
import io.minio.messages.Item;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.StringJoiner;

@UtilityClass
public class ItemParser {

    private final int BYTES_PER_KILOBYTE = 1024;

    public FileDtoResponse parseItemIntoFileDtoResponse(Item item, String folderName) {
        var itemName = item.objectName().substring(folderName.length());
        if (item.isDir())
            return FileDtoResponse.builder()
                    .fileName(itemName.substring(0, itemName.length() - 1))
                    .isFolder(true)
                    .build();
        return FileDtoResponse.builder()
                .fileName(itemName)
                .isFolder(false)
                .path(getPathToFile(null, item.objectName()))
                .build();
    }

    public FileDtoResponse parseItemIntoFileDtoResponseForView(Item item, String url, String path) {
        var fileName = item.objectName();
        var size = BigDecimal.valueOf(item.size());
        size = size.divide(BigDecimal.valueOf(BYTES_PER_KILOBYTE), RoundingMode.HALF_UP);
        return FileDtoResponse.builder()
                .size(size.longValue())
                .lastModified(item.lastModified()
                        .format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH)))
                .fileName(fileName.substring(fileName.lastIndexOf("/") + 1))
                .url(url)
                .path(getPathToFile(path, fileName))
                .build();
    }

    private String getPathToFile(String path, String fileName) {
        if (path != null)
            return path;
        var pathElement = fileName.split("/");
        var sj = new StringJoiner("/");
        for (int i = 0; i < pathElement.length; i++) {
            if (i == 0 || i == pathElement.length - 1)
                continue;
            sj.add(pathElement[i]);
        }
        return sj.toString();
    }
}
