package org.digilinq.platform.users.service;

import org.digilinq.platform.users.api.MailService;
import org.digilinq.platform.users.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(User recipient, String subject, String templateName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info@helmataart.nl");
        message.setTo(recipient.email());
        message.setSubject(subject);
        message.setText(createHtmlContent(templateName, recipient));
        mailSender.send(message);
    }

    private String createHtmlContent(String templateName, User recipient) {
        SpringTemplateEngine templateEngine = getSpringTemplateEngine();

        Context ctx = new Context();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();

        ctx.setVariable("todayDate", dateFormat.format(cal.getTime()));
        ctx.setVariable("userName", recipient.username());
        String content = templateEngine.process(templateName, ctx);
        logger.info("Content : {}", content);
        return content;
    }

    private static SpringTemplateEngine getSpringTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        // https://github.com/thymeleaf/thymeleaf/issues/606
        templateResolver.setForceTemplateMode(true);

        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }
}
