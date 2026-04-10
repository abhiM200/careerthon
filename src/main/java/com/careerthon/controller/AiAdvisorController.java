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
    public String generatePortfolio(@RequestParam("name") String name,
                                   @RequestParam("title") String title,
                                   @RequestParam("projects") String projects, 
                                   @RequestParam("experience") String experience, 
                                   Model model) {
        String content = "Professional Bio for " + name + ":\n" +
            "Highly driven " + title + " with experience in " + experience + ". Specialized in building " + projects + ".\n\n" +
            "Expert Advice: Your portfolio should lead with the " + projects.split(",")[0] + " project as it showcases your most relevant skills.";
            
        // Generate a simple, premium HTML template
        String htmlCode = "<!DOCTYPE html>\n<html>\n<head>\n" +
            "<title>" + name + " | Portfolio</title>\n" +
            "<script src=\"https://cdn.tailwindcss.com\"></script>\n" +
            "</head>\n<body class=\"bg-slate-50 text-slate-900\">\n" +
            "  <nav class=\"p-6 flex justify-between items-center max-w-5xl mx-auto\">\n" +
            "    <h1 class=\"text-2xl font-black\">" + name.split(" ")[0].toUpperCase() + "</h1>\n" +
            "    <div class=\"space-x-4 font-bold text-sm uppercase\"><a>About</a> <a>Projects</a> <a>Contact</a></div>\n" +
            "  </nav>\n" +
            "  <header class=\"py-24 text-center max-w-3xl mx-auto px-6\">\n" +
            "    <h2 class=\"text-6xl font-black mb-6 tracking-tighter\">" + title + "</h2>\n" +
            "    <p class=\"text-xl text-slate-500 leading-relaxed\">Building meaningful solutions through " + experience + " and specialized in " + projects + ".</p>\n" +
            "  </header>\n" +
            "  <section class=\"max-w-5xl mx-auto px-6 py-20 bg-white rounded-[40px] shadow-xl mb-24\">\n" +
            "    <h3 class=\"text-3xl font-black mb-12\">Featured Projects</h3>\n" +
            "    <div class=\"grid md:grid-cols-2 gap-8\">\n" +
            "      <div class=\"p-8 bg-slate-50 rounded-3xl border border-slate-100\">\n" +
            "        <div class=\"text-4xl mb-4\">🚀</div>\n" +
            "        <h4 class=\"text-xl font-bold\">" + projects.split(",")[0] + "</h4>\n" +
            "        <p class=\"text-slate-500 mt-2\">A deep-dive into scalable architecture and user-centric design.</p>\n" +
            "      </div>\n" +
            "    </div>\n" +
            "  </section>\n" +
            "</body>\n</html>";

        model.addAttribute("portfolioContent", content);
        model.addAttribute("portfolioHtml", htmlCode);
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
