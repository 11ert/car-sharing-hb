package de.thorsten.controller;

import de.thorsten.data.MailingListRepository;
import de.thorsten.model.MailingList;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.thorsten.model.News;
import de.thorsten.service.NewsService;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.event.ValueChangeEvent;
import org.os890.cdi.ext.scope.api.scope.conversation.ViewAccessScoped;

@ViewAccessScoped
@Named
public class NewsController implements Serializable{

    @Inject
    private FacesContext facesContext;

    @Inject
    private NewsService newsService;
    
    @Inject
    private Logger log;

    @Produces
    @Named
    private News newNews;
    
    private MailingList selectedMailingList;
    
    @Inject
    private MailingListRepository mailingListRepository ;
    
    @PostConstruct
    public void initNewNews() {
        newNews = new News();
    }

    public void register() throws Exception {
        try {
            newNews.setMailingList(selectedMailingList);
            newsService.update(newNews);
            
            log.fine("News updated: " + newNews);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "News angelegt!", "News erfolgreich angelegt");
            facesContext.addMessage(null, m);
            initNewNews();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "News konnte nicht angelegt werden");
            facesContext.addMessage(null, m);
        }
    }

    public void setInactive(News editedNews) throws Exception {
        log.fine("editedNews= " + editedNews);
        try {
            if (editedNews.isActiv())
                editedNews.setActiv(false);
            else
                editedNews.setActiv(true);
            newsService.update(editedNews);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "News geändert!", "Änderung erfolgreich");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "News konnte nicht geändert werden");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

    /**
     * @return the selectedMailingList
     */
    public MailingList getSelectedMailingList() {
        return selectedMailingList;
    }

    /**
     * @param selectedMailingList the selectedMailingList to set
     */
    public void setSelectedMailingList(MailingList selectedMailingList) {
        log.fine("set selectedMailingList");
        this.selectedMailingList = selectedMailingList;
    }

    public void mailingListChanged(ValueChangeEvent event) {
        log.fine("mailingListChanged ");
        if (event.getNewValue() != null) {
            Long tmpId = Long.valueOf((String) event.getNewValue());
            selectedMailingList = mailingListRepository.findById(tmpId);
            log.fine("...to new MailingList = " + selectedMailingList.getDescription());
            newNews.setMailingList(selectedMailingList);
        }
    }

}
