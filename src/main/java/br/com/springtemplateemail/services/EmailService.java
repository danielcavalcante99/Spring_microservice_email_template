package br.com.springtemplateemail.services;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.com.springtemplateemail.model.EmailTemplateModel;
import br.com.springtemplateemail.model.enums.StatusEmail;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	public EmailTemplateModel sendTemplateEmail(EmailTemplateModel emailTemplate, Context atributtes) {
		try {

			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");


			String html = templateEngine.process("template-thymeleaf", atributtes);
			emailTemplate.setHtml(html);

			helper.setSubject(emailTemplate.getSubject());
			helper.setFrom(emailTemplate.getEmailFrom());
			helper.setTo(emailTemplate.getEmailTo());
			helper.setText(emailTemplate.getHtml(), true);

			for (Map.Entry<String,  File> key : emailTemplate.getAttachment().entrySet()) {
				helper.addAttachment(key.getKey(), key.getValue());
			}

			emailTemplate.setStatus(StatusEmail.SENT);

			mailSender.send(mail);
			System.out.println(emailTemplate);

		} catch (MailException | MessagingException e) {
			emailTemplate.setStatus(StatusEmail.ERROR);
			e.printStackTrace();
		}
		return emailTemplate;
	}

}