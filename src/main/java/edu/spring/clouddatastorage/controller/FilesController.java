package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.dto.file.FileDtoRequest;
import edu.spring.clouddatastorage.service.FileManagerService;
import edu.spring.clouddatastorage.util.ControllerHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yaml.snakeyaml.util.UriEncoder;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilesController {

    private final FileManagerService fileManagerService;

    @GetMapping()
    public String getFile(@RequestParam("name") String fileName,
                          @RequestParam(value = "path", required = false) String path,
                          @RequestParam(value = "searchParam", required = false) String searchParam,
                          RedirectAttributes redirectAttributes,
                          Authentication authentication) {
        var userDto = ControllerHelper.getUSerDtoFromAuthentication(authentication);
        var fileDto = FileDtoRequest.builder()
                .path(path)
                .fileName(fileName)
                .userId(userDto.id())
                .build();
        redirectAttributes.addFlashAttribute("fileDto", fileManagerService.getFile(fileDto));
        if (searchParam == null)
            return ControllerHelper.generateRedirectUrl(path);
        searchParam = UriEncoder.encode(searchParam);
        return "redirect:/search?fileName=" + searchParam;
    }
}
