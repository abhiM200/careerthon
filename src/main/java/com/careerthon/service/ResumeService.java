package com.careerthon.service;

import com.careerthon.model.ResumeReview;
import com.careerthon.repository.ResumeReviewRepository;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ResumeService {

    private final ResumeReviewRepository resumeReviewRepository;
    private final EmailService emailService;
    private final Random random = new Random();
    private final Tika tika = new Tika();

    public ResumeService(ResumeReviewRepository resumeReviewRepository, EmailService emailService) {
        this.resumeReviewRepository = resumeReviewRepository;
        this.emailService = emailService;
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
            "Customer Success Manager", "Finance Manager", "Corporate Strategy Associate", "UI/UX Designer"
    );

    public ResumeReview analyzeResume(MultipartFile file, String userName, String userEmail) {
        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown_file";
        String content = "";
        
        try (InputStream stream = file.getInputStream()) {
            content = tika.parseToString(stream);
        } catch (Exception e) {
            // Fallback if parsing fails
            content = "User with background in " + fileName.toLowerCase();
        }

        // Logic to simulate ATS analysis
        int score = calculateSimulatedScore(content);
        
        List<String> suggested = new ArrayList<>();
        suggestRoles(content, suggested);

        String suggestedRoles = String.join(", ", suggested);
        String suggestions = generateSuggestions(score, suggested, content);

        ResumeReview review = new ResumeReview(fileName, userName, userEmail, score, suggestedRoles, suggestions);
        ResumeReview saved = resumeReviewRepository.save(review);
        
        // Trigger actual email for resume report
        if (userEmail != null && !userEmail.isEmpty()) {
            String reportUrl = "https://careerthon.onrender.com/resume/results/" + saved.getId();
            emailService.sendReport(userEmail, reportUrl, userName);
        }

        return saved;
    }

    private int calculateSimulatedScore(String content) {
        String lowerContent = content.toLowerCase();
        int score = 60 + random.nextInt(20);
        
        // Bonus for length
        if (content.length() > 1000) score += 5;
        if (content.length() > 5000) score += 5;
        
        // Keyword checking
        String[] keywords = {"agile", "scrum", "java", "python", "sql", "cloud", "aws", "docker", "leadership", "impact", "result"};
        for (String kw : keywords) {
            if (lowerContent.contains(kw)) score += 2;
        }
        
        return Math.min(score, 99);
    }

    private void suggestRoles(String content, List<String> suggested) {
        String lowerContent = content.toLowerCase();
        
        // Suggest tech roles
        if (lowerContent.contains("code") || lowerContent.contains("java") || lowerContent.contains("python") || lowerContent.contains("developer")) {
            suggested.add("SDE");
            suggested.add("Full Stack Developer");
        }
        
        // Suggest data
        if (lowerContent.contains("sql") || lowerContent.contains("data") || lowerContent.contains("intelligence")) {
            suggested.add("Data Analyst");
            suggested.add("Data Scientist");
        }
        
        // Suggest non-tech
        if (lowerContent.contains("p&l") || lowerContent.contains("client") || lowerContent.contains("stakeholder") || lowerContent.contains("management")) {
            suggested.add("Project Manager");
            suggested.add("Customer Success");
        }

        // Add 2 random appropriate roles to satisfy requirement of "all positions"
        if (suggested.isEmpty()) {
            suggested.add(TECHNICAL_ROLES.get(random.nextInt(TECHNICAL_ROLES.size())));
            suggested.add(NON_TECHNICAL_ROLES.get(random.nextInt(NON_TECHNICAL_ROLES.size())));
        }
    }

    private String generateSuggestions(int score, List<String> suggested, String content) {
        StringBuilder sb = new StringBuilder();
        if (score < 80) {
            sb.append("Your resume needs better keyword optimization. ");
            sb.append("Consider adding more industry-specific technical skills. ");
        } else {
            sb.append("Excellent resume structure! ");
        }
        
        if (!content.toLowerCase().contains("%") && !content.toLowerCase().contains("improved") && !content.toLowerCase().contains("reduced")) {
            sb.append("Focus on highlighting impact with metrics (e.g., 'Improved performance by 20%'). ");
        }
        
        sb.append("Ideal roles for you include: ").append(String.join(", ", suggested));
        return sb.toString();
    }
}
