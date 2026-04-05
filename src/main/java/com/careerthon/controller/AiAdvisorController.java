package com.careerthon.controller;

import com.careerthon.service.LlmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ai-tools")
public class AiAdvisorController {

    private final LlmService llmService;

    public AiAdvisorController(LlmService llmService) {
        this.llmService = llmService;
    }

    @GetMapping("/interview")
    public String interviewPage() {
        return "ai/interview";
    }

    @PostMapping("/interview/generate")
    public String generateQuestions(@RequestParam("role") String role, Model model) {
        String prompt = "Generate 5 challenging technical and behavioral interview questions for the role of: " + role;
        String questions = llmService.generateResponse("You are a senior interviewer.", prompt);
        model.addAttribute("questions", questions);
        model.addAttribute("role", role);
        return "ai/interview";
    }

    @PostMapping("/interview/evaluate")
    public String evaluateInterview(@RequestParam("role") String role, 
                                   @RequestParam("answers") String answers, 
                                   Model model) {
        String prompt = "Review these interview answers for the role of " + role + ". Provide constructive feedback and a mock performance rating out of 10.\n\nUser Answers:\n" + answers;
        String feedback = llmService.generateResponse("You are a senior interviewer providing feedback.", prompt);
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
        String prompt = "Analyze the following LinkedIn profile data and suggest a catchy headline, an impactful summary, and 3 key profile improvements.\n\nProfile Data:\n" + data;
        String suggestions = llmService.generateResponse("You are a professional LinkedIn branding expert.", prompt);
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
        String prompt = "Based on the following projects and experience, generate a professional 'About Me' bio and 3 detailed project descriptions perfect for a portfolio website.\n\nProjects: " + projects + "\nExperience: " + experience;
        String content = llmService.generateResponse("You are a professional technical writer.", prompt);
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
        String prompt = "Compare these skills: [" + skills + "] with the target role: [" + role + "]. Provide: 1. Match percentage estimate. 2. Critical missing technical skills. 3. Immediate learning suggestions.";
        String gapAnalysis = llmService.generateResponse("You are a skills analysis expert.", prompt);
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
        String prompt = "Generate a structured 30/60/90 day learning roadmap for a user who knows: [" + startingFrom + "] and wants to become a: [" + targetGoal + "]. Provide actionable steps for each phase.";
        String roadmap = llmService.generateResponse("You are a senior technical mentor.", prompt);
        model.addAttribute("roadmap", roadmap);
        model.addAttribute("target", targetGoal);
        return "ai/roadmap";
    }
}
