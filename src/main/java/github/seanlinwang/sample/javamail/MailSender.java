package github.seanlinwang.sample.javamail;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;

/**
*
*/
@SuppressWarnings("restriction")
public class MailSender {
	{
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	}

	/**
	 * @param mailInfo
	 * @throws MessagingException
	 */
	public void sendTextMail(MailInfo mailInfo) throws MessagingException {

		MyAuthenticator authenticator = mailInfo.getAuthenticator();
		Properties pro = mailInfo.getProperties();

		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		Message mailMessage = new MimeMessage(sendMailSession);

		Address from = new InternetAddress(mailInfo.getFromAddress());

		mailMessage.setFrom(from);

		Address to = new InternetAddress(mailInfo.getToAddress());
		mailMessage.setRecipient(Message.RecipientType.TO, to);

		mailMessage.setSubject(mailInfo.getSubject());

		mailMessage.setSentDate(new Date());

		String mailContent = mailInfo.getContent();
		mailMessage.setText(mailContent);

		Transport.send(mailMessage);
	}

	/**
	 * @param mailInfo
	 * @throws MessagingException
	 */
	public static void sendHtmlMail(MailInfo mailInfo) throws MessagingException {

		MyAuthenticator authenticator = mailInfo.getAuthenticator();
		Properties props = mailInfo.getProperties();

		Session sendMailSession = Session.getDefaultInstance(props, authenticator);

		Message mailMessage = new MimeMessage(sendMailSession);

		Address from = new InternetAddress(mailInfo.getFromAddress());

		mailMessage.setFrom(from);

		Address to = new InternetAddress(mailInfo.getToAddress());

		mailMessage.setRecipient(Message.RecipientType.TO, to);

		mailMessage.setSubject(mailInfo.getSubject());

		mailMessage.setSentDate(new Date());

		Multipart mainPart = new MimeMultipart();

		BodyPart html = new MimeBodyPart();

		html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);

		mailMessage.setContent(mainPart);

		Transport.send(mailMessage);
	}

	public static void main(String[] args) throws MessagingException, IOException {
		Set<String> set = getEmailAdress();

		Set<String> setIgnore = getEmailIgnores();

		System.out.println("total address\t" + set.size());
		System.out.println("ignore address\t" + setIgnore.size());

		int i = 1;
		for (String toAddress : set) {
			if (setIgnore.contains(toAddress)) {
				System.out.println("ignore\t" + toAddress);
				continue;
			}
			try {
				MailInfo mailInfo = new MailInfo();
				mailInfo.setMailServerHost("smtp.gmail.com");
				mailInfo.setMailServerPort(587);
				mailInfo.setUsername("xxxxx");
				mailInfo.setPassword("xxxx");
				mailInfo.setFromAddress("xxxxx");
				mailInfo.setToAddress(toAddress);
				mailInfo.setSubject("xxxx");
				mailInfo.setContent("xxxxx");
				MailSender.sendHtmlMail(mailInfo);

				System.out.println(i + "\t" + toAddress + "\t" + new Date());
				i++;
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private static Set<String> getEmailIgnores() throws IOException {
		Set<String> setIgnore = new HashSet<String>();
		FileInputStream ignoreinput = new FileInputStream("xxx.csv");
		DataInputStream ignoreIn = new DataInputStream(ignoreinput);
		BufferedReader ingnoreBr = new BufferedReader(new InputStreamReader(ignoreIn));
		// Read File Line By Line
		String strLine = null;
		while ((strLine = ingnoreBr.readLine()) != null) {
			setIgnore.add(StringUtils.trim(strLine));
		}
		return setIgnore;
	}

	private static Set<String> getEmailAdress() throws IOException {
		Set<String> set = new HashSet<String>();
		FileInputStream fstream = new FileInputStream("/xxx.csv");
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {

			String[] segs = StringUtils.split(strLine, ',');
			if (segs == null || segs.length < 3) {
				continue;
			}
			String email = StringUtils.trimToNull(segs[2]);
			if (email == null || !email.contains("@")) {
				continue;
			}
			set.add(StringUtils.trim(email));
		}
		return set;
	}
}
