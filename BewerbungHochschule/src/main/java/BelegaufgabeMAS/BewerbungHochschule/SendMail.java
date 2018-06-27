package BelegaufgabeMAS.BewerbungHochschule;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;

import io.FileReaderEmail;

public class SendMail implements JavaDelegate{

	@Override
	public void execute(DelegateExecution exec) throws Exception {
		// TODO Auto-generated method stub
		String 	anrede = (String) exec.getVariable("anrede"),
				vorname = (String) exec.getVariable("vorname"),
				nachname = (String) exec.getVariable("nachname"),
				email = (String) exec.getVariable("email"),
				type = (String) exec.getVariable("type"),
				matrikelnummer = null;
		
		if(!(type.equalsIgnoreCase("zusage") || type.equalsIgnoreCase("absage"))) {
			matrikelnummer = (String) exec.getVariable("matrikelnummer");
		}
		
		this.sendEmail(email, type, anrede, vorname, nachname, matrikelnummer);

		
	}
	
	public String sendEmail(String email, String type, String anrede, String vorname, String nachname, String matrikelnummer) throws EmailException {
		FileReaderEmail fr = new FileReaderEmail();
		HashMap<String, String> emailParts = fr.getEmailParts(type, anrede, vorname, nachname);
		MultiPartEmail mail = null;
		if(type.equalsIgnoreCase("zusage") || type.equalsIgnoreCase("absage")) {
			mail = this.createEmail(emailParts.get("body"), emailParts.get("subject"), email);
		}else {
			File f = new File("C:/Users/Felix/studentCard" + matrikelnummer + ".pdf");
			EmailAttachment attach = new EmailAttachment();
			attach.setPath(f.getPath());
			attach.setDisposition(EmailAttachment.ATTACHMENT);
			attach.setDescription("Logs");
			attach.setName(f.getName());
			mail = this.createEmail(emailParts.get("body"), emailParts.get("subject"), email, attach);
		}
		
		return mail.send();
	}
	
	public MultiPartEmail createEmail (String mailtext, String subject, String toEmail, EmailAttachment f) throws EmailException {
		MultiPartEmail email = this.createEmail(mailtext, subject, toEmail);
		email.attach(f);
		return email;
	}

	public MultiPartEmail createEmail(String mailtext, String subject, String toEmail) throws EmailException {
		//https://anleitungen.rz.htw-berlin.de/de/email/e-mail_programm/
		
		MultiPartEmail email = new MultiPartEmail();
		email.setCharset("utf-8");
		email.setSSL(true);
		email.setSmtpPort(587);
//		email.setHostName("mail.gmx.net");
//		email.setAuthentication("XXXX@gmx.de", "XXXXXXX");
//		email.addTo(toEmail);
//		email.setFrom("XXXXXX@gmx.de");
		email.setHostName("mail.htw-berlin.de");
		//email.setAuthentication();
		email.addTo(toEmail);
		email.setFrom("s0561126@htw-berlin.de");
		email.setSubject(subject);
		email.setMsg(mailtext);
		return email;

	}

	
}
