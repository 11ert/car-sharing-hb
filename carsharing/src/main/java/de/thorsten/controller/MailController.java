package de.thorsten.controller;

import de.thorsten.model.MailingList;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Model
public class MailController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private Logger log;

    @Produces
    @Named
    private String newMail;
    
    @Resource(mappedName = "java:/mail/Gmail")
    private Session mailSession;

    @PostConstruct
    public void init() {
        MailingList mailingList = new MailingList();
        
        
    }
    
    
    
    @Asynchronous
    public void send() throws Exception {
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("mailFrom"));
            Address toAddress = new InternetAddress("thorsten.elfert@gmail.com");
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject("subject");
            message.setContent(newMail, "text/plain");
            Transport.send(message);
            log.info("<font color=red>Sent Mail</font>");
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
            log.info("<font color=red>Error in Sending Mail: " + e + "</font>");
        }
    }
    
}
