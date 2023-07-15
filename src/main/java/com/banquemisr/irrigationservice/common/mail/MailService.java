package com.banquemisr.irrigationservice.common.mail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    public void sendEmail(Mail mail) {
        SimpleMailMessage message = createMailMessage(mail);
        sendMail(message);
        log.info("mail {} sent successfully", mail);
    }

    private SimpleMailMessage createMailMessage(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getMessage());
        return message;
    }

    private void sendMail(SimpleMailMessage message) {
        emailSender.send(message);
    }
}
