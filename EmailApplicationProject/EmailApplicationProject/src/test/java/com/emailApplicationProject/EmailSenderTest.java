package com.emailApplicationProject;

import com.emailApplicationProject.Helper.Message;
import com.emailApplicationProject.Services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailService emailService;

    @Test
    void EmailSendTest(){
        System.out.println("Sending Email...");
        emailService.sendEmail("anshul.sharma2023@vitstudent.ac.in", "Testing mail by Anshul", "Hey, This is a testing mail from Java Email Application Project.");
    }

    @Test
    void sendHtmlInMain(){
        String html = ""+
                "<h1 style='color:red; border:1px solid red;'>Welcome to another Mail with HTML function.</h1>"
                +"";
        emailService.sendEmailWithHTML("anshul.sharma2023@vitstudent.ac.in", "Testing mail by Anshul", html);
    }

    @Test
    void sendEmailWithFile(){
        emailService.sendEmailWithFile("anshul.sharma2023@vitstudent.ac.in", "Testing mail by Anshul", "This email contains file", new File("src/main/resources/static/Images/W1.jpg"));

    }

    @Test
    void sendEmailWithFileWithStream(){
        File file = new File("src/main/resources/static/Images/W1.jpg");
        try {
            InputStream is = new FileInputStream(file);
            emailService.sendEmailWithFile("anshul.sharma2023@vitstudent.ac.in", "Testing mail by Anshul", "This email contains file",is);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    // Email receiving test
    @Test
    void getInbox(){
        List<Message> inboxMessages = emailService.getInboxMessages();
        inboxMessages.forEach(item -> {
            System.out.println(item.getSubjects());
            System.out.println(item.getContent());
            System.out.println(item.getFiles());
            System.out.println("__________________________________________");
        });
    }
}
