/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.service;

import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Thorsten Elfert
 */
@Stateless
public class MailSender {

    @Resource(mappedName = "java:/mail/Gmail")
    private Session mailSession;

    @Inject
    Logger log;

    //@Asynchronous
    public void sendMail(final String mailFrom, final String mailTo, final String replyTo, final String subject, String text) throws MessagingException {
        log.info("MailSession = " + mailSession.toString());
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(mailFrom));
            Address toAddress = new InternetAddress(mailTo);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setContent(text, "text/plain");
            Transport.send(message);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
            log.info("<font color=red>Error in Sending Mail: " + e + "</font>");
        }
    }

}
