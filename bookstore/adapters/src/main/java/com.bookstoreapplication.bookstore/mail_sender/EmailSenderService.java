package com.bookstoreapplication.bookstore.mail_sender;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EmailSenderService implements IEmailSenderService{

    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bookstore.application.akej@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
        log.info("Mail sent successfully");
    }
}
