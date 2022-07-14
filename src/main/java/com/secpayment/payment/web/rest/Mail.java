package com.secpayment.payment.web.rest;

import com.secpayment.payment.domain.Payment;
import com.secpayment.payment.kafka.Storage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Mail {

    Payment payedGmail;
    Payment payAmount;
    Payment paymentStatus;

    private void sendmail(String payedGmail, String payAmount, String paymentStatus)
        throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(
            props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("henokaddis72@gmail.com", "unaigtqkynbebynl");
                }
            }
        );
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("henokaddis72@gmail.com", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("henokaddis72@gmail.com"));
        msg.setSubject("PayGov Payment Status Confirmation!");
        msg.setContent("Your payment is Confirmed!", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(
            "Dear Madam or Sir," +
            "\r\n" +
            "\n Thank you for using our system, we confirm receiving it with, " +
            "\r\n" +
            " payment amount:  " +
            payAmount +
            "\r\n" +
            " with email Address: " +
            payedGmail +
            "\r\n" +
            "  \n We will anounce your payment status shortly. ",
            "text/html"
        );

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        //MimeBodyPart attachPart = new MimeBodyPart();

        // attachPart.attachFile("/var/tmp/image19.png");
        // multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }

    @PostMapping("/sendemail")
    public String sendEmail(@Valid @RequestBody Payment payment) throws AddressException, MessagingException, IOException {
        Storage storage = new Storage();
        storage.update(payment);
        sendmail(payment.getEmail(), payment.getPaymentAmout(), payment.getApprovalStatus());
        return "Email sent successfully";
    }
}
