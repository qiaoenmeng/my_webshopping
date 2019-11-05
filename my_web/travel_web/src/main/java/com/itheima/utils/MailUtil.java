package com.itheima.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 发送邮件工具类
 */
public final class MailUtil {
	private MailUtil(){}
	/**
	 * 发送邮件
	 * 参数一:发送邮件给谁
	 * 参数二:发送邮件的内容
	 */
	public static void sendMail(String toEmail, String emailMsg) throws Exception {
		//1_创建Java程序与163邮件服务器的连接对象
		Properties props = new Properties();
		props.put("mail.smtp.host", "localhost");
		props.put("mail.smtp.auth", "true");
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin", "1234");
			}
		};
		Session session = Session.getInstance(props, auth);
		//2_创建一封邮件  收件人  抄送 密送 主题
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("admin@itheima.com"));//发件人
		message.setRecipient(RecipientType.TO, new InternetAddress(toEmail));//收件人
		message.setSubject("用户激活");//z主题
		message.setContent(emailMsg, "text/html;charset=UTF-8");//内容
		//3_发送邮件
		Transport.send(message);
	}
	/**
	 * 测试类
	 */
	public static void main(String[] args) throws Exception{
		String toEmail = "jack@itheima.com";
		String emailMsg = "测试一下";
		sendMail(toEmail,emailMsg);
		System.out.println("发送成功。。。");
	}
}








