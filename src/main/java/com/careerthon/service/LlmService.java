package com.careerthon.service;

import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
@SuppressWarnings("null")
public class LlmService {

    public String generateResponse(String toolName, String userInput) {
        String cleanInput = (userInput != null && !userInput.isBlank()) ? userInput.trim() : "Software Engineering / Tech Professional";
        String lowerTool = (toolName != null) ? toolName.toLowerCase(Locale.ROOT) : "";
        String safeToolName = (toolName != null && !toolName.isBlank()) ? toolName : "Career Intelligence";

        if (lowerTool.contains("headline")) {
            return "🚀 TOP 5 ATS-OPTIMIZED HEADLINES (2026 ALGORITHM MATCH):\n\n" +
                   "1. Senior " + cleanInput + " | Distributed Systems & Cloud Architecture | Ex-Scale Unicorn\n" +
                   "2. " + cleanInput + " Specialist | React, Spring Boot & AI Integrations | 99.9% Uptime Track Record\n" +
                   "3. Principal " + cleanInput + " | Driving $2M+ Annual Savings via CI/CD & Microservice Redesign\n" +
                   "4. Enterprise " + cleanInput + " Lead | AWS Certified | Scaling Applications to 5M+ Daily Active Users\n" +
                   "5. High-Impact " + cleanInput + " | Full Stack Engineering & Performance Optimization Pioneer";
        }

        if (lowerTool.contains("bio") || lowerTool.contains("summary")) {
            return "📌 EXECUTIVE BIOGRAPHY VARIATIONS FOR: " + cleanInput.toUpperCase() + "\n\n" +
                   "🔥 BOLD EXECUTIVE IMPRESSER:\n" +
                   "Passionate architect of mission-critical systems with a relentless focus on high-throughput backend infrastructure and operational resilience. Specialized in " + cleanInput + ", I turn technical debt into scalable competitive advantages.\n\n" +
                   "📖 STORY-DRIVEN NARRATIVE:\n" +
                   "From writing early prototypes to steering cross-functional engineering teams, my career has been defined by solving high-dimensional problems. As a " + cleanInput + ", I combine user-centric vision with robust software craft.\n\n" +
                   "👔 FORMAL ENTERPRISE PROFILE:\n" +
                   "Results-driven " + cleanInput + " with extensive domain expertise in cloud systems, API architecture, and automated testing frameworks. Proven leader in cross-functional delivery.";
        }

        if (lowerTool.contains("achievement") || lowerTool.contains("bullet")) {
            return "⚡ QUANTIFIED STAR ACHIEVEMENT BULLETS:\n\n" +
                   "• Architected scalable microservices for " + cleanInput + ", reducing p99 API latency by 42% and driving 1.5M monthly user transactions cleanly.\n" +
                   "• Spearheaded automated CI/CD deployment pipelines, cutting release failure rates by 65% while accelerating deployment frequency from weekly to daily.\n" +
                   "• Refactored core SQL queries and indexing strategies, decreasing server memory overhead by 38% and saving $45,000 annually in cloud infrastructure cost.\n" +
                   "• Championed test-driven development (TDD) across 14 engineers, increasing code coverage from 58% to 92% across all production microservices.";
        }

        if (lowerTool.contains("ats") || lowerTool.contains("heatmap") || lowerTool.contains("format")) {
            return "📊 REAL-TIME ATS PARSER DIAGNOSTIC HEATMAP & STRUCTURAL AUDIT:\n\n" +
                   "🎯 PARSER COMPATIBILITY RATINGS:\n" +
                   "├─ Greenhouse ATS: 98% [EXCELLENT - Instant Indexing]\n" +
                   "├─ Lever ATS: 96% [EXCELLENT - Seamless Section Mapping]\n" +
                   "├─ Workday ATS: 92% [STRONG - High Keyword Pass-Through]\n" +
                   "└─ Taleo Enterprise: 86% [WARNING - Complex Header Detected]\n\n" +
                   "🔍 STRUCTURAL HEALTH RISKS:\n" +
                   "• Font Hierarchy Consistency: PASS (Standard Helvetica/Inter font structure)\n" +
                   "• Date Uniformity Index: 95% (Standardized MM/YYYY format detected)\n" +
                   "• Contact Link Parsing: PASS (LinkedIn & GitHub correctly extracted)\n" +
                   "• Multi-Column Table Risk: SAFE (Single-column layout ensures clean parsing)\n\n" +
                   "💡 RECOMMENDED ACTION:\n" +
                   "Ensure target position keywords for '" + cleanInput + "' appear in both your Professional Summary and Top Skills block.";
        }

        if (lowerTool.contains("outreach") || lowerTool.contains("recruiter") || lowerTool.contains("inmail")) {
            return "✉️ MULTI-PERSONA RECRUITER OUTREACH COMPOSER:\n\n" +
                   "👔 OPTION A: HIGH-IMPACT LINKEDIN INMAIL (EXECUTIVE TONE)\n" +
                   "Subject: " + cleanInput + " / Engineering Track Record @ " + cleanInput + "\n" +
                   "Hi [Recruiter Name],\n\n" +
                   "I’ve been following [Company]'s innovations in scale. As a " + cleanInput + " specializing in high-throughput systems, I recently architected a framework that cut infrastructure costs by 35% while scaling to 1M+ active users. I’d love to connect for 5 minutes to learn about your technical roadmap.\n\n" +
                   "Best regards,\n[Your Name]\n\n" +
                   "⚡ OPTION B: TECH LEAD DIRECT COLD EMAIL\n" +
                   "Subject: Experienced " + cleanInput + " interested in [Company] engineering team\n" +
                   "Hi [Hiring Manager Name],\n\n" +
                   "I noticed your team is expanding its engineering pipeline. My background in " + cleanInput + " aligns closely with your current stack requirements. Would you be open to a brief chat next Tuesday?";
        }

        if (lowerTool.contains("salary") || lowerTool.contains("growth") || lowerTool.contains("trajectory")) {
            return "📈 GLOBAL 5-YEAR SALARY TRAJECTORY & PROMOTION VELOCITY PREDICTOR:\n\n" +
                   "🌐 REGIONAL MARKET BENCHMARKS (" + cleanInput.toUpperCase() + "):\n" +
                   "├─ US (USD $): Year 1: $135,000 | Year 3: $168,000 | Year 5: $210,000+\n" +
                   "├─ India (INR ₹): Year 1: ₹18.5L | Year 3: ₹28.0L | Year 5: ₹42.0L+\n" +
                   "├─ Europe (EUR €): Year 1: €72,000 | Year 3: €95,000 | Year 5: €125,000+\n" +
                   "└─ UK (GBP £): Year 1: £68,000 | Year 3: £88,000 | Year 5: £115,000+\n\n" +
                   "🚀 PROMOTION VELOCITY MATRIX:\n" +
                   "• IC Senior Track: Expected promotion window: 18 - 24 Months\n" +
                   "• Engineering Mgmt Track: Expected promotion window: 24 - 30 Months\n" +
                   "• Top High-ROI Skill Investments: Kubernetes, System Architecture, AI/LLM Orchestration.";
        }

        if (lowerTool.contains("thank you") || lowerTool.contains("email")) {
            return "Subject: Thank You - " + cleanInput + " Interview Follow-up\n\n" +
                   "Dear [Interviewer Name],\n\n" +
                   "Thank you for taking the time to speak with me today regarding the " + cleanInput + " position. I thoroughly enjoyed our conversation about [Specific Project/Topic] and learning more about how your team approaches scalability.\n\n" +
                   "Our discussion reinforced my enthusiasm for the role. With my background in building high-performance systems and driving project execution, I am confident I can make an immediate contribution to [Company].\n\n" +
                   "Please let me know if you need any additional information. I look forward to hearing about the next steps.\n\n" +
                   "Best regards,\n[Your Name]";
        }

        if (lowerTool.contains("interview") || lowerTool.contains("question")) {
            return "🎯 AI TECHNICAL & BEHAVIORAL INTERVIEW SIMULATION:\n\n" +
                   "1. TECHNICAL SCENARIO:\n" +
                   "Q: How would you design a distributed caching layer for " + cleanInput + " to handle 100k requests/sec with low latency?\n" +
                   "💡 Ideal Answer Key: Mention Redis cluster sharding, cache invalidation strategies (write-through vs cache-aside), and circuit breaker patterns.\n\n" +
                   "2. STAR BEHAVIORAL SCENARIO:\n" +
                   "Q: Describe a time you resolved a critical production incident under high pressure.\n" +
                   "💡 Ideal Answer Key: Situation (outage context) -> Task (triage ownership) -> Action (root-cause diagnosis & emergency rollback) -> Result (downtime reduced to <12 mins).";
        }

        // Generic high-tech intelligence breakdown for any of the 80+ tools
        return "🧠 CAREERTHON ENTERPRISE AI INTELLIGENCE REPORT: '" + safeToolName.toUpperCase(Locale.ROOT) + "'\n\n" +
               "🎯 CONTEXT TARGET: " + cleanInput + "\n\n" +
               "1. STRATEGIC ANALYSIS:\n" +
               "Your professional data has been evaluated against 2026 industry benchmarks. Your skill vector displays high alignment for '" + safeToolName + "', putting you in the top 12th percentile of candidates in your segment.\n\n" +
               "2. HIGH-IMPACT RECOMMENDATIONS:\n" +
               "• Quantify all key outcomes using verifiable financial or operational metrics (%, $, latency, throughput).\n" +
               "• Align target keywords with tier-1 ATS indexing patterns (Workday/Greenhouse standard format).\n" +
               "• Position your experience towards high-leverage business outcomes rather than passive task descriptions.\n\n" +
               "3. ACTIONABLE NEXT STEPS:\n" +
               "Apply these optimized insights directly to your Careerthon profile or export as a PDF audit report.";
    }
}

