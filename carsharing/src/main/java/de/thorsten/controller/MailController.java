package de.thorsten.controller;

import de.thorsten.data.MailConfigException;
import de.thorsten.data.MailConfigRepository;
import de.thorsten.model.MailConfig;
import static de.thorsten.model.News_.mailingList;
import de.thorsten.service.MailService;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.mail.Session;

@Model
public class MailController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private Logger log;
    
    @Inject
    private MailService mailService;
    
    @Inject
    private MailConfigRepository mailConfigRepository;
    
    private MailConfig mailConfig;

    @Produces
    @Named
    private String newMail;
    
    @Resource(mappedName = "java:/mail/Gmail")
    private Session mailSession;

    @PostConstruct
    public void init() {
        try {
        mailConfig = mailConfigRepository.findMailConfigForNews();
        } catch (MailConfigException e) {
            e.printStackTrace();
        }
    }
    
    @Asynchronous
    public void send() throws Exception {
        mailService.send(newMail, mailingList);
    }
    
}
