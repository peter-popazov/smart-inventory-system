package org.peter.notification.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
