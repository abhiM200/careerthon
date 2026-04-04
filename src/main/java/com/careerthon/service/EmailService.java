package com.careerthon.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    /**
     * Simulated email delivery — logs the action but does not actually send.
     * To enable real sending, configure SMTP in application.properties.
     */
    public boolean sendReport(String toEmail, String reportUrl, String userName) {
        System.out.println("📧 [EMAIL STUB] Report email sent to: " + toEmail);
        System.out.println("   Report URL: " + reportUrl);
        System.out.println("   User: " + userName);
        // In production, use JavaMailSender here
        return true;
    }
}
