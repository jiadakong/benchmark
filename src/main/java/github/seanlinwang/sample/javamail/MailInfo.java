package github.seanlinwang.sample.javamail;

import java.util.Properties;

public class MailInfo {

	private String mailServerHost;
	private int mailServerPort;

	private String fromAddress;

	private String toAddress;

	private String username;

	private String password;

	private boolean validate = true;

	private boolean ssl = false;

	private String subject;

	private String content;

	private String[] attachFileNames;

	/**
      *
      */
	public Properties getProperties() {
		// Properties p = new Properties();
		// if (ssl) {
		// p.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		// p.put("mail.smtp.socketFactory.fallback", "false");
		// p.put("mail.smtp.socketFactory.port", "" + this.mailServerPort);
		// } else {
		// p.put("mail.smtp.port", "" + this.mailServerPort);
		// }
		// p.put("mail.smtp.host", this.mailServerHost);
		//
		// p.put("mail.smtp.auth", validate ? "true" : "false");
		// return p;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.connectiontimeout", "30000");
		props.setProperty("mail.smtp.timeout", "30000");
		props.put("mail.smtp.host", this.mailServerHost);
		props.put("mail.smtp.port", this.mailServerPort);
		return props;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public int getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(int mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

	public MyAuthenticator getAuthenticator() {
		return new MyAuthenticator(this.username, this.getPassword());
	}

}
