package com.jazz.kafkases.services.aws;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SesException;

public class SESSender {
    public static void sendMessage(String message) throws AddressException, MessagingException {
        String bodyText = "EMAIL BY PRODUCER";

        String bodyHTML = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h1>TEST</h1>"
                + "<p>SENT BY KAFKA-JAVA-PRODUCER</p>"
                + "</body>"
                + "</html>";

        try {
            send("example@gmail.com", "example@gmail.com", "Testing 123", bodyText, bodyHTML);

            System.out.println("Email sent.");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void send(String sender,
            String recipient,
            String subject,
            String bodyText,
            String bodyHTML) throws AddressException, MessagingException, IOException {

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);

        message.setSubject(subject, "UTF-8");
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(bodyText, "text/plain; charset=UTF-8");

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(bodyHTML, "text/html; charset=UTF-8");

        MimeMultipart msgBody = new MimeMultipart();
        msgBody.addBodyPart(textPart);
        msgBody.addBodyPart(htmlPart);

        message.setContent(msgBody);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();

            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .build();

            Client.getSESClient().sendRawEmail(rawEmailRequest);
            Client.getSESClient().close();
        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}