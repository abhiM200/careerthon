package com.careerthon.controller;

import com.careerthon.repository.ProfileReviewRepository;
import com.careerthon.repository.ResumeReviewRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProfileReviewRepository profileReviewRepository;
    private final ResumeReviewRepository resumeReviewRepository;

    public AdminController(ProfileReviewRepository profileReviewRepository, ResumeReviewRepository resumeReviewRepository) {
        this.profileReviewRepository = profileReviewRepository;
        this.resumeReviewRepository = resumeReviewRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("reviews", profileReviewRepository.findAll());
        model.addAttribute("resumes", resumeReviewRepository.findAllByOrderByUploadedAtDesc());
        return "admin/dashboard";
    }

    @org.springframework.web.bind.annotation.PostMapping("/profile/suggest")
    public String suggestProfile(@org.springframework.web.bind.annotation.RequestParam Long id, 
                                 @org.springframework.web.bind.annotation.RequestParam String suggestions) {
        profileReviewRepository.findById(id).ifPresent(review -> {
            review.setAdminSuggestions(suggestions);
            profileReviewRepository.save(review);
        });
        return "redirect:/admin/dashboard?success=true";
    }

    @org.springframework.web.bind.annotation.PostMapping("/resume/suggest")
    public String suggestResume(@org.springframework.web.bind.annotation.RequestParam Long id, 
                                @org.springframework.web.bind.annotation.RequestParam String suggestions) {
        resumeReviewRepository.findById(id).ifPresent(resume -> {
            resume.setAdminSuggestions(suggestions);
            resumeReviewRepository.save(resume);
        });
        return "redirect:/admin/dashboard?success=true";
    }
}
