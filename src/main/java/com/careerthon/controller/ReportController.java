package com.careerthon.controller;

import com.careerthon.model.ProfileReview;
import com.careerthon.service.EmailService;
import com.careerthon.service.ProfileAnalyzerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final ProfileAnalyzerService analyzerService;
    private final EmailService emailService;

    public ReportController(ProfileAnalyzerService analyzerService, EmailService emailService) {
        this.analyzerService = analyzerService;
        this.emailService = emailService;
    }

    @GetMapping("/{id}")
    public String viewReport(@PathVariable Long id, Model model, HttpSession session) {
        Optional<ProfileReview> optReview = analyzerService.getReview(id);
        if (optReview.isEmpty()) {
            return "redirect:/";
        }
        ProfileReview review = optReview.get();
        if (!canAccessReview(review, session)) {
            return "redirect:/login";
        }
        model.addAttribute("review", review);
        model.addAttribute("breakdown", review.getScoreBreakdown());
        return "report";
    }

    @PostMapping("/{id}/email")
    @ResponseBody
    public String emailReport(@PathVariable Long id, @RequestParam String email, HttpSession session) {
        Optional<ProfileReview> optReview = analyzerService.getReview(id);
        if (optReview.isPresent()) {
            ProfileReview review = optReview.get();
            if (!canAccessReview(review, session)) {
                return "{\"success\":false,\"message\":\"Access denied\"}";
            }
            emailService.sendReportWithPdf(email, review);
            return "{\"success\":true,\"message\":\"Report sent with PDF to " + email + "\"}";
        }
        return "{\"success\":false,\"message\":\"Review not found\"}";
    }

    @GetMapping("/{id}/pdf")
    public org.springframework.http.ResponseEntity<byte[]> downloadPdfReport(@PathVariable Long id, HttpSession session) {
        Optional<ProfileReview> optReview = analyzerService.getReview(id);
        if (optReview.isEmpty()) {
            return org.springframework.http.ResponseEntity.notFound().build();
        }
        ProfileReview review = optReview.get();
        if (!canAccessReview(review, session)) {
            return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build();
        }
        byte[] pdfBytes = emailService.generatePdfReport(review);
        if (pdfBytes == null || pdfBytes.length == 0) {
            return org.springframework.http.ResponseEntity.internalServerError().build();
        }
        String filename = "Careerthon_Audit_Report_" + id + ".pdf";
        return org.springframework.http.ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    private boolean canAccessReview(ProfileReview review, HttpSession session) {
        if (review == null) return false;
        if (session != null && Boolean.TRUE.equals(session.getAttribute("OWNED_PROFILE_REVIEW_" + review.getId()))) {
            return true;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return true;
            }
            String name = auth.getName();
            if (review.getEmailAddress() != null && review.getEmailAddress().equalsIgnoreCase(name)) {
                return true;
            }
            if (review.getUserName() != null && review.getUserName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}

