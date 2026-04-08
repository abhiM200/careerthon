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
        // Only seed stories on first boot (table empty). Skipping deleteAll saves ~10s startup time.
        if (userStoryRepository.count() == 0) {
            List<UserStory> originalStories = List.of(
                new UserStory(
                    "Abhishek Mishra",
                    "Full Stack Developer & Project Lead",
                    "As the developer and architect of Careerthon, I built this platform to democratize LinkedIn profile optimization. Using Spring Boot and modern web technologies, I created an end-to-end SaaS solution that provides actionable insights to job seekers. The profile scoring algorithm analyzes 15 key dimensions to give users a comprehensive view of their LinkedIn presence.",
                    "AM", "#0A66C2", "/images/abhishek_mishra.jpg"
                ),
                new UserStory(
                    "Priyanshu Shekhar",
                    "UI/UX Designer & Frontend Developer",
                    "I focused on crafting an intuitive and visually stunning user experience for Careerthon. From the animated landing page to the interactive report dashboard, every element was designed to make profile analysis accessible and engaging. The responsive design ensures a seamless experience across all devices.",
                    "PS", "#7c3aed", "/images/priyanshu_shekhar.jpg"
                ),
                new UserStory(
                    "Altamash Mallick",
                    "Backend Engineer & Data Analyst",
                    "I contributed to the profile analysis engine and data modeling for Careerthon. The scoring algorithm uses weighted analysis across 15 profile dimensions, benchmarked against industry standards. I also worked on the API layer and database design to ensure scalability and performance.",
                    "AM", "#059669", "/images/altamash_mallick.jpg"
                ),
                new UserStory(
                    "Binit Mishra",
                    "Accenture Germany (ex-TCS)",
                    "This tool gave my profile the edge it needed. The comprehensive analysis was precise and personalized, helping me highlight my strengths in Delivery & Operations. I've seen a clear increase in profile engagement since the updates.",
                    "BM", "#dc2626", null
                ),
                new UserStory(
                    "Prashant Kumar",
                    "LTM",
                    "They provided clear and practical advice to make my profile stand out. The expert analysis was thorough and tailored to my needs. I've seen more views and connections from people in my field after implementing the suggested changes.",
                    "PK", "#ea580c", null
                ),
                new UserStory(
                    "Risabh Jaishwal",
                    "Cognizant",
                    "I'm receiving job opportunities from top-quality companies. The tailored feedback made all the difference in highlighting my strengths as a Senior Product Manager. Highly recommended for anyone looking to level up their LinkedIn presence.",
                    "RJ", "#0284c7", null
                )
            );
            userStoryRepository.saveAll(originalStories);
            System.out.println("✅ Seeded " + originalStories.size() + " user stories.");
        } else {
            System.out.println("ℹ️ User stories already exist, skipping seed.");
        }

        // Ensure Admin user exists and always has correct credentials
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
