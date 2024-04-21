package com.ecommerce.service;

import com.ecommerce.dto.ProductEnquiryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
public class NotificationService {

    @Value("${mail.trigger.enabled:false}")
    private boolean mailTriggerEnabled;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.to}")
    private String to;

    @Value("${mail.subject}")
    private String subject;

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    public NotificationService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }
    public void notifySupplyTeam(ProductEnquiryDto productEnquiry) {
        Context context = new Context();
        context.setVariable("productEnquiry", productEnquiry);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject + productEnquiry.getProductId());
        String body = templateEngine.process("emailTemplate", context);
        message.setText(body);
        try {
            if (mailTriggerEnabled)
            {
                mailSender.send(message);
            }
        } catch (MailException e) {
            log.error("An error occurred while sending email notification: {}", e.getMessage());
        }
    }
}
