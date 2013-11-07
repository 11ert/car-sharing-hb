/*
 * noch nicht benötigt - erst wenn Teilnahme aus UI erstellt wird.
 */
package de.thorsten.controller;

import javax.inject.Inject;

import de.thorsten.model.Participation;
import de.thorsten.service.ParticipationService;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "participationController")
@ViewScoped
public class ParticipationController implements Serializable {

    private static final long serialVersionUID = -3832235132261771583L;

    @Inject
    private Logger log;

    private boolean drivingBack;

    private boolean drivingForth;

    private int currentParticipationIndex;

   private Participation currentParticipation;

    private Participation editedParticipation;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ParticipationService participationService;

    /**
     * @return the drivesBack
     */
    public boolean isDrivingBack() {
        return drivingBack;
    }

    /**
     * @param drivingBack the drivesBack to set
     */
    public void setDrivingBack(boolean drivesBack) {
        this.drivingBack = drivesBack;
    }

    /**
     * @return the drivesForth
     */
    public boolean isDrivingForth() {
        return drivingForth;
    }

    /**
     * @param drivesForth the drivesForth to set
     */
    public void setDrivingForth(boolean drivingForth) {
        this.drivingForth = drivingForth;
    }

    /**
     * @return the currentParticipation
     */
    public Participation getCurrentParticipation() {
        return currentParticipation;
    }

    /*
    neu - nur zum testen, ob einfacher als via Property Action Listener
    */
    public void editParticipation(Participation editParticipation) {
        log.info("Edit Participation called mit " + editParticipation);
        this.editedParticipation = editParticipation;
    }
    
    
    /**
     * @param currentParticipation the currentParticipation to set
     */
    public void setCurrentParticipation(Participation currentParticipation) {
        log.info("setCurrentParticipaton()" + currentParticipation);
        this.currentParticipation = currentParticipation;
    }

    /**
     * @return the currentParticipationIndex
     */
    public int getCurrentParticipationIndex() {
        log.info("getCurrentParticipationIndex called mit " + currentParticipationIndex);
        return currentParticipationIndex;
    }

    /**
     * @param currentParticipationIndex the currentParticipationIndex to set
     */
    public void setCurrentParticipationIndex(int currentParticipationIndex) {
        log.info("setCurrentParticipationIndex called mit " + currentParticipationIndex);
        this.currentParticipationIndex = currentParticipationIndex;
    }

    /**
     * @return the editedParticipation
     */
    public Participation getEditedParticipation() {
        return editedParticipation;
    }

    /**
     * @param editedParticipation the editedParticipation to set
     */
    public void setEditedParticipation(Participation changedParticipation) {
        log.info("setEditedParticipation " + changedParticipation);
        this.editedParticipation = changedParticipation;
    }

    public void updateParticipation() {
        log.info("updateParticipation called for participation: " + editedParticipation);
        try {
            participationService.update(editedParticipation);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Registration successful");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
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
