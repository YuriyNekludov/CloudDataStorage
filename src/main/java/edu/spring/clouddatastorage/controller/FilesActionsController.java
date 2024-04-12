package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.dto.file.FileDeleteDto;
import edu.spring.clouddatastorage.dto.file.FileDtoRequest;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping()
public class FilesActionsController {

    private final FolderService folderService;
    private final FileManagerService fileManagerService;

    @PostMapping("/new-folder")
    public String createNewFolder(@ModelAttribute FolderCreateDto folderDto) {
        folderService.create(folderDto);
        return ControllerHelper.generateRedirectUrl(folderDto.path());
    }

    @PostMapping("/upload")
    public String uploadFiles(@ModelAttribute FileUploadDto fileDto) {
        fileManagerService.upload(fileDto);
        return ControllerHelper.generateRedirectUrl(fileDto.path());
    }

    @DeleteMapping("/delete")
    public String deleteFile(@ModelAttribute FileDeleteDto fileDto) {
        if (fileDto.isFolder())
            folderService.delete(fileDto);
        else
            fileManagerService.delete(fileDto);
        return ControllerHelper.generateRedirectUrl(fileDto.path());
    }

    @PatchMapping("/rename")
    public String renameFile(@ModelAttribute FileRenameDto fileDto) {
        if (fileDto.isFolder())
            folderService.rename(fileDto);
        else
            fileManagerService.rename(fileDto);
        return ControllerHelper.generateRedirectUrl(fileDto.path());
    }

    @GetMapping("/files")
    public String getFile(@RequestParam("name") String fileName,
                          @RequestParam(value = "path", required = false) String path,
                          RedirectAttributes redirectAttributes,
                          Authentication authentication) {
        var userDto = ControllerHelper.getUSerDtoFromAuthentication(authentication);
        var fileDto = FileDtoRequest.builder()
                .path(path)
                .fileName(fileName)
                .userId(userDto.id())
                .build();
        redirectAttributes.addFlashAttribute("fileDto", fileManagerService.getFile(fileDto));
        return ControllerHelper.generateRedirectUrl(path);
    }
}
