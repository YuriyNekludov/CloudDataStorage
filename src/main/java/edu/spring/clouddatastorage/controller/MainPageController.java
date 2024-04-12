package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.dto.UserDtoResponse;
import edu.spring.clouddatastorage.dto.folder.FolderDto;
import edu.spring.clouddatastorage.service.FileManagerService;
import edu.spring.clouddatastorage.util.ControllerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping()
public class MainPageController {

    private final FileManagerService fileManagerService;

    @GetMapping()
    public String mainPage(@RequestParam(value = "path", required = false) String path,
                           Model model,
                           Authentication authentication) {
        var userDto = ControllerHelper.getUSerDtoFromAuthentication(authentication);
        model.addAttribute("userDto", userDto);
        var folderDto = FolderDto.builder()
                .path(path)
                .userId(userDto.id())
                .build();
        model.addAttribute("userFiles", fileManagerService.getFiles(folderDto));
        if (path != null) {
            model.addAttribute("path", path);
            model.addAttribute("navigationPath", ControllerHelper.parsePathForNavigation(path));
        }
        return "main_page";
    }
}
