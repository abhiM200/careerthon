package com.careerthon.controller;

import com.careerthon.model.ProfileReview;
import com.careerthon.service.ProfileAnalyzerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ProfileAnalyzerService analyzerService;

    public ApiController(ProfileAnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @GetMapping("/reviews")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfileReview>> getAllReviews() {
        return ResponseEntity.ok(analyzerService.getAllReviews());
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ProfileReview> getReview(@PathVariable Long id, HttpSession session) {
        Optional<ProfileReview> optReview = analyzerService.getReview(id);
        if (optReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProfileReview review = optReview.get();
        if (!canAccessReview(review, session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(review);
    }

    @GetMapping("/reviews/{id}/status")
    public ResponseEntity<String> getStatus(@PathVariable Long id, HttpSession session) {
        Optional<ProfileReview> optReview = analyzerService.getReview(id);
        if (optReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProfileReview review = optReview.get();
        if (!canAccessReview(review, session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok("{\"status\":\"" + review.getStatus() + "\"}");
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

