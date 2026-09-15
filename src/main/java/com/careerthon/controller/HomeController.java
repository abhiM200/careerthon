package com.careerthon.controller;

import com.careerthon.model.UserStory;
import com.careerthon.repository.UserStoryRepository;
import com.careerthon.service.SpecExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SuppressWarnings("null")
public class HomeController {

    private final UserStoryRepository userStoryRepository;
    private final SpecExportService specExportService;
    private final com.careerthon.repository.JobRepository jobRepository;
    private final com.careerthon.repository.ProfileReviewRepository profileReviewRepository;
    private final com.careerthon.repository.ResumeReviewRepository resumeReviewRepository;

    public HomeController(UserStoryRepository userStoryRepository, 
                          SpecExportService specExportService, 
                          com.careerthon.repository.JobRepository jobRepository,
                          com.careerthon.repository.ProfileReviewRepository profileReviewRepository,
                          com.careerthon.repository.ResumeReviewRepository resumeReviewRepository) {
        this.userStoryRepository = userStoryRepository;
        this.specExportService = specExportService;
        this.jobRepository = jobRepository;
        this.profileReviewRepository = profileReviewRepository;
        this.resumeReviewRepository = resumeReviewRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<UserStory> allStories = userStoryRepository.findAll();
        model.addAttribute("testimonials", allStories.stream().filter(this::isTestimonial).collect(Collectors.toList()));
        
        long totalProfiles = profileReviewRepository.count();
        long totalResumes = resumeReviewRepository.count();
        long totalTransformed = totalProfiles + totalResumes;
        
        model.addAttribute("totalProfiles", totalProfiles);
        model.addAttribute("totalResumes", totalResumes);
        model.addAttribute("totalTransformed", totalTransformed);
        
        // Latest real profile review if available
        var latestReviewOpt = profileReviewRepository.findFirstByStatusOrderByCreatedAtDesc(com.careerthon.model.ProfileReview.ReviewStatus.COMPLETED);
        model.addAttribute("latestReview", latestReviewOpt.orElse(null));
        
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        List<UserStory> allStories = userStoryRepository.findAll();
        model.addAttribute("team", allStories.stream().filter(this::isTeamMember).collect(Collectors.toList()));
        return "about";
    }

    @GetMapping("/careers")
    public String careers(Model model) {
        model.addAttribute("jobs", jobRepository.findAllByOrderByCreatedAtDesc());
        return "careers";
    }

    @GetMapping("/features")
    public String features() {
        return "features";
    }

    @GetMapping("/developer/export-spec")
    public ResponseEntity<byte[]> exportSpec() throws IOException {
        byte[] docx = specExportService.generateMasterSpec();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=careerthon_master_spec.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(docx);
    }

    private boolean isTeamMember(UserStory story) {
        return java.util.List.of("Abhishek Mishra", "Altamash Mallick", "Piyush Kumar").contains(story.getName());
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
        return "redirect:/student/dashboard";
    }
}
