package com.careerthon.controller;

import com.careerthon.repository.UserStoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserStoryRepository userStoryRepository;

    public HomeController(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("testimonials", userStoryRepository.findAll());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("team", userStoryRepository.findAll());
        return "about";
    }
}
