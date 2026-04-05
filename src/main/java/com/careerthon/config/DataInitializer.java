package com.careerthon.config;

import com.careerthon.model.UserStory;
import com.careerthon.repository.UserStoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserStoryRepository userStoryRepository;

    public DataInitializer(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    @Override
    public void run(String... args) {
        // We wipe old placeholders and ensure our 4 EXPERT STORIES are always visible.
        // This ensures Shristi Jha and other premium testimonials are never skipped.
        userStoryRepository.deleteAll();
        
        List<UserStory> expertStories = List.of(
            new UserStory(
                "Shristi Jha",
                "Final Year B.Tech Student, KIIT Bhubaneswar",
                "As a final year B.Tech student at KIIT Bhubaneswar, I was struggling to get noticed by top MNCs. Careerthon provided me with a detailed LinkedIn audit and a technical roadmap that completely changed my approach. The expert suggestions for my about section were a game-changer.",
                "SJ",
                "#10b981",
                "/images/testimonials/shristi.jpg"
            ),
            new UserStory(
                "Ananya Dubey",
                "Senior Product Manager at Tech Corp",
                "Careerthon transformed my LinkedIn visibility! Before the review, my profile was barely getting any views. After applying the expert headline and summary recommendations, recruiters started reaching out to me for Product Management roles.",
                "AD",
                "#0A66C2",
                "/images/testimonials/ananya.jpg"
            ),
            new UserStory(
                "Rahul Sharma",
                "Cloud Solutions Architect",
                "The ATS resume scan is incredibly accurate. It helped me realize that my CV was missing critical keywords for Cloud Architecture roles. I used the Expert Skill Gap Analyzer to identify exactly what to learn next. Careerthon is an essential tool for modern career development.",
                "RS",
                "#f59e0b",
                null
            ),
            new UserStory(
                "Priya Singh",
                "Operations Lead",
                "Finally, a service that provides expert career advice without the AI fluff. The Mock Interview tool helped me prep for my last two interview rounds with confidence. The responses are truly professional and aligned with what hiring managers are actually looking for.",
                "PS",
                "#ef4444",
                null
            )
        );
        userStoryRepository.saveAll(expertStories);
    }
}
