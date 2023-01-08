package br.com.springtemplateemail.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import br.com.springtemplateemail.model.EmailTemplateModel;
import br.com.springtemplateemail.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private EmailService service;

	@PostMapping("/sendTemplateEmail")
	public ResponseEntity<EmailTemplateModel> sendSimpleEmail(@RequestBody @Valid EmailTemplateModel emailTemplate) {
		
		Map<String,  File> attachment = new HashMap<>();
		attachment.put( "ImageTemplateSuccess",new File("./src/main/resources/templates/img-templates/sendTemplateEmailSuccess.png"));
		attachment.put( "ImageWelcome", new File("./src/main/resources/templates/img-templates/welcome2022.png"));
		emailTemplate.setAttachment(attachment);
		
		Context atribute = new Context();
		atribute.setVariable("subject", emailTemplate.getSubject());
		atribute.setVariable("emailFrom", emailTemplate.getEmailFrom());
		atribute.setVariable("emailTo", emailTemplate.getEmailTo());
		
		
		EmailTemplateModel obj = service.sendTemplateEmail(emailTemplate, atribute);
		return ResponseEntity.ok().body(obj);
	}
}

