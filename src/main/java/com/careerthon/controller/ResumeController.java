package com.careerthon.controller;

import com.careerthon.model.ResumeReview;
import com.careerthon.repository.ResumeReviewRepository;
import com.careerthon.service.ResumeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeReviewRepository resumeReviewRepository;

    public ResumeController(ResumeService resumeService, ResumeReviewRepository resumeReviewRepository) {
        this.resumeService = resumeService;
        this.resumeReviewRepository = resumeReviewRepository;
    }

    @GetMapping
    public String resumeHome() {
        return "resume/index";
    }

    @PostMapping("/upload")
    public String uploadResume(@RequestParam("resume") MultipartFile file, 
                              @RequestParam("userName") String userName,
                              @RequestParam("userEmail") String userEmail,
                              Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload.");
            return "resume/index";
        }
        
        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown_file";
        
        // Simulating text extraction from file for the ATS analysis
        String simulatedContent = "User with background in " + fileName.toLowerCase();
        if (fileName.toLowerCase().contains("java")) simulatedContent += " coding java agile scrum stakeholder management";
        if (fileName.toLowerCase().contains("it")) simulatedContent += " project management p&l client management";
        
        ResumeReview review = resumeService.analyzeResume(fileName, userName, userEmail, simulatedContent);
        return "redirect:/resume/results/" + review.getId();
    }

    @GetMapping("/results/{id}")
    public String results(@PathVariable Long id, Model model) {
        ResumeReview review = resumeReviewRepository.findById(id).orElse(null);
        if (review == null) return "redirect:/resume";
        model.addAttribute("review", review);
        return "resume/results";
    }

    @GetMapping("/templates")
    public String templates() {
        return "resume/templates";
    }
}
