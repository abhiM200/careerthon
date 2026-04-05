package com.careerthon.config;

import com.careerthon.model.User;
import com.careerthon.model.UserStory;
import com.careerthon.repository.UserRepository;
import com.careerthon.repository.UserStoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserStoryRepository userStoryRepository, 
                              UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        return args -> {
            // Seed Users
            // Seed/Update Admin User
            userRepository.findByUsername("admin").ifPresentOrElse(
                admin -> {
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    userRepository.save(admin);
                },
                () -> userRepository.save(new User("admin", passwordEncoder.encode("admin123"), "ROLE_USER,ROLE_ADMIN", "System Administrator"))
            );
            
            if (userRepository.findByUsername("user").isEmpty()) {
                userRepository.save(new User("user", passwordEncoder.encode("user123"), "ROLE_USER", "Test User"));
            }

            // Upsert User Stories (Ensures they exist or are completely up to date)
            ensureStoryExists("Abhishek Mishra", "Full Stack Developer & Project Lead", "As the developer and architect of Careerthon, I built this platform to democratize LinkedIn profile optimization. Using Spring Boot and modern web technologies, I created an end-to-end SaaS solution that provides actionable insights to job seekers. The profile scoring algorithm analyzes 15 key dimensions to give users a comprehensive view of their LinkedIn presence.", "AM", "#0A66C2", userStoryRepository);
            ensureStoryExists("Priyanshu Shekhar", "UI/UX Designer & Frontend Developer", "I focused on crafting an intuitive and visually stunning user experience for Careerthon. From the animated landing page to the interactive report dashboard, every element was designed to make profile analysis accessible and engaging. The responsive design ensures a seamless experience across all devices.", "PS", "#7c3aed", userStoryRepository);
            ensureStoryExists("Altamash Mallick", "Backend Engineer & Data Analyst", "I contributed to the profile analysis engine and data modeling for Careerthon. The scoring algorithm uses weighted analysis across 15 profile dimensions, benchmarked against industry standards. I also worked on the API layer and database design to ensure scalability and performance.", "AM", "#059669", userStoryRepository);
            ensureStoryExists("Pradeep Singh", "Manager Delivery & Operations", "This tool gave my profile the edge it needed. The comprehensive analysis was precise and personalized, helping me highlight my strengths in Delivery & Operations. I've seen a clear increase in profile engagement since the updates.", "PS", "#dc2626", userStoryRepository);
            ensureStoryExists("Harshit Gulati", "Data Analyst, Delhi", "They provided clear and practical advice to make my profile stand out. The expert analysis was thorough and tailored to my needs. I've seen more views and connections from people in my field after implementing the suggested changes.", "HG", "#ea580c", userStoryRepository);
            ensureStoryExists("Kartik Raja", "Senior Product Manager", "I'm receiving job opportunities from top-quality companies. The tailored feedback made all the difference in highlighting my strengths as a Senior Product Manager. Highly recommended for anyone looking to level up their LinkedIn presence.", "KR", "#0284c7", userStoryRepository);
            
            // Newly updated specific profiles
            ensureStoryExists("Prashant Kumar", "Sr Analyst at LTM", "Careerthon's profile review completely transformed how recruiters see my profile. After 4 years as an Analyst, I wanted to showcase my impact clearly for senior roles, and this tool gave me the perfect step-by-step guidance.", "PK", "#c026d3", userStoryRepository);
            ensureStoryExists("Rishabh Jaiswal", "Sr Software Developer at Cognizant", "Having been in the industry for 3 years, my LinkedIn profile was a bit outdated. The detailed feedback and scoring from Careerthon helped me optimize my keywords and headline, leading to a significant increase in relevant connection requests.", "RJ", "#0ea5e9", userStoryRepository);
            ensureStoryExists("Binit Mishra", "Sr Software Developer at Accenture Germany", "As an ex-TCS employee with 8 years of experience, moving to an international role required my profile to stand out globally. Careerthon's deep analysis and actionable insights were crucial in refining my digital presence for opportunities like Accenture.", "BM", "#10b981", userStoryRepository);
        };
    }

    private void ensureStoryExists(String name, String role, String story, String initials, String color, UserStoryRepository repo) {
        List<UserStory> existing = repo.findAll();
        boolean exists = existing.stream().anyMatch(u -> u.getName().equals(name));
        if (!exists) {
            repo.save(new UserStory(name, role, story, initials, color));
        } else {
            // Properly update the existing story to reflect dynamic new textual refinements
            UserStory s = existing.stream().filter(u -> u.getName().equals(name)).findFirst().get();
            s.setRole(role);
            s.setStory(story);
            s.setAvatarInitials(initials);
            s.setAvatarColor(color);
            repo.save(s);
        }
    }
}
