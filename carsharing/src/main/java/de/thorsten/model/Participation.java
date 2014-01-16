/*
 * Die Teilnahme am Traing
 * Wer nimmt wann teil und fährt evtl. ?
 */
package de.thorsten.model;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *
 * @author Thorsten
 */
@Entity
public class Participation implements Serializable {

    private static long serialVersionUID = 1L;

    @Transient
    @Inject
    private Logger log;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Member player;

    @OneToOne
    private SportsEvent trainingItem;

    private boolean participating;
    
    private boolean notParticipating;

    private boolean drivingBack;

    private boolean drivingForth;

    private Date lastChanged;
    
    public Participation() {
        this.drivingForth = Boolean.FALSE;
        this.drivingBack = Boolean.FALSE;
        this.participating = Boolean.FALSE;
        this.notParticipating = Boolean.FALSE;
        this.lastChanged = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participation)) {
            return false;
        }
        Participation other = (Participation) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Participation{" + "id=" + id + ", player=" + player + ", trainingItem=" + trainingItem + ", participating=" + participating + ", notParticipating=" + notParticipating + ", drivingBack=" + drivingBack + ", drivingForth=" + drivingForth + ", lastChanged=" + lastChanged + '}';
    }

   
    /**
     * @return the player
     */
    public Member getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Member player) {
        this.player = player;
    }

    /**
     * @return the trainingItem
     */
    public SportsEvent getTrainingItem() {
        return trainingItem;
    }

    /**
     * @param training the trainingItem to set
     */
    public void setTraining(SportsEvent trainingItem) {
        this.trainingItem = trainingItem;
    }

    /**
     * @return the participating
     */
    public boolean isParticipating() {
        return participating;
    }

    /**
     * @param participating the participating to set
     */
    public void setParticipating(boolean participating) {
        this.participating = participating;
    }

    /**
     * @return the drivingBack
     */
    public boolean isDrivingBack() {
        return drivingBack;
    }

    /**
     * @param drivingBack the drivingBack to set
     */
    public void setDrivingBack(boolean drivingBack) {
        this.drivingBack = drivingBack;
    }

    /**
     * @return the drivingForth
     */
    public boolean isDrivingForth() {
        return drivingForth;
    }

    /**
     * @param drivingForth the drivingForth to set
     */
    public void setDrivingForth(boolean drivingForth) {
        this.drivingForth = drivingForth;
    }

    // Funktioniert nicht!
    @Named
    public void useDrivingForthChanged(ValueChangeEvent ev) {
        log.info("!!!!!!ValueChangeListener aufgerufen!!!!!");
        Boolean useChangedDrivingForth = (Boolean) ev.getNewValue();
        if (useChangedDrivingForth != null) {
            this.drivingForth = useChangedDrivingForth;
        }
        FacesContext.getCurrentInstance().renderResponse();
    }

    /**
     * @return the lastChanged
     */
    public Date getLastChanged() {
        return lastChanged;
    }

    /**
     * @param lastChanged the lastChanged to set
     */
    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }

    /**
     * @return the notParticipating
     */
    public boolean isNotParticipating() {
        return notParticipating;
    }

    /**
     * @param notParticipating the notParticipating to set
     */
    public void setNotParticipating(boolean notParticipating) {
        this.notParticipating = notParticipating;
    }

}
