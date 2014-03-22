/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.service;

import de.thorsten.model.MailConfig;
import java.util.Collection;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Thorsten
 */
@Stateless
public class MailService {

    @Inject
    private Logger log;

    @Resource(mappedName = "java:/mail/Gmail")
    private Session mailSession;


    @Asynchronous
    public void send(String text, Collection<String> mailingList, MailConfig mailConfig) throws Exception {
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(mailConfig.getSender()));
            Address toAddress;
            for (String mailAdress : mailingList) {
                toAddress = new InternetAddress(mailAdress);
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }
            message.setSubject(mailConfig.getSubject());
            StringBuffer messageText = new StringBuffer(500);
            messageText.append(mailConfig.getPreConfiguredText());
            messageText.append("\n\n");
            messageText.append(text);
            messageText.append("\n");
            messageText.append(mailConfig.getPostConfiguredText());
            messageText.append(mailConfig.getSignature());
            message.setContent(messageText.toString(), "text/plain");
            Transport.send(message);
            log.info("<font color=red>Sent Mail</font>");
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
            log.info("<font color=red>Error in Sending Mail: " + e + "</font>");
        }
    }


    private String convertToCsv(String[] listOfStrings) {
        StringBuffer retString = new StringBuffer(100);
        for (String oneString : listOfStrings) {
            retString.append(oneString);
            retString.append(",");
        }
        // letztes Komma entfernen
        retString.deleteCharAt(retString.length());
        return retString.toString();
    }
}
