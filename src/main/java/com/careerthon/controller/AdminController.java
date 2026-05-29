package com.careerthon.controller;

import com.careerthon.model.Job;
import com.careerthon.repository.ProfileReviewRepository;
import com.careerthon.repository.ResumeReviewRepository;
import com.careerthon.repository.JobRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProfileReviewRepository profileReviewRepository;
    private final ResumeReviewRepository resumeReviewRepository;
    private final JobRepository jobRepository;

    public AdminController(ProfileReviewRepository profileReviewRepository, 
                           ResumeReviewRepository resumeReviewRepository,
                           JobRepository jobRepository) {
        this.profileReviewRepository = profileReviewRepository;
        this.resumeReviewRepository = resumeReviewRepository;
        this.jobRepository = jobRepository;
    }

    @GetMapping("/dashboard")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public String dashboard(Model model) {
        model.addAttribute("reviews", profileReviewRepository.findAll());
        model.addAttribute("resumes", resumeReviewRepository.findAllByOrderByUploadedAtDesc());
        model.addAttribute("jobs", jobRepository.findAllByOrderByCreatedAtDesc());
        return "admin/dashboard";
    }

    @PostMapping("/profile/suggest")
    public String suggestProfile(@RequestParam Long id, @RequestParam String suggestions) {
        profileReviewRepository.findById(id).ifPresent(review -> {
            review.setAdminSuggestions(suggestions);
            profileReviewRepository.save(review);
        });
        return "redirect:/admin/dashboard?success=true";
    }

    @PostMapping("/resume/suggest")
    public String suggestResume(@RequestParam Long id, @RequestParam String suggestions) {
        resumeReviewRepository.findById(id).ifPresent(resume -> {
            resume.setAdminSuggestions(suggestions);
            resumeReviewRepository.save(resume);
        });
        return "redirect:/admin/dashboard?success=true";
    }

    @PostMapping("/job/add")
    public String addJob(@RequestParam String title,
                         @RequestParam String commitment,
                         @RequestParam String location,
                         @RequestParam String description) {
        Job job = new Job(title, commitment, location, description);
        jobRepository.save(job);
        return "redirect:/admin/dashboard?success=true";
    }

    @PostMapping("/job/delete")
    public String deleteJob(@RequestParam Long id) {
        jobRepository.deleteById(id);
        return "redirect:/admin/dashboard?success=true";
    }
}
