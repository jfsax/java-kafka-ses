package com.jazz.kafkases.services.aws;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import com.jazz.kafkases.util.MessageBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SesException;

public class SESSender {
    public static void sendMessage(String subject, String message) throws AddressException, MessagingException {
        String bodyHTML = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h1>" + subject + "</h1>"
                + "<p>" + message + "</p>"
                + "</body>"
                + "</html>";

        try {
            MimeMessage mimeMessage = MessageBuilder.createMessage("EXAMPLE@gmail.com",
                    "EXAMPLE@gmail.com",
                    subject,
                    bodyHTML);

            sendToSES(mimeMessage);

            System.out.println("Email sent.");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void sendToSES(MimeMessage message) {
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
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
    }
}