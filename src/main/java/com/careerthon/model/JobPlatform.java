package com.careerthon.model;

public enum JobPlatform {
    ALL("All Platforms", "bg-slate-700", "🌐"),
    NAUKRI("Naukri.com", "bg-blue-600", "🔷"),
    LINKEDIN("LinkedIn", "bg-sky-700", "💼"),
    INDEED("Indeed", "bg-indigo-600", "🔍");

    private final String displayName;
    private final String badgeClass;
    private final String icon;

    JobPlatform(String displayName, String badgeClass, String icon) {
        this.displayName = displayName;
        this.badgeClass = badgeClass;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBadgeClass() {
        return badgeClass;
    }

    public String getIcon() {
        return icon;
    }
}
