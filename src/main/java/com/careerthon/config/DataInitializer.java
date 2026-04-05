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
        // We ensure all 8 stories (7 originals + Shristi) are always correctly persisted.
        userStoryRepository.deleteAll();
        
        List<UserStory> allStories = List.of(
            new UserStory(
                "Abhishek Mishra",
                "Full Stack Developer & Project Lead",
                "Careerthon results were incredibly detailed and professional. Transforming my LinkedIn was efficient and the feedback was very high-quality.",
                "AM", "#0A66C2", null
            ),
            new UserStory(
                "Priyanshu Shekhar",
                "UI/UX Designer & Frontend Developer",
                "This tool gave my profile the edge it needed. The comprehensive analysis provided by their expert team was precise and personalized.",
                "PS", "#10b981", null
            ),
            new UserStory(
                "Shristi Jha",
                "Final Year B.Tech Student, KIIT Bhubaneswar",
                "As a final year student at KIIT, I was struggling to get noticed by top MNCs. Careerthon’s expert headline and summary recommendations were a game-changer. I secured a top-tier placement!",
                "SJ", "#ef4444", "/images/testimonials/shristi.jpg"
            ),
            new UserStory(
                "Altamash Mallick", 
                "Backend Engineer & Data Analyst",
                "The ATS score really helped me understand where my resume was failing. A must-have for every serious job seeker today.",
                "AM", "#f59e0b", null
            ),
            new UserStory(
                "Rahul Verma", "Verified Professional",
                "Expert assessment that accurately highlights key profile improvements. A fantastic resource for career positioning.",
                "RV", "#378FE9", null
            ),
            new UserStory(
                "Prashant Kumar", "Verified Professional",
                "The LinkedIn audit is top-notch. It saved me hours of guesswork trying to figure out profile visibility.",
                "PK", "#004182", null
            ),
            new UserStory(
                "Rishabh Jaiswal", "Verified Professional",
                "Actionable recommendations that actually work. My recruiter views doubled within a single week of updates.",
                "RJ", "#111827", null
            ),
            new UserStory(
                "Binit Mishra", "Verified Professional",
                "Highly professional and effective career suite. The expert mock interview provides great confidence booster.",
                "BM", "#64748b", null
            )
        );
        userStoryRepository.saveAll(allStories);
    }
}
