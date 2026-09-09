package com.careerthon.controller;

import com.careerthon.model.ProfileReview;
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
@RequestMapping("/review")
public class ReviewController {

    private final ProfileAnalyzerService analyzerService;

    public ReviewController(ProfileAnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @GetMapping
    public String reviewForm() {
        return "review";
    }

    @PostMapping("/submit")
    public String submitReview(@RequestParam String linkedinUrl,
                                @RequestParam(required = false) String email,
                                HttpSession session) {
        ProfileReview review = analyzerService.createReview(linkedinUrl, email);
        if (session != null) {
            session.setAttribute("OWNED_PROFILE_REVIEW_" + review.getId(), true);
        }
        return "redirect:/review/analyzing/" + review.getId();
    }

    @GetMapping("/analyzing/{id}")
    public String analyzing(@PathVariable Long id, Model model, HttpSession session) {
        Optional<ProfileReview> optReview = analyzerService.getReview(id);
        if (optReview.isEmpty()) {
            return "redirect:/review";
        }
        if (!canAccessReview(optReview.get(), session)) {
            return "redirect:/login";
        }
        model.addAttribute("reviewId", id);
        return "analyzing";
    }

    @GetMapping("/analyze/{id}")
    @ResponseBody
    public String triggerAnalysis(@PathVariable Long id, HttpSession session) {
        Optional<ProfileReview> optReview = analyzerService.getReview(id);
        if (optReview.isEmpty()) {
            return "{\"status\":\"FAILED\"}";
        }
        ProfileReview reviewObj = optReview.get();
        if (!canAccessReview(reviewObj, session)) {
            return "{\"status\":\"FORBIDDEN\"}";
        }
        ProfileReview review = analyzerService.analyzeProfile(id);
        if (review != null && review.getStatus() == ProfileReview.ReviewStatus.COMPLETED) {
            return "{\"status\":\"COMPLETED\",\"redirectUrl\":\"/report/" + id + "\"}";
        }
        return "{\"status\":\"FAILED\"}";
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

