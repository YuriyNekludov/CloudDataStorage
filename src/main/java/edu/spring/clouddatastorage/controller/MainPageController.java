package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.service.FileManagerService;
import lombok.RequiredArgsConstructor;
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
                           Model model) {
        var userId = (Long) model.getAttribute("userId");
        if (path == null) {
            model.addAttribute("userFiles", fileManagerService.getAllUsersFiles(userId));
        }
        return "main_page";
    }
}
