package com.careerthon.controller;

import com.careerthon.model.ResumeReview;
import com.careerthon.repository.ResumeReviewRepository;
import com.careerthon.service.ResumeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

        try {
            ResumeReview review = resumeService.analyzeResume(file, userName, userEmail);
            return "redirect:/resume/results/" + review.getId();
        } catch (Exception e) {
            model.addAttribute("error", "Failed to analyze resume: " + e.getMessage());
            return "resume/index";
        }
    }

    @GetMapping("/results/{id}")
    public String results(@PathVariable("id") Long id, Model model) {
        ResumeReview review = resumeReviewRepository.findById(id).orElse(null);
        if (review == null) return "redirect:/resume";
        model.addAttribute("review", review);
        return "resume/results";
    }

    @GetMapping("/templates")
    public String templates() {
        return "resume/templates";
    }

    @GetMapping("/download/{type}")
    public ResponseEntity<byte[]> downloadTemplate(@PathVariable String type) {
        byte[] pdfContent = resumeService.generateTemplatePdf(type);
        String filename = type.equalsIgnoreCase("fresher") ? "Fresher_Template.pdf" : "Corporate_Pro_Template.pdf";
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewResume(@PathVariable Long id) {
        if (id == null) return ResponseEntity.badRequest().build();
        ResumeReview review = resumeReviewRepository.findById(id).orElse(null);
        if (review == null || review.getFileData() == null) return ResponseEntity.notFound().build();
        
        String fileName = review.getFileName() != null ? review.getFileName() : "resume.pdf";
        MediaType mediaType = MediaType.APPLICATION_PDF;
        if (fileName.toLowerCase().endsWith(".docx") || fileName.toLowerCase().endsWith(".doc")) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(mediaType)
                .body(review.getFileData());
    }
}
