package com.emailApplicationProject.Services;

import com.emailApplicationProject.Helper.Message;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface EmailService {
    //    Send email to single person
    void sendEmail(String to, String subject, String message);

    //    Send email to multiple persons
    void sendEmail(String[] to, String subject, String message);

    //    Send email with HTML
    void sendEmailWithHTML(String to, String subject, String htmlContent);

    //    Send email with File attachment
    void sendEmailWithFile(String to, String subject, String message, File file);

    //    Send email with File attachment - Dynamic
    void sendEmailWithFile(String to, String subject, String message, InputStream is);

    //    Mail receiving service
    List<Message> getInboxMessages();

}
