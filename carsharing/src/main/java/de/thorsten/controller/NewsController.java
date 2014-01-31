package de.thorsten.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.thorsten.model.News;
import de.thorsten.service.NewsService;
import java.util.logging.Logger;

@Model
public class NewsController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private NewsService newsService;
    
    @Inject
    private Logger log;

    @Produces
    @Named
    private News newNews;
    
    @PostConstruct
    public void initNewNews() {
        newNews = new News();
    }

    public void register() throws Exception {
        try {
            newsService.update(newNews);
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

    
}
