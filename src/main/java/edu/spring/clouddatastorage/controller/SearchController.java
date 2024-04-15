package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.dto.file.FileDtoRequest;
import edu.spring.clouddatastorage.exception.EmptySearchQueryException;
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
    public String search(@RequestParam("fileName") String fileName,
                         Model model,
                         Authentication authentication) {
        if (fileName.isBlank())
            throw new EmptySearchQueryException("Поле для поиска файлов не должно быть пустым.");
        var userDto = ControllerHelper.getUSerDtoFromAuthentication(authentication);
        var fileDto = FileDtoRequest.builder()
                .fileName(fileName)
                .userId(userDto.id())
                .build();
        model.addAttribute("files", fileManagerService.search(fileDto));
        model.addAttribute("userDto", userDto);
        model.addAttribute("fileName", fileName);
        return "search";
    }
}
