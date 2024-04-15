package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.dto.file.FileDeleteDto;
import edu.spring.clouddatastorage.dto.file.FileRenameDto;
import edu.spring.clouddatastorage.dto.file.FileUploadDto;
import edu.spring.clouddatastorage.dto.folder.FolderCreateDto;
import edu.spring.clouddatastorage.service.FileManagerService;
import edu.spring.clouddatastorage.service.FolderService;
import edu.spring.clouddatastorage.util.ControllerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping()
public class FilesActionsController {

    private final FolderService folderService;
    private final FileManagerService fileManagerService;

    @PostMapping("/new-folder")
    public String createNewFolder(@ModelAttribute FolderCreateDto folderDto,
                                  Authentication authentication) {
        var userDto = ControllerHelper.getUSerDtoFromAuthentication(authentication);
        folderDto.setUserId(userDto.id());
        folderService.create(folderDto);
        return ControllerHelper.generateRedirectUrl(folderDto.getPath());
    }

    @PostMapping("/upload")
    public String uploadFiles(@ModelAttribute FileUploadDto fileDto,
                              Authentication authentication) {
        var userDto = ControllerHelper.getUSerDtoFromAuthentication(authentication);
        fileDto.setUserId(userDto.id());
        fileManagerService.upload(fileDto);
        return ControllerHelper.generateRedirectUrl(fileDto.getPath());
    }

    @DeleteMapping("/delete")
    public String deleteFile(@ModelAttribute FileDeleteDto fileDto,
                             Authentication authentication) {
        var userDto = ControllerHelper.getUSerDtoFromAuthentication(authentication);
        fileDto.setUserId(userDto.id());
        if (fileDto.getIsFolder())
            folderService.delete(fileDto);
        else
            fileManagerService.delete(fileDto);
        return ControllerHelper.generateRedirectUrl(fileDto.getPath());
    }

    @PatchMapping("/rename")
    public String renameFile(@ModelAttribute FileRenameDto fileDto,
                             Authentication authentication) {
        var userDto = ControllerHelper.getUSerDtoFromAuthentication(authentication);
        fileDto.setUserId(userDto.id());
        if (fileDto.getIsFolder())
            folderService.rename(fileDto);
        else
            fileManagerService.rename(fileDto);
        return ControllerHelper.generateRedirectUrl(fileDto.getPath());
    }
}
