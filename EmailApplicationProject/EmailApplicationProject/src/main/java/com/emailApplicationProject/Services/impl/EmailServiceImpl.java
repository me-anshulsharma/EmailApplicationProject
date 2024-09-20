package com.emailApplicationProject.Services.impl;

import com.emailApplicationProject.Helper.Message;
import com.emailApplicationProject.Services.EmailService;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private final Logger logger = (Logger) LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("work.anshulsharma@gmail.com");
        mailSender.send(simpleMailMessage);
        logger.info("Email has been sent successfully.");
    }

    @Override
    public void sendEmail(String[] to, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("work.anshulsharma@gmail.com");
        mailSender.send(simpleMailMessage);
        logger.info("Email has been sent successfully.");

    }

    @Override
    public void sendEmailWithHTML(String to, String subject, String htmlContent) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(htmlContent, true);
            mimeMessageHelper.setFrom("work.anshulsharma@gmail.com");
            mailSender.send(mimeMessage);
            logger.info("Email has been sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, File file) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), file);
            mimeMessageHelper.setFrom("work.anshulsharma@gmail.com");
            mailSender.send(mimeMessage);
            logger.info("Email has been sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, InputStream is) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);

            File file = new File("src/main/resources/email/Text.png");
            Files.copy(is,file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), file);

            mimeMessageHelper.setFrom("work.anshulsharma@gmail.com");
            mailSender.send(mimeMessage);
            logger.info("Email has been sent successfully.");

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Value("${mail.store.protocol}")
    String protocol;

    @Value("${mail.imaps.host}")
    String host;

    @Value("${mail.imaps.port}")
    String port;

    @Value("${spring.mail.username}")
    String username;

    @Value("${spring.mail.password}")
    String password;

    @Override
    public List<Message> getInboxMessages() {
    //  code for receiving email: get all mail

        Properties configurations = new Properties();

        configurations.setProperty("mail.store.protocol", protocol);
        configurations.setProperty("mail.imaps.host", host);
        configurations.setProperty("mail.imaps.port", port);

        Session session = Session.getDefaultInstance(configurations);

        try {
            Store store = session.getStore();
            store.connect(username, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            jakarta.mail.Message[] messages = inbox.getMessages();

            List<Message> list = new ArrayList<>();

            for(jakarta.mail.Message message : messages){

                String content = getContentFromEmailMessage(message);
                List<String> files = getFilesFromEmailMessage(message);

                list.add(Message.builder().subjects(message.getSubject()).content(content).files(files).build());
            }

            return list;

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getFilesFromEmailMessage(jakarta.mail.Message message) throws MessagingException, IOException {
        List<String> files = new ArrayList<>();

        if(message.isMimeType("multipart/*")){
            Multipart content = (Multipart) message.getContent();

            for (int i = 0; i < content.getCount(); i++) {
                BodyPart bodyPart = content.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())){
                    InputStream is = bodyPart.getInputStream();

                    File file = new File("src/main/resources/email/" + bodyPart.getFileName());

                    // Save the file
                    Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    // URLs
                    files.add(file.getAbsolutePath());
                }
            }

        }
        return files;
    }

    private String getContentFromEmailMessage(jakarta.mail.Message message) throws MessagingException, IOException {
        if(message.isMimeType("text/plain") || message.isMimeType("text/html")){
            return (String)message.getContent();
        } else if (message.isMimeType("multipart/*")) {
            Multipart part = (Multipart)message.getContent();

            for (int i = 0; i < part.getCount(); i++) {
                BodyPart bodyPart = part.getBodyPart(i);
                if(bodyPart.isMimeType("text/plain"))
                    return (String) bodyPart.getContent();

            }
        }
        return null;
    }
}
