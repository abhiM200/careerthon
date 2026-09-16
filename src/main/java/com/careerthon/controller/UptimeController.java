package com.careerthon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UptimeController {

    private static final long START_TIME_MS = ManagementFactory.getRuntimeMXBean().getStartTime();

    @GetMapping("/api/uptime")
    public ResponseEntity<Map<String, Object>> getUptime() {
        long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
        long seconds = uptimeMs / 1000;
        long days = seconds / (24 * 3600);
        long hours = (seconds % (24 * 3600)) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        String formattedUptime;
        if (days > 0) {
            formattedUptime = String.format("%dd %dh %dm", days, hours, minutes);
        } else if (hours > 0) {
            formattedUptime = String.format("%dh %dm %ds", hours, minutes, secs);
        } else if (minutes > 0) {
            formattedUptime = String.format("%dm %ds", minutes, secs);
        } else {
            formattedUptime = String.format("%ds", secs);
        }

        String startTimeStr = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                .format(Instant.ofEpochMilli(START_TIME_MS).atZone(ZoneId.systemDefault()));

        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("uptime", formattedUptime);
        response.put("uptimeMillis", uptimeMs);
        response.put("startTime", startTimeStr);

        return ResponseEntity.ok(response);
    }

    public static String getFormattedUptime() {
        long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
        long seconds = uptimeMs / 1000;
        long days = seconds / (24 * 3600);
        long hours = (seconds % (24 * 3600)) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        if (days > 0) {
            return String.format("%dd %dh %dm", days, hours, minutes);
        } else if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes, secs);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, secs);
        } else {
            return String.format("%ds", secs);
        }
    }
}
