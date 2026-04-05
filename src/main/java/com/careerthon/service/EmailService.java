package com.careerthon.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendReport(String toEmail, String reportUrl, String userName) {
        try {
            SimpleMailMessage m = new SimpleMailMessage();
            m.setFrom("Careerthon <no-reply@careerthon.onrender.com>");
            m.setTo(toEmail);
            m.setSubject("Your LinkedIn Profile Review is Ready! ✨");
            m.setText("Hi " + userName + ",\n\n" +
                      "Great news! Your LinkedIn profile review is now complete. " +
                      "You've taken the first step towards a more powerful professional presence.\n\n" +
                      "You can view your detailed report and actionable insights here:\n" +
                      reportUrl + "\n\n" +
                      "If you have any questions, feel free to reach out to us.\n\n" +
                      "Best regards,\n" +
                      "The Careerthon Team ⚡");
            mailSender.send(m);
            System.out.println("✅ Real email successfully sent to: " + toEmail);
            return true;
        } catch (Exception e) {
            System.err.println("❌ Failed to send real email to " + toEmail + ": " + e.getMessage());
            return false;
        }
    }
}
