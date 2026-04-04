package com.careerthon.config;

import com.careerthon.model.User;
import com.careerthon.model.UserStory;
import com.careerthon.repository.UserRepository;
import com.careerthon.repository.UserStoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserStoryRepository userStoryRepository, 
                              UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        return args -> {
            // Seed Users
            if (userRepository.count() == 0) {
                userRepository.save(new User("admin", passwordEncoder.encode("admin123"), "ROLE_USER,ROLE_ADMIN", "System Administrator"));
                userRepository.save(new User("user", passwordEncoder.encode("user123"), "ROLE_USER", "Test User"));
            }

            if (userStoryRepository.count() == 0) {
                userStoryRepository.save(new UserStory(
                    "Abhishek Mishra",
                    "Full Stack Developer & Project Lead",
                    "As the developer and architect of Careerthon, I built this platform to democratize LinkedIn profile optimization. Using Spring Boot and modern web technologies, I created an end-to-end SaaS solution that provides actionable insights to job seekers. The profile scoring algorithm analyzes 15 key dimensions to give users a comprehensive view of their LinkedIn presence.",
                    "AM",
                    "#0A66C2"
                ));

                userStoryRepository.save(new UserStory(
                    "Priyanshu Shekhar",
                    "UI/UX Designer & Frontend Developer",
                    "I focused on crafting an intuitive and visually stunning user experience for Careerthon. From the animated landing page to the interactive report dashboard, every element was designed to make profile analysis accessible and engaging. The responsive design ensures a seamless experience across all devices.",
                    "PS",
                    "#7c3aed"
                ));

                userStoryRepository.save(new UserStory(
                    "Altamash Mallick",
                    "Backend Engineer & Data Analyst",
                    "I contributed to the profile analysis engine and data modeling for Careerthon. The scoring algorithm uses weighted analysis across 15 profile dimensions, benchmarked against industry standards. I also worked on the API layer and database design to ensure scalability and performance.",
                    "AM",
                    "#059669"
                ));

                userStoryRepository.save(new UserStory(
                    "Pradeep Singh",
                    "Manager Delivery & Operations",
                    "This tool gave my profile the edge it needed. The comprehensive analysis was precise and personalized, helping me highlight my strengths in Delivery & Operations. I've seen a clear increase in profile engagement since the updates.",
                    "PS",
                    "#dc2626"
                ));

                userStoryRepository.save(new UserStory(
                    "Harshit Gulati",
                    "Data Analyst, Delhi",
                    "They provided clear and practical advice to make my profile stand out. The expert analysis was thorough and tailored to my needs. I've seen more views and connections from people in my field after implementing the suggested changes.",
                    "HG",
                    "#ea580c"
                ));

                userStoryRepository.save(new UserStory(
                    "Kartik Raja",
                    "Senior Product Manager",
                    "I'm receiving job opportunities from top-quality companies. The tailored feedback made all the difference in highlighting my strengths as a Senior Product Manager. Highly recommended for anyone looking to level up their LinkedIn presence.",
                    "KR",
                    "#0284c7"
                ));
            }
        };
    }
}
