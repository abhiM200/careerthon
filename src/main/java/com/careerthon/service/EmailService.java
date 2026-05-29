package com.careerthon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(@Autowired(required = false) JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendReport(String toEmail, String reportUrl, String userName) {
        if (mailSender == null) {
            System.err.println("⚠️ Email sending skipped — JavaMailSender not configured (MAIL_PASSWORD env var missing).");
            return false;
        }
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

    public boolean sendRecruiterInvite(String toEmail, String candidateName, String recruiterName, String recruiterEmail, String subject, String messageText) {
        if (mailSender == null) {
            System.err.println("⚠️ Recruiter invite skipped — JavaMailSender not configured (Simulating Success to console).");
            System.out.println("   [Simulated Send] To: " + toEmail + " (" + candidateName + ")");
            System.out.println("   [Simulated Send] ReplyTo: " + recruiterEmail);
            System.out.println("   [Simulated Send] Subject: " + subject);
            System.out.println("   [Simulated Send] Message: " + messageText);
            return true;
        }
        try {
            SimpleMailMessage m = new SimpleMailMessage();
            m.setFrom("Careerthon Outreach <no-reply@careerthon.onrender.com>");
            m.setReplyTo(recruiterEmail);
            m.setTo(toEmail);
            m.setSubject(subject);
            m.setText("Dear " + candidateName + ",\n\n" +
                      messageText + "\n\n" +
                      "Feel free to reply directly to this email to coordinate next steps.\n\n" +
                      "Best regards,\n" +
                      recruiterName + " via Careerthon ⚡\n" +
                      recruiterEmail);
            mailSender.send(m);
            System.out.println("✅ Recruiter email successfully sent to: " + toEmail);
            return true;
        } catch (Exception e) {
            System.err.println("❌ Failed to send recruiter email to " + toEmail + ": " + e.getMessage());
            return false;
        }
    }
}
