package edu.spring.clouddatastorage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping()
public class MainPageController {

    @GetMapping()
    public String mainPage(@RequestParam(value = "path", required = false) String path) {
        return "main_page";
    }
}
