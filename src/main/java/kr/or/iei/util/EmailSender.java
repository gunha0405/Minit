package kr.or.iei.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailSender {
	@Autowired
	private JavaMailSender sender;

	public void sendMail(String emailTitle, String receiver, String emailContent) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		try {
			//언제 보냈는지
			helper.setSentDate(new Date());
			//누가
			helper.setFrom(new InternetAddress("hi2wonhi@gmail.com", "MINIT 회원가입 이메일 인증"));
			//누구에게
			helper.setTo(receiver);
			//제목
			helper.setSubject(emailTitle);
			//내용
			helper.setText(emailContent, true);
			//이메일 전송 코드
			sender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
