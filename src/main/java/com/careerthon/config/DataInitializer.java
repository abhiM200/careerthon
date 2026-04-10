package com.careerthon.config;

import com.careerthon.model.User;
import com.careerthon.model.UserStory;
import com.careerthon.repository.UserRepository;
import com.careerthon.repository.UserStoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserStoryRepository userStoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserStoryRepository userStoryRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userStoryRepository = userStoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Only seed stories if empty, but ALWAYS ensure our key team members are present
        if (userStoryRepository.count() == 0) {
            List<UserStory> originalStories = List.of(
                new UserStory(
                    "Ayushi Mishra",
                    "Senior Associate - Talent Acquisition",
                    "Ayushi is a results-driven Talent Acquisition professional with 4.6+ years of experience in hiring across Cloud, SaaS, Fintech, and Insurtech domains. She has strong expertise in end-to-end recruitment, stakeholder management, and building high-performing teams through innovative sourcing strategies, referrals, and vendor optimization.",
                    "AM", "#db2777", "/images/ayushi_mishra.png"
                ),
                new UserStory(
                    "Head HR",
                    "Head - Human Resources & CV Review",
                    "As our Head of HR, I oversee the final review of CV details to ensure they meet the highest standards of corporate excellence. With deep expertise in recruitment strategy and talent alignment, I guide candidates in presenting their professional journey effectively to top-tier employers.",
                    "HR", "#1e3a8a", "/images/head_hr.png"
                ),
                new UserStory(
                    "Abhishek Mishra",
                    "Full Stack Developer & Project Lead",
                    "As the developer and architect of Careerthon, I built this platform to democratize LinkedIn profile optimization. Using Spring Boot and modern web technologies, I created an end-to-end SaaS solution that provides actionable insights to job seekers.",
                    "AM", "#0A66C2", "/images/abhishek_mishra.jpg"
                ),
                new UserStory(
                    "Priyanshu Shekhar",
                    "UI/UX Designer & Frontend Developer",
                    "I focused on crafting an intuitive and visually stunning user experience for Careerthon. From the animated landing page to the interactive report dashboard, every element was designed to make profile analysis accessible and engaging.",
                    "PS", "#7c3aed", "/images/priyanshu_shekhar.jpg"
                ),
                new UserStory(
                    "Altamash Mallick",
                    "Backend Engineer & Data Analyst",
                    "I contributed to the profile analysis engine and data modeling for Careerthon. The scoring algorithm uses weighted analysis across 15 profile dimensions, benchmarked against industry standards.",
                    "AM", "#059669", "/images/altamash_mallick.jpg"
                ),
                new UserStory(
                    "Binit Mishra",
                    "Accenture Germany (ex-TCS)",
                    "This tool gave my profile the edge it needed. The comprehensive analysis was precise and personalized, helping me highlight my strengths in Delivery & Operations.",
                    "BM", "#dc2626", null
                )
            );
            userStoryRepository.saveAll(originalStories);
            System.out.println("✅ Seeded initial user stories.");
        } else {
            // Ensure Ayushi and Head HR exist even if others do
            List<UserStory> existing = userStoryRepository.findAll();
            if (existing.stream().noneMatch(s -> "Ayushi Mishra".equals(s.getName()))) {
                userStoryRepository.save(new UserStory(
                    "Ayushi Mishra",
                    "Senior Associate - Talent Acquisition",
                    "Ayushi is a results-driven Talent Acquisition professional with 4.6+ years of experience in hiring across Cloud, SaaS, Fintech, and Insurtech domains.",
                    "AM", "#db2777", "/images/ayushi_mishra.png"
                ));
            }
            if (existing.stream().noneMatch(s -> "Head HR".equals(s.getName()))) {
                userStoryRepository.save(new UserStory(
                    "Head HR",
                    "Head - Human Resources & CV Review",
                    "As our Head of HR, I oversee the final review of CV details to ensure they meet the highest standards of corporate excellence.",
                    "HR", "#1e3a8a", "/images/head_hr.png"
                ));
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
            }
        );
    }

}
