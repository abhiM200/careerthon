package com.careerthon.controller;

import com.careerthon.model.ExternalJobListing;
import com.careerthon.service.ExternalJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class JobExplorerController {

    private final ExternalJobService externalJobService;

    public JobExplorerController(ExternalJobService externalJobService) {
        this.externalJobService = externalJobService;
    }

    @GetMapping("/jobs-explorer")
    public String showJobExplorerPage(Model model) {
        List<ExternalJobListing> initialJobs = externalJobService.searchJobs(null, null, "ALL", null, null);
        List<ExternalJobListing> recommendations = externalJobService.getRecommendations(Arrays.asList("Java", "Spring Boot", "React", "Python", "AWS"));
        Map<String, String> searchUrls = externalJobService.generatePlatformSearchUrls("Software Engineer", "Bangalore");

        model.addAttribute("jobs", initialJobs);
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("platformUrls", searchUrls);
        return "jobs_explorer";
    }

    @GetMapping("/api/jobs-explorer/search")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchJobsApi(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "platform", required = false, defaultValue = "ALL") String platform,
            @RequestParam(value = "experience", required = false) String experience,
            @RequestParam(value = "jobType", required = false) String jobType) {

        List<ExternalJobListing> searchResults = externalJobService.searchJobs(query, location, platform, experience, jobType);
        Map<String, String> platformUrls = externalJobService.generatePlatformSearchUrls(query, location);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", searchResults.size());
        response.put("jobs", searchResults);
        response.put("platformUrls", platformUrls);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/jobs-explorer/recommendations")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRecommendationsApi(
            @RequestParam(value = "skills", required = false) String skillsStr) {

        List<String> skillsList = new ArrayList<>();
        if (skillsStr != null && !skillsStr.trim().isEmpty()) {
            skillsList = Arrays.asList(skillsStr.split(","));
        }

        List<ExternalJobListing> recommendations = externalJobService.getRecommendations(skillsList);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", recommendations.size());
        response.put("recommendations", recommendations);

        return ResponseEntity.ok(response);
    }
}
