package com.ecommerce.service;
import com.ecommerce.dto.ProductEnquiryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class NotificationServiceTest {


    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SpringTemplateEngine templateEngine;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(notificationService, "from", "noreply@example.com");
        ReflectionTestUtils.setField(notificationService, "to", "user@example.com");
        ReflectionTestUtils.setField(notificationService, "subject", "Test Subject");
        ReflectionTestUtils.setField(notificationService, "mailTriggerEnabled", true);
    }

    @Test
    void notifySupplyTeam() {
        // Arrange

        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("email body");

        // Act
        notificationService.notifySupplyTeam(getProductEnquiryDto());

        // Assert
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void errorInMailSend() {
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("email body");
        doThrow(new MailException("Test exception") {}).when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        notificationService.notifySupplyTeam(getProductEnquiryDto());

        // Assert
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    private ProductEnquiryDto getProductEnquiryDto() {
        ProductEnquiryDto productEnquiry = new ProductEnquiryDto();
        productEnquiry.setProductId(1L);
        productEnquiry.setQty(10);
        return productEnquiry;
    }
}