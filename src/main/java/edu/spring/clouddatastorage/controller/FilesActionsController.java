package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.dto.file.FileDeleteDto;
import edu.spring.clouddatastorage.dto.folder.FolderCreateDto;
import edu.spring.clouddatastorage.service.FileManagerService;
import edu.spring.clouddatastorage.service.FolderService;
import edu.spring.clouddatastorage.util.ControllerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping()
public class FilesActionsController {

    private final FolderService folderService;
    private final FileManagerService fileManagerService;

    @PostMapping("/new-folder")
    public String createNewFolder(@ModelAttribute FolderCreateDto folderDto) {
        folderService.createNewFolder(folderDto);
        return ControllerHelper.generateRedirectUrl(folderDto.path());
    }

    @DeleteMapping("/delete")
    public String deleteFile(@ModelAttribute FileDeleteDto fileDto) {
        fileManagerService.delete(fileDto);
        return ControllerHelper.generateRedirectUrl(fileDto.path());
    }
}
