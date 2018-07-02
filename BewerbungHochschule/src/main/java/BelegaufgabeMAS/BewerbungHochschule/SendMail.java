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

public class SendMail implements JavaDelegate {

	/* (non-Javadoc)
	 * Creates the right Email with the help of the setted type and Variables the user gave as input. 
	 * If it's type is 'bestaetigung',
	 * the 'matrikelnummer' will be read out of the execution and an Email with attachment will be send.
	 * If not, either a 'zusage' or 'absage' Mail will be send.
	 */
	@Override
	public void execute(DelegateExecution exec) throws Exception {
		// TODO Auto-generated method stub
		String anrede = (String) exec.getVariable("anrede"), vorname = (String) exec.getVariable("vorname"),
				nachname = (String) exec.getVariable("nachname"), email = (String) exec.getVariable("email"),
				type = (String) exec.getVariable("type"), matrikelnummer = null;

		if (!(type.equalsIgnoreCase("zusage") || type.equalsIgnoreCase("absage"))) {
			matrikelnummer = (String) exec.getVariable("matrikelnummer");
		}

		this.sendEmail(email, type, anrede, vorname, nachname, matrikelnummer);

	}

	/**
	 * sendEmail:
	 * Sends an Email.
	 * At first the right emailparts get loaded through the FileReaderEmail class.
	 * After that, an Email for the right type is constructed and sended.
	 * @param email
	 * @param type
	 * @param anrede
	 * @param vorname
	 * @param nachname
	 * @param matrikelnummer
	 * @return MessageId of Email
	 * @throws EmailException
	 *             an exception
	 */
	public void sendEmail(String email, String type, String anrede, String vorname, String nachname,
			String matrikelnummer) throws EmailException {
		FileReaderEmail fr = new FileReaderEmail();
		HashMap<String, String> emailParts = fr.getEmailParts(type, anrede, vorname, nachname);
		MultiPartEmail mail = null;
		if (type.equalsIgnoreCase("zusage") || type.equalsIgnoreCase("absage")) {
			mail = this.createEmail(emailParts.get("body"), emailParts.get("subject"), email);
		} else {
			File f = new File("C:/Users/Felix/studentCard" + matrikelnummer + ".pdf");
			EmailAttachment attach = new EmailAttachment();
			attach.setPath(f.getPath());
			attach.setDisposition(EmailAttachment.ATTACHMENT);
			attach.setDescription("Studentenausweis");
			attach.setName(f.getName());
			mail = this.createEmail(emailParts.get("body"), emailParts.get("subject"), email, attach);
		}

		mail.send();
	}

	/**
	 * createEmail: creates an email and adds an attachment to it.
	 * 
	 * @param mailtext
	 *            the mailtext
	 * @param subject
	 *            the subject of the mail
	 * @param toEmail
	 *            the mail receiver
	 * @param f
	 *            file, which is the attachment
	 * @return email with attatchment
	 * @throws EmailException
	 *             an Exception
	 */
	public MultiPartEmail createEmail(String mailtext, String subject, String toEmail, EmailAttachment f)
			throws EmailException {
		MultiPartEmail email = this.createEmail(mailtext, subject, toEmail);
		email.attach(f);
		return email;
	}

	/**
	 * createEmail: creates an email with a subject, mailtext, a receiver and the
	 * needed technical details.
	 * 
	 * @param mailtext
	 *            the mailtext
	 * @param subject
	 *            the subject of the mail
	 * @param toEmail
	 *            the mail receiver
	 * @return a MultipartEmail which could be send
	 * @throws EmailException
	 *             an Exception
	 */
	public MultiPartEmail createEmail(String mailtext, String subject, String toEmail) throws EmailException {

		MultiPartEmail email = new MultiPartEmail();
		email.setCharset("utf-8");
		email.setSSL(true);
		email.setSmtpPort(587);
		email.setHostName("mail.htw-berlin.de");
		email.setAuthentication("name", "password");
		email.addTo(toEmail);
		email.setFrom("s0561126@htw-berlin.de");
		email.setSubject(subject);
		email.setMsg(mailtext);
		return email;

	}

}
