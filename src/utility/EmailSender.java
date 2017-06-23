package utility;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {

	private static String FROM = "EMAIL_ADDRESS_FOR_SENDING_EMAILS";
	private static String PASSWORD = "EMAIL_PASSWORD";
	private static String TO = "EMAIL RECEIVER";
	private static String HOST = "SMTP_SERVER";


	
	public static void sendEmail(String subject, String body){
		Properties props = System.getProperties();
		
		props.put("mail.smtp.starttls.enable", "true");

		props.put("mail.smtp.host", HOST);

		props.put("mail.smtp.user", FROM);

		props.put("mail.smtp.password", PASSWORD);

		props.put("mail.smtp.port", "25");

		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props);

		MimeMessage message = new MimeMessage(session);
		
		try {
		    
			message.setFrom(new InternetAddress(FROM));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			
			message.setSubject(subject);

			message.setText(body);

			BodyPart objMessageBodyPart = new MimeBodyPart();

			objMessageBodyPart.setText("Please Find The Attached Report File!");

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(objMessageBodyPart);

			objMessageBodyPart = new MimeBodyPart();

			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");

			transport.connect(HOST, FROM, PASSWORD);

			transport.sendMessage(message, message.getAllRecipients());

			transport.close();
		} catch(AddressException ae) {
			ae.printStackTrace();
		} catch(MessagingException me){
			me.printStackTrace();
		}
	}
}
