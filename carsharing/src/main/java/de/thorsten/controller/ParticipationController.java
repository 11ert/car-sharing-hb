/*
 * noch nicht benötigt - erst wenn Teilnahme aus UI erstellt wird.
 */
package de.thorsten.controller;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.thorsten.model.Participation;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation

@ManagedBean(name = "participationController")
@ViewScoped
public class ParticipationController implements Serializable {

    @Inject
    private Logger log;
   
    private boolean drivingBack;
    
    private boolean drivingForth;
    
    private int currentParticipationIndex;
    
    private Participation currentParticipation;

    private Participation editedParticipation;
    
    
    public void updateParticipation() {
        log.info("updateParticipation! Not Implemented yet");
    }
  
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

    /**
     * @param currentParticipation the currentParticipation to set
     */
    public void setCurrentParticipation(Participation currentParticipation) {
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

}
