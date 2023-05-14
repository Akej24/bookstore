package com.bookstoreapplication.bookstore.mail_sender;

public interface IEmailSenderService {

    void sendEmail(String toEmail, String subject, String text);

}
