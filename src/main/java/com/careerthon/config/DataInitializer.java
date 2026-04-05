package com.careerthon.config;

import com.careerthon.model.Testimonial;
import com.careerthon.repository.TestimonialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TestimonialRepository testimonialRepository;

    public DataInitializer(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @Override
    public void run(String... args) {
        // We use 'upsert' logic: if we have 0 testimonials, add the default ones.
        // This prevents wiping out new user-generated testimonials on every restart.
        if (testimonialRepository.count() == 0) {
            List<Testimonial> defaults = List.of(
                new Testimonial(
                    "Careerthon transformed my LinkedIn visibility! Before the review, my profile was barely getting any views. After applying the expert headline and summary recommendations, recruiters started reaching out to me for Product Management roles. Highly recommend it for anyone looking to stand out in a competitive market.",
                    "Ananya Dubey",
                    "Senior Product Manager at Tech Corp",
                    "/images/testimonials/ananya.jpg",
                    "AD",
                    "#0A66C2"
                ),
                new Testimonial(
                    "As a final year B.Tech student at KIIT Bhubaneswar, I was struggling to get noticed by top MNCs. Careerthon provided me with a detailed LinkedIn audit and a technical roadmap that completely changed my approach. The expert suggestions for my about section were a game-changer. I'm now placed at a top-tier firm!",
                    "Shristi Jha",
                    "Final Year B.Tech Student, KIIT Bhubaneswar",
                    "/images/testimonials/shristi.jpg",
                    "SJ",
                    "#10b981"
                ),
                new Testimonial(
                    "The ATS resume scan is incredibly accurate. It helped me realize that my CV was missing critical keywords for Cloud Architecture roles. I used the Expert Skill Gap Analyzer to identify exactly what to learn next. Careerthon is an essential tool for modern career development.",
                    "Rahul Sharma",
                    "Cloud Solutions Architect",
                    null,
                    "RS",
                    "#f59e0b"
                ),
                new Testimonial(
                    "Finally, a service that provides expert career advice without the AI fluff. The Mock Interview tool helped me prep for my last two interview rounds with confidence. The responses are truly professional and aligned with what hiring managers are actually looking for.",
                    "Priya Singh",
                    "Operations Lead",
                    null,
                    "PS",
                    "#ef4444"
                )
            );
            testimonialRepository.saveAll(defaults);
        }
    }
}
