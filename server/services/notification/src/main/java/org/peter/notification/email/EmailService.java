package org.peter.notification.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.peter.notification.client.EmailGeneratorClient;
import org.peter.notification.kafka.LowStockProduct;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final EmailGeneratorClient emailGeneratorClient;

    @Async
    public void sendEmailConfirmation(String sendTo, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());


        messageHelper.setFrom("notification@peter.com");

        final String templateName = EmailTemplates.EMAIL_CONFIRMATION.getTemplateName();

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("sendTo", sendTo);

        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(EmailTemplates.EMAIL_CONFIRMATION.getSubject());

        sendEmail(sendTo, mimeMessage, messageHelper, templateName, context);
    }

    @Async
    public void sendEmailLowStock(LowStockProduct lowStockProduct) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        messageHelper.setFrom("notification@peter.com");

        final String templateName = EmailTemplates.LOW_STOCK_ALERT.getTemplateName();

        Map<String, Object> model = new HashMap<>();
        model.put("userName", lowStockProduct.username());
        model.put("productName", lowStockProduct.productName());
        model.put("currentStock", lowStockProduct.currentStock());
        model.put("minStockLevel", lowStockProduct.minStock());
        model.put("reordering", lowStockProduct.maxStock() - lowStockProduct.currentStock());

        byte[] pdfFile = emailGeneratorClient.getOrderPdf(lowStockProduct);
        messageHelper.addAttachment(lowStockProduct.productName().toLowerCase() + "-reorder", new ByteArrayDataSource(pdfFile, "application/pdf"));

        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(EmailTemplates.EMAIL_CONFIRMATION.getSubject());

        String userEmail = lowStockProduct.userEmail();

        sendEmail(userEmail, mimeMessage, messageHelper, templateName, context);
    }

    private void sendEmail(String sendTo, MimeMessage mimeMessage, MimeMessageHelper messageHelper, String templateName, Context context) {
        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(sendTo);
            mailSender.send(mimeMessage);
            log.info("Email confirmation sent to {}", sendTo);
        } catch (MessagingException e) {
            log.warn("Email confirmation failed to send to {}", sendTo, e);
        }
    }

}
