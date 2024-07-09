package com.pia.reservation.service.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${pia.email}")
    private String fromEmail;

    private String subject;
    private String body;


    @Async
    public void sendEmail(String toEmail, String hotelName, String fullName, String reservationId)
            throws MessagingException {
        System.out.println("Send Mail THREAD START : " + Thread.currentThread().getName());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        setAttributes(hotelName,fullName, reservationId);

        helper.setFrom(fromEmail);
        helper.addTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
        System.out.println("Send Mail Thread tail : " + Thread.currentThread().getName());
    }

    private void setAttributes(String hotelName, String fullName,String reservationId) {
        String link = "http://localhost:3000/user-page/" + hotelName;

        this.subject = "Pia Yesil Reservation " + hotelName;
        this.body = "<html><body>"
                + "<p>Dear " + fullName + ",</p>"
                + "<p>Congratulations! Your Reservation Has Been Created "
                + "<strong>" + "TEST" + "</strong>"
                + "click <a href=\"" + link + "\">HERE</a>"
                + "l</p>"
                + "<p>Link: <a href=\"" + "link" + "\">" + "link" + "</a></p>"
                + "</body></html>";
    }
}
