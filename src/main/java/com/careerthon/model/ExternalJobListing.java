package com.careerthon.model;

import java.util.List;

public class ExternalJobListing {
    private String id;
    private String title;
    private String company;
    private String location;
    private JobPlatform platform;
    private String salary;
    private String experienceLevel;
    private String jobType;
    private String description;
    private List<String> skills;
    private String postedTime;
    private int matchPercentage;
    private String directApplyUrl;

    public ExternalJobListing() {}

    public ExternalJobListing(String id, String title, String company, String location, JobPlatform platform,
                              String salary, String experienceLevel, String jobType, String description,
                              List<String> skills, String postedTime, int matchPercentage, String directApplyUrl) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.platform = platform;
        this.salary = salary;
        this.experienceLevel = experienceLevel;
        this.jobType = jobType;
        this.description = description;
        this.skills = skills;
        this.postedTime = postedTime;
        this.matchPercentage = matchPercentage;
        this.directApplyUrl = directApplyUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public JobPlatform getPlatform() { return platform; }
    public void setPlatform(JobPlatform platform) { this.platform = platform; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public String getPostedTime() { return postedTime; }
    public void setPostedTime(String postedTime) { this.postedTime = postedTime; }

    public int getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(int matchPercentage) { this.matchPercentage = matchPercentage; }

    public String getDirectApplyUrl() { return directApplyUrl; }
    public void setDirectApplyUrl(String directApplyUrl) { this.directApplyUrl = directApplyUrl; }
}
