
package com.isso.common.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.isso.common.CommonServiceException;
import com.isso.common.IMailService;



@Service("MailService")
public class MailServiceImpl implements IMailService {
	
	// mail smtp host name
	@Value("#{propertyConfigurer['com.isso.common.mail.smtp.hostName']}")
	private String hostName;

	// mail smtp port
	@Value("#{propertyConfigurer['com.isso.common.mail.smtp.port']}")
	private String port;
	
	// mail smtp tls
	@Value("#{propertyConfigurer['com.isso.common.mail.smtp.starttls.enable']}")
	private Boolean tls;
	
	// mail smtp user
	@Value("#{propertyConfigurer['com.isso.common.mail.smtp.user']}")
	private String user;

	// mail smtp password
	@Value("#{propertyConfigurer['com.isso.common.mail.smtp.password']}")
	private String password;
	
	// mail smtp auth
	@Value("#{propertyConfigurer['com.isso.common.mail.smtp.auth']}")
	private Boolean auth;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Override
	public void sendMail(String[] toAddress, String subject, String templateName, Map<String, String> editPointMap) throws CommonServiceException {
		 //Set mail properties
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", tls);
		props.put("mail.smtp.host", hostName);
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", auth);

		Session session = Session.getInstance(props);
		MimeMessage message = new MimeMessage(session);
		try {
			//Set email data 
			message.setFrom(new InternetAddress(user));
			InternetAddress recipientAddresses[] = new InternetAddress[toAddress.length];
			for(int i = 0; i<toAddress.length; i++)
				recipientAddresses[i] = new InternetAddress(toAddress[i]);
			message.addRecipients(Message.RecipientType.TO,recipientAddresses);
			message.setSubject(subject);
			MimeMultipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			
			//HTML mail content
			String htmlText = readEmailFromHtml(templateName,editPointMap);
			messageBodyPart.setContent(htmlText, "text/html;charset=UTF-8");
			multipart.addBodyPart(messageBodyPart); 
			message.setContent(multipart);

			//Conect to smtp server and send Email
			Transport transport = session.getTransport("smtp");
			transport.connect(hostName, user, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("email success");
			}
		catch (Exception ae) {
			ae.printStackTrace();
		}
	}
	
	//Method to replace the values for keys
	private String readEmailFromHtml(String templateName, Map<String, String> editPointMap)
	{
		String msg = getMailContent(templateName);
		try{
			Set<Entry<String, String>> entries = editPointMap.entrySet();
			for(Map.Entry<String, String> entry : entries)
				msg = msg.replace("{" + entry.getKey() + "}", entry.getValue());
			}
		catch(Exception exception){
		exception.printStackTrace();
		}
		return msg;
	}
	
	private String getMailContent(String templateName) {
		StringBuffer mailContent = new StringBuffer();
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String data = null;
		try {
			is = appContext.getResource("classpath:" + templateName).getInputStream();
			isr = new InputStreamReader(is,"UTF-8");
			br = new BufferedReader(isr);
			while ((data = br.readLine()) != null) {
				mailContent.append(data);
				data = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
				is = null;
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (Exception e) {
				}
				isr = null;
			}
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
				}
				br = null;
			}
		}
		return mailContent.toString();
	}
}
