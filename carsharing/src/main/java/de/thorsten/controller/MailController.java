package de.thorsten.controller;

import de.thorsten.data.MailConfigException;
import de.thorsten.data.MailConfigRepository;
import de.thorsten.data.MailingListRepository;
import de.thorsten.model.MailConfig;
import de.thorsten.model.MailingList;
import de.thorsten.service.MailService;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;

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
    
    @Inject 
            private MailingListRepository mailingListRepository;
    
    private Long mailingListId;
    private MailingList mailingList;
    
    private MailConfig mailConfigForNews;

    
    @Produces
    @Named
    private String newMail;


    @PostConstruct
    public void init() {
        try {
        mailConfigForNews = mailConfigRepository.findMailConfigForNews();
        } catch (MailConfigException e) {
            e.printStackTrace();
        }
        mailingList = mailingListRepository.findById(mailingListId);
    }
    
    @Asynchronous
    public void send() throws Exception {
        mailService.send(newMail, mailingList.geteMailAdresses(), mailConfigForNews);
    }
    
    public void setMailingListid(Long id) {
        mailingListId = id;
    }
    
}
