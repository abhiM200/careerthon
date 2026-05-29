package com.careerthon.config;

import com.careerthon.model.User;
import com.careerthon.model.UserStory;
import com.careerthon.model.Job;
import com.careerthon.repository.UserRepository;
import com.careerthon.repository.UserStoryRepository;
import com.careerthon.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserStoryRepository userStoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JobRepository jobRepository;

    public DataInitializer(UserStoryRepository userStoryRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder, JobRepository jobRepository) {
        this.userStoryRepository = userStoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jobRepository = jobRepository;
    }

    @Override
    public void run(String... args) {
        // Only seed stories if empty, but ALWAYS ensure our key team members are
        // present
        if (userStoryRepository.count() == 0) {
            List<UserStory> originalStories = List.of(
                    new UserStory(
                            "Abhishek Mishra",
                            "Full Stack Developer & Project Lead",
                            "As the developer and architect of Careerthon, I built this platform to democratize LinkedIn profile optimization. Using Spring Boot and modern web technologies, I created an end-to-end SaaS solution that provides actionable insights to job seekers.",
                            "AM", "#0A66C2", "/images/abhishek_mishra.jpg"),
                    new UserStory(
                            "Priyanshu Shekhar",
                            "UI/UX Designer & Frontend Developer",
                            "I focused on crafting an intuitive and visually stunning user experience for Careerthon. From the animated landing page to the interactive report dashboard, every element was designed to make profile analysis accessible and engaging.",
                            "PS", "#7c3aed", "/images/priyanshu_shekhar.jpg"),
                    new UserStory(
                            "Altamash Mallick",
                            "Backend Engineer & Data Analyst",
                            "I contributed to the profile analysis engine and data modeling for Careerthon. The scoring algorithm uses weighted analysis across 15 profile dimensions, benchmarked against industry standards.",
                            "AM", "#059669", "/images/altamash_mallick.jpg"),
                    new UserStory(
                            "Pratiksha Kumari",
                            "Frontend Developer",
                            "I focused on developing a seamless and highly responsive user interface for Careerthon. By leveraging modern frontend design principles, subtle micro-interactions, and optimized assets, I ensured that every user has an exceptionally polished and premium experience while navigating their profile insights.",
                            "PK", "#ec4899", "/images/pratiksha_kumari.png"),
                    new UserStory(
                            "Anubha Shankar",
                            "Marketing Manager",
                            "I led the growth and branding strategies for Careerthon. By leveraging data-driven marketing campaigns, social media outreach, and user acquisition models, I succeeded in establishing Careerthon as a premium standard in LinkedIn profile intelligence, exponentially scaling our user base.",
                            "AS", "#ea580c", "/images/anubha_shankar.jpg"),
                    new UserStory(
                            "Binit Mishra",
                            "Accenture Germany (ex-TCS)",
                            "This tool gave my profile the edge it needed. The comprehensive analysis was precise and personalized, helping me highlight my strengths in Delivery & Operations.",
                            "BM", "#dc2626", null));
            userStoryRepository.saveAll(originalStories);
            System.out.println("✅ Seeded initial user stories.");
        } else {
            List<UserStory> existing = userStoryRepository.findAll();

            // Delete Head HR if exists
            existing.stream()
                    .filter(s -> "Head HR".equals(s.getName()))
                    .findFirst()
                    .ifPresent(userStoryRepository::delete);

            // Explicitly remove Ayushi Mishra if exists in DB
            existing.stream()
                    .filter(s -> "Ayushi Mishra".equals(s.getName()))
                    .findFirst()
                    .ifPresent(userStoryRepository::delete);

            // Ensure Pratikssha Kumari is added to the database
            boolean hasPratikssha = existing.stream()
                    .anyMatch(s -> "Pratikssha Kumari".equals(s.getName()));
            if (!hasPratikssha) {
                userStoryRepository.save(new UserStory(
                        "Pratikssha Kumari",
                        "Frontend Developer",
                        "I focused on developing a seamless and highly responsive user interface for Careerthon. By leveraging modern frontend design principles, subtle micro-interactions, and optimized assets, I ensured that every user has an exceptionally polished and premium experience while navigating their profile insights.",
                        "PK", "#ec4899", "/images/pratikssha_kumari.png"));
                System.out.println("✅ Seeded Pratikssha Kumari to the team.");
            }

            // Ensure Anubha Shankar is added to the database
            boolean hasAnubha = existing.stream()
                    .anyMatch(s -> "Anubha Shankar".equals(s.getName()));
            if (!hasAnubha) {
                userStoryRepository.save(new UserStory(
                        "Anubha Shankar",
                        "Marketing Manager",
                        "I led the growth and branding strategies for Careerthon. By leveraging data-driven marketing campaigns, social media outreach, and user acquisition models, I succeeded in establishing Careerthon as a premium standard in LinkedIn profile intelligence, exponentially scaling our user base.",
                        "AS", "#ea580c", "/images/anubha_shankar.jpg"));
                System.out.println("✅ Seeded Anubha Shankar to the team.");
            }
        }

        // Ensure Admin user exists
        userRepository.findByUsername("admin").ifPresentOrElse(
                adminUser -> {
                    adminUser.setRoles("ROLE_ADMIN");
                    adminUser.setPassword(passwordEncoder.encode("admin"));
                    userRepository.save(adminUser);
                },
                () -> {
                    User admin = new User("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN", "Administrator");
                    userRepository.save(admin);
                });

        // Ensure initial Job Openings are seeded
        if (jobRepository.count() == 0) {
            jobRepository.saveAll(List.of(
                new Job("AI NLP Architect", "Full-Time", "Engineering | Fully Remote", 
                    "Architect next-generation deep learning parsing algorithms capable of extracting intelligence from complex executive resumes."),
                new Job("Senior Profile Advisor", "Part-Time / Contract", "Customer Success | Remote / Hybrid", 
                    "Direct executive clients in identifying digital gaps, providing strategic keyword alignment roadmaps, and hosting private consultations."),
                new Job("Spring Boot Platform Engineer", "Full-Time", "Engineering | Fully Remote", 
                    "Scale high-performance enterprise REST API frameworks, secure H2/PostgreSQL database instances, and maintain Thymeleaf UI pipelines.")
            ));
            System.out.println("✅ Seeded initial job openings.");
        }
    }

}
