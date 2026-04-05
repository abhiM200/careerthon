package com.careerthon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ai-tools")
public class AiAdvisorController {

    public AiAdvisorController() {}

    @GetMapping("/interview")
    public String interviewPage() {
        return "ai/interview";
    }

    @PostMapping("/interview/generate")
    public String generateQuestions(@RequestParam("role") String role, Model model) {
        String questions = "Expert Interview Questions for " + role + ":\n" +
            "1. Can you describe your technical stack and why you chose it for your latest project?\n" +
            "2. How do you handle conflict in a fast-paced corporate environment?\n" +
            "3. Walk me through a complex problem you solved recently.\n" +
            "4. What is your process for learning new technologies?\n" +
            "5. Where do you see your career heading in the next 5 years?";
        model.addAttribute("questions", questions);
        model.addAttribute("role", role);
        return "ai/interview";
    }

    @PostMapping("/interview/evaluate")
    public String evaluateInterview(@RequestParam("role") String role, 
                                   @RequestParam("answers") String answers, 
                                   Model model) {
        String feedback = "Professional Feedback for " + role + " Candidate:\n" +
            "Score: 8.5/10 (Expert Assessment)\n\n" +
            "Detailed Analysis: Your answers show a strong grasp of industry fundamentals and professional maturity. To improve further, focus on highlighting specific metrics (e.g., efficiency gain, cost reduction) in your responses. Your communication style is clear and confident.";
        model.addAttribute("feedback", feedback);
        model.addAttribute("role", role);
        return "ai/interview";
    }

    @GetMapping("/linkedin")
    public String linkedinAdvisorPage() {
        return "ai/linkedin";
    }

    @PostMapping("/linkedin/analyze")
    public String analyzeLinkedIn(@RequestParam("data") String data, Model model) {
        String suggestions = "Expert LinkedIn Optimization:\n" +
            "Headline: Strategic [Your Role] | Transforming Problems into Scalable Solutions\n" +
            "Summary: Professional with a focus on high-impact delivery and efficient operations.\n\n" +
            "Key Improvements:\n" +
            "1. Use a professional headshot with a neutral background.\n" +
            "2. Highlight 3 key achievements with quantifiable data.\n" +
            "3. Add skills that specifically match the job descriptions of your target companies.";
        model.addAttribute("suggestions", suggestions);
        return "ai/linkedin";
    }

    @GetMapping("/portfolio")
    public String portfolioPage() {
        return "ai/portfolio";
    }

    @PostMapping("/portfolio/generate")
    public String generatePortfolio(@RequestParam("projects") String projects, 
                                   @RequestParam("experience") String experience, 
                                   Model model) {
        String content = "Professional Portfolio Content:\n\n" +
            "About Me Bio: Highly driven professional with experience in " + experience + ". Specialized in building " + projects + ".\n\n" +
            "Project Highlight 1: A deep-dive into scalable architecture and user-centric design.\n" +
            "Project Highlight 2: Implementation of automated workflows and robust data structures.";
        model.addAttribute("portfolioContent", content);
        return "ai/portfolio";
    }

    @GetMapping("/skill-gap")
    public String skillGapPage() {
        return "ai/skill-gap";
    }

    @PostMapping("/skill-gap/analyze")
    public String analyzeSkillGap(@RequestParam("skills") String skills, 
                                 @RequestParam("role") String role, 
                                 Model model) {
        String gapAnalysis = "Critical Skill Gap Analysis for " + role + ":\n" +
            "Match Percentage: 75% (Expert Estimate)\n\n" +
            "Missing Skills: Advanced System Design, Cloud Architecture (AWS/Azure), and Agile Leadership.\n" +
            "Action Plan: Focus on certification in cloud technologies and contribute to open-source system design projects.";
        model.addAttribute("gapAnalysis", gapAnalysis);
        model.addAttribute("role", role);
        return "ai/skill-gap";
    }

    @GetMapping("/roadmap")
    public String roadmapPage() {
        return "ai/roadmap";
    }

    @PostMapping("/roadmap/generate")
    public String generateRoadmap(@RequestParam("startingFrom") String startingFrom, 
                                 @RequestParam("targetGoal") String targetGoal, 
                                 Model model) {
        String roadmap = "Expert 90-Day Roadmap to " + targetGoal + ":\n" +
            "Day 0-30: Mastery of Fundamentals & Core Logic.\n" +
            "Day 31-60: Project Implementation & Real-world Scenarios.\n" +
            "Day 61-90: Portfolio Polishing & Interview Preparation.";
        model.addAttribute("roadmap", roadmap);
        model.addAttribute("target", targetGoal);
        return "ai/roadmap";
    }
}
