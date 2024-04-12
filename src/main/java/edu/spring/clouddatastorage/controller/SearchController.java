package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.dto.file.FileDtoRequest;
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
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final FileManagerService fileManagerService;

    @GetMapping()
    public String search(@RequestParam("fileName") String fileName, Model model, Authentication authentication) {
        var userDto = ControllerHelper.getUSerDtoFromAuthentication(authentication);
        var fileDto = FileDtoRequest.builder()
                .fileName(fileName)
                .build();
        model.addAttribute("files", fileManagerService.search(fileDto));
        return "search";
    }
}
