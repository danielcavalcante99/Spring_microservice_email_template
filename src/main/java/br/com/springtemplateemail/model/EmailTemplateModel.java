package br.com.springtemplateemail.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.springtemplateemail.model.enums.StatusEmail;
import lombok.Data;

@Data
public class EmailTemplateModel {

	@NotBlank
	private String subject;
	@NotBlank
	@Email
	private String emailFrom;
	@NotBlank
	@Email
	private String emailTo;
	private String html;
	private Map<String, File> attachment = new HashMap<>();
	private StatusEmail status;

}
