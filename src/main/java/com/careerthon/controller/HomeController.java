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
        List<UserStory> allStories = userStoryRepository.findAll();
        model.addAttribute("testimonials", allStories.stream().filter(this::isTestimonial).collect(Collectors.toList()));
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        List<UserStory> allStories = userStoryRepository.findAll();
        model.addAttribute("team", allStories.stream().filter(this::isTeamMember).collect(Collectors.toList()));
        return "about";
    }

    private boolean isTeamMember(UserStory story) {
        String name = story.getName();
        return "Abhishek Mishra".equals(name) || "Priyanshu Shekhar".equals(name) || "Altamash Mallick".equals(name);
    }

    private boolean isTestimonial(UserStory story) {
        return !isTeamMember(story);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/profile")
    public String profile() {
        // For now, redirect to home or show a simple profile
        return "index";
    }
}
