package com.careerthon.service;

import com.careerthon.model.ResumeReview;
import com.careerthon.repository.ResumeReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ResumeService {

    private final ResumeReviewRepository resumeReviewRepository;
    private final Random random = new Random();

    public ResumeService(ResumeReviewRepository resumeReviewRepository) {
        this.resumeReviewRepository = resumeReviewRepository;
    }

    private static final List<String> TECHNICAL_ROLES = List.of(
            "Software Development Engineer (SDE)", "Backend Developer (Java/Spring)", "Frontend Developer (React/Angular)",
            "Full Stack Developer", "Data Scientist", "DevOps Engineer", "Cloud Solutions Architect",
            "SRE (Site Reliability Engineer)", "QA/Automation Engineer", "Artificial Intelligence Engineer",
            "Data Analyst", "Android/iOS Developer", "Cybersecurity Specialist", "Database Administrator"
    );

    private static final List<String> NON_TECHNICAL_ROLES = List.of(
            "Product Manager (PM)", "IT Project Manager", "Delivery Manager", "Operations Manager",
            "HR Business Partner", "Marketing Manager", "Sales & Account Executive", "Business Analyst",
            "Customer Success Manager", "Finance Manager", "Corporate Strategy Associate", "U/UX Designer"
    );

    public ResumeReview analyzeResume(String fileName, String userName, String userEmail, String content) {
        // Logic to simulate ATS analysis
        int score = calculateSimulatedScore(content);
        
        List<String> suggested = new ArrayList<>();
        suggestRoles(content, suggested);

        String suggestedRoles = String.join(", ", suggested);
        String suggestions = generateSuggestions(score, suggested);

        ResumeReview review = new ResumeReview(fileName, userName, userEmail, score, suggestedRoles, suggestions);
        return resumeReviewRepository.save(review);
    }

    private int calculateSimulatedScore(String content) {
        // Simulating logic based on keywords
        int base = 65 + random.nextInt(25);
        if (content.toLowerCase().contains("agile") || content.toLowerCase().contains("scrum")) base += 5;
        if (content.toLowerCase().contains("java") || content.toLowerCase().contains("python")) base += 5;
        return Math.min(base, 99);
    }

    private void suggestRoles(String content, List<String> suggested) {
        String lowerContent = content.toLowerCase();
        
        // Suggest tech roles
        if (lowerContent.contains("code") || lowerContent.contains("java") || lowerContent.contains("algorithm")) {
            suggested.add("SDE");
            suggested.add("Full Stack Developer");
        }
        
        // Suggest non-tech
        if (lowerContent.contains("p&l") || lowerContent.contains("client") || lowerContent.contains("stakeholder")) {
            suggested.add("Project Manager");
            suggested.add("Customer Success");
        }

        // Add 2 random appropriate roles to satisfy requirement of "all positions"
        if (suggested.isEmpty()) {
            suggested.add(TECHNICAL_ROLES.get(random.nextInt(TECHNICAL_ROLES.size())));
            suggested.add(NON_TECHNICAL_ROLES.get(random.nextInt(NON_TECHNICAL_ROLES.size())));
        }
    }

    private String generateSuggestions(int score, List<String> suggested) {
        StringBuilder sb = new StringBuilder();
        if (score < 80) {
            sb.append("Your resume needs better keyword optimization. ");
            sb.append("Focus on highlighting impact with metrics (e.g., 'Improved performance by 20%'). ");
        } else {
            sb.append("Excellent resume! ");
        }
        sb.append("Ideal roles for you include: ").append(String.join(", ", suggested));
        return sb.toString();
    }
}
