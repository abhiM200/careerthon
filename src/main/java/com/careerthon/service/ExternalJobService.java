package com.careerthon.service;

import com.careerthon.model.ExternalJobListing;
import com.careerthon.model.JobPlatform;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExternalJobService {

    private final List<ExternalJobListing> allJobs = new ArrayList<>();

    public ExternalJobService() {
        initSampleJobs();
    }

    private void initSampleJobs() {
        // Naukri.com listings
        allJobs.add(new ExternalJobListing(
                "nk-101",
                "Senior Java Full Stack Developer",
                "Tata Consultancy Services",
                "Bangalore",
                JobPlatform.NAUKRI,
                "₹14,00,000 - ₹24,00,000 P.A.",
                "3-6 Yrs",
                "Full Time",
                "We are seeking an experienced Java Full Stack Developer proficient in Spring Boot, REST APIs, Microservices, and React/Angular to lead enterprise transformation projects.",
                Arrays.asList("Java", "Spring Boot", "React", "Microservices", "REST API", "SQL"),
                "2 hours ago",
                96,
                "https://www.naukri.com/job-listings-senior-java-full-stack-developer-tcs-bangalore-3-to-6-years-101"
        ));

        allJobs.add(new ExternalJobListing(
                "nk-102",
                "Lead Data Scientist & AI Specialist",
                "Flipkart",
                "Bangalore",
                JobPlatform.NAUKRI,
                "₹28,00,000 - ₹45,00,000 P.A.",
                "5+ Yrs",
                "Hybrid",
                "Design and deploy high-throughput Machine Learning models, Deep Learning algorithms, and LLM fine-tuning pipelines for e-commerce search & recommendation engines.",
                Arrays.asList("Python", "PyTorch", "Machine Learning", "LLM", "NLP", "BigQuery"),
                "5 hours ago",
                94,
                "https://www.naukri.com/job-listings-lead-data-scientist-ai-specialist-flipkart-bangalore-5-to-10-years-102"
        ));

        allJobs.add(new ExternalJobListing(
                "nk-103",
                "DevOps & Cloud Systems Engineer",
                "Infosys",
                "Pune",
                JobPlatform.NAUKRI,
                "₹12,00,000 - ₹20,00,000 P.A.",
                "2-5 Yrs",
                "Remote",
                "Manage Kubernetes clusters, Terraform infrastructure-as-code, and automated CI/CD pipelines across AWS and GCP environments.",
                Arrays.asList("DevOps", "AWS", "Kubernetes", "Docker", "Terraform", "CI/CD"),
                "1 day ago",
                91,
                "https://www.naukri.com/job-listings-devops-cloud-systems-engineer-infosys-pune-2-to-5-years-103"
        ));

        allJobs.add(new ExternalJobListing(
                "nk-104",
                "Backend Engineer - Spring Boot & Kafka",
                "Paytm",
                "Noida",
                JobPlatform.NAUKRI,
                "₹18,00,000 - ₹30,00,000 P.A.",
                "3-5 Yrs",
                "Full Time",
                "Build low-latency payment processing pipelines and distributed event-driven systems using Spring Boot, Apache Kafka, and PostgreSQL.",
                Arrays.asList("Java", "Spring Boot", "Kafka", "PostgreSQL", "Redis", "Distributed Systems"),
                "3 hours ago",
                95,
                "https://www.naukri.com/job-listings-backend-engineer-spring-boot-kafka-paytm-noida-3-to-5-years-104"
        ));

        // LinkedIn listings
        allJobs.add(new ExternalJobListing(
                "li-201",
                "Staff Software Engineer - Frontend",
                "Google",
                "Bangalore",
                JobPlatform.LINKEDIN,
                "₹35,00,000 - ₹60,00,000 P.A.",
                "5+ Yrs",
                "Hybrid",
                "Drive web client architecture for next-generation Cloud platforms using React, TypeScript, modern CSS modules, and web performance optimization tools.",
                Arrays.asList("React", "TypeScript", "JavaScript", "HTML5/CSS3", "Web Vitals", "System Design"),
                "Just now",
                98,
                "https://www.linkedin.com/jobs/view/staff-software-engineer-frontend-at-google-201"
        ));

        allJobs.add(new ExternalJobListing(
                "li-202",
                "Product Manager - AI Platform",
                "Microsoft",
                "Hyderabad",
                JobPlatform.LINKEDIN,
                "₹30,00,000 - ₹50,00,000 P.A.",
                "4-8 Yrs",
                "Hybrid",
                "Define product roadmap and developer experience for Copilot APIs and Cloud AI Services. Work closely with cross-functional engineering teams.",
                Arrays.asList("Product Management", "AI/ML", "Agile", "User Research", "Roadmap", "SQL"),
                "4 hours ago",
                92,
                "https://www.linkedin.com/jobs/view/product-manager-ai-platform-at-microsoft-202"
        ));

        allJobs.add(new ExternalJobListing(
                "li-203",
                "Junior Backend Java Developer",
                "Amazon",
                "Hyderabad",
                JobPlatform.LINKEDIN,
                "₹16,00,000 - ₹25,00,000 P.A.",
                "0-2 Yrs",
                "Full Time",
                "Develop scalable web microservices, write unit and integration tests, and optimize DynamoDB schemas for Amazon Fulfillment Systems.",
                Arrays.asList("Java", "AWS", "DynamoDB", "REST API", "Data Structures"),
                "6 hours ago",
                90,
                "https://www.linkedin.com/jobs/view/junior-backend-java-developer-at-amazon-203"
        ));

        allJobs.add(new ExternalJobListing(
                "li-204",
                "UI/UX Product Designer",
                "Swiggy",
                "Bangalore",
                JobPlatform.LINKEDIN,
                "₹15,00,000 - ₹26,00,000 P.A.",
                "2-5 Yrs",
                "Remote",
                "Design seamless, elegant mobile and desktop interface experiences for millions of consumer food & instant delivery users.",
                Arrays.asList("Figma", "UI/UX", "User Research", "Prototyping", "Design Systems"),
                "1 day ago",
                89,
                "https://www.linkedin.com/jobs/view/ui-ux-product-designer-at-swiggy-204"
        ));

        // Indeed listings
        allJobs.add(new ExternalJobListing(
                "id-301",
                "Full Stack Web Developer (Node.js & React)",
                "Zomato",
                "Gurugram",
                JobPlatform.INDEED,
                "₹14,00,000 - ₹22,00,000 P.A.",
                "2-4 Yrs",
                "Full Time",
                "Build modern, fast interactive web tools using Node.js, Express, React, and MongoDB. Experience in responsive web apps is required.",
                Arrays.asList("Node.js", "React", "JavaScript", "MongoDB", "Express", "Tailwind CSS"),
                "1 hour ago",
                93,
                "https://www.indeed.com/viewjob?jk=full-stack-web-developer-zomato-gurugram-301"
        ));

        allJobs.add(new ExternalJobListing(
                "id-302",
                "Python & Django Software Engineer",
                "Razorpay",
                "Bangalore",
                JobPlatform.INDEED,
                "₹16,00,000 - ₹28,00,000 P.A.",
                "3-5 Yrs",
                "Remote",
                "Create highly secure banking and payment gateway API infrastructure using Python, Django, Celery, Redis, and PostgreSQL.",
                Arrays.asList("Python", "Django", "PostgreSQL", "Redis", "Celery", "REST API"),
                "3 hours ago",
                94,
                "https://www.indeed.com/viewjob?jk=python-django-software-engineer-razorpay-302"
        ));

        allJobs.add(new ExternalJobListing(
                "id-303",
                "Cloud Security & Compliance Lead",
                "Wipro",
                "Chennai",
                JobPlatform.INDEED,
                "₹20,00,000 - ₹32,00,000 P.A.",
                "5+ Yrs",
                "Hybrid",
                "Lead cloud governance, vulnerability assessments, penetration testing, and AWS/Azure security posture management across client workloads.",
                Arrays.asList("Cloud Security", "AWS", "Azure", "Cybersecurity", "Compliance", "Python"),
                "1 day ago",
                88,
                "https://www.indeed.com/viewjob?jk=cloud-security-compliance-lead-wipro-303"
        ));

        allJobs.add(new ExternalJobListing(
                "id-304",
                "Quality Assurance (QA) Automation Engineer",
                "PhonePe",
                "Bangalore",
                JobPlatform.INDEED,
                "₹12,00,000 - ₹19,00,000 P.A.",
                "2-4 Yrs",
                "Full Time",
                "Develop comprehensive automated test suites for mobile and backend web APIs using Selenium, TestNG, Java, and Postman.",
                Arrays.asList("QA Automation", "Java", "Selenium", "Postman", "API Testing"),
                "2 days ago",
                87,
                "https://www.indeed.com/viewjob?jk=qa-automation-engineer-phonepe-304"
        ));
    }

    public List<ExternalJobListing> searchJobs(String query, String location, String platformStr, String experience, String jobType) {
        return allJobs.stream().filter(job -> {
            boolean matchesQuery = true;
            if (query != null && !query.trim().isEmpty()) {
                String qLower = query.trim().toLowerCase();
                matchesQuery = job.getTitle().toLowerCase().contains(qLower)
                        || job.getCompany().toLowerCase().contains(qLower)
                        || job.getDescription().toLowerCase().contains(qLower)
                        || job.getSkills().stream().anyMatch(s -> s.toLowerCase().contains(qLower));
            }

            boolean matchesLocation = true;
            if (location != null && !location.trim().isEmpty() && !"Any".equalsIgnoreCase(location.trim())) {
                String locLower = location.trim().toLowerCase();
                matchesLocation = job.getLocation().toLowerCase().contains(locLower)
                        || job.getJobType().toLowerCase().contains(locLower);
            }

            boolean matchesPlatform = true;
            if (platformStr != null && !platformStr.trim().isEmpty() && !"ALL".equalsIgnoreCase(platformStr.trim())) {
                try {
                    JobPlatform selectedPlatform = JobPlatform.valueOf(platformStr.trim().toUpperCase());
                    matchesPlatform = job.getPlatform() == selectedPlatform;
                } catch (IllegalArgumentException e) {
                    matchesPlatform = true;
                }
            }

            boolean matchesExp = true;
            if (experience != null && !experience.trim().isEmpty() && !"All".equalsIgnoreCase(experience.trim())) {
                String expLower = experience.trim().toLowerCase();
                matchesExp = job.getExperienceLevel().toLowerCase().contains(expLower);
            }

            boolean matchesJobType = true;
            if (jobType != null && !jobType.trim().isEmpty() && !"All".equalsIgnoreCase(jobType.trim())) {
                String jtLower = jobType.trim().toLowerCase();
                matchesJobType = job.getJobType().toLowerCase().contains(jtLower);
            }

            return matchesQuery && matchesLocation && matchesPlatform && matchesExp && matchesJobType;
        }).map(job -> {
            // Recalculate dynamic match percentage if search query provided
            if (query != null && !query.trim().isEmpty()) {
                int calcMatch = calculateMatchScore(job, query.trim());
                job.setMatchPercentage(calcMatch);
            }
            return job;
        }).sorted(Comparator.comparingInt(ExternalJobListing::getMatchPercentage).reversed())
          .collect(Collectors.toList());
    }

    public List<ExternalJobListing> getRecommendations(List<String> userSkills) {
        if (userSkills == null || userSkills.isEmpty()) {
            return allJobs.stream()
                    .sorted(Comparator.comparingInt(ExternalJobListing::getMatchPercentage).reversed())
                    .limit(6)
                    .collect(Collectors.toList());
        }

        Set<String> targetSkillSet = userSkills.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toSet());

        return allJobs.stream().map(job -> {
            long matchCount = job.getSkills().stream()
                    .map(String::toLowerCase)
                    .filter(s -> targetSkillSet.stream().anyMatch(t -> s.contains(t) || t.contains(s)))
                    .count();

            int matchScore = targetSkillSet.isEmpty() ? job.getMatchPercentage() :
                    Math.min(99, Math.max(75, (int) Math.round(((double) matchCount / Math.max(1, targetSkillSet.size())) * 100)));
            
            ExternalJobListing copy = new ExternalJobListing(
                    job.getId(), job.getTitle(), job.getCompany(), job.getLocation(),
                    job.getPlatform(), job.getSalary(), job.getExperienceLevel(),
                    job.getJobType(), job.getDescription(), job.getSkills(),
                    job.getPostedTime(), matchScore, job.getDirectApplyUrl()
            );
            return copy;
        }).sorted(Comparator.comparingInt(ExternalJobListing::getMatchPercentage).reversed())
          .limit(6)
          .collect(Collectors.toList());
    }

    public Map<String, String> generatePlatformSearchUrls(String query, String location) {
        String qEncoded = URLEncoder.encode(query != null ? query.trim() : "software engineer", StandardCharsets.UTF_8);
        String locEncoded = URLEncoder.encode(location != null ? location.trim() : "India", StandardCharsets.UTF_8);

        Map<String, String> urls = new LinkedHashMap<>();
        urls.put("naukri", "https://www.naukri.com/" + qEncoded.toLowerCase().replaceAll("\\+", "-") + "-jobs-in-" + locEncoded.toLowerCase().replaceAll("\\+", "-"));
        urls.put("linkedin", "https://www.linkedin.com/jobs/search/?keywords=" + qEncoded + "&location=" + locEncoded);
        urls.put("indeed", "https://www.indeed.com/jobs?q=" + qEncoded + "&l=" + locEncoded);
        return urls;
    }

    private int calculateMatchScore(ExternalJobListing job, String query) {
        String qLower = query.toLowerCase();
        int score = 80;
        if (job.getTitle().toLowerCase().contains(qLower)) score += 12;
        if (job.getSkills().stream().anyMatch(s -> s.toLowerCase().contains(qLower))) score += 7;
        return Math.min(99, score);
    }
}
