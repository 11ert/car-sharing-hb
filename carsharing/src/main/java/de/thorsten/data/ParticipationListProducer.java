package de.thorsten.data;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import de.thorsten.model.Participation;
import de.thorsten.util.DateUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;


// nur zum testen
@SessionScoped
@Named
public class ParticipationListProducer implements Serializable {

    @Inject
    private ParticipationRepository participationRepository;

    @Inject
    private Logger log;

    private List<Participation> participations;

    private Date trainingDate;

    private int numberOfParticipators;

    private int numberOfDriversBack;

    private int numberOfDriversForth;

    private int numberOfSeatsForthAvailable;

    private int numberOfSeatsBackAvailable;

    private int numberOfSeatsBackRequired;

    private int numberOfSeatsForthRequired;

    public int getNumberOfParticipators() {
        return numberOfParticipators;
    }

    public int getNumberOfDriversBack() {
        return numberOfDriversBack;
    }

    public int getNumberOfDriversForth() {
        return numberOfDriversForth;
    }

    public int getNumberOfSeatsBackRequired() {
        log.info("return numberOfSeatsBackRequired" + numberOfSeatsBackRequired);
        return numberOfSeatsBackRequired;
    }

    public int getNumberOfSeatsForthRequired() {
        log.info("return numberOfSeatsForthRequired=" + numberOfSeatsForthRequired);
        return numberOfSeatsForthRequired;
    }

    public int getNumberOfSeatsBackAvailable() {
        return numberOfSeatsBackRequired;
    }

    public int getNumberOfSeatsForthAvailable() {
        return numberOfSeatsForthRequired;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setTrainingDate(Date x) {
        log.info("setTrainingDate called " + x.toString());
        trainingDate = x;
    }

    public void trainingDateChanged(ValueChangeEvent event) {
        log.info("trainingDateChanged called !");
        if (event.getNewValue() != null) {
            log.info("New TrainingDate = " + event.getNewValue());
            trainingDate = (Date) event.getNewValue();
            retrieveAllParticipatorsForSpecificDate();
            calculateParticipationAttributes();
        }
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public String getTrainingDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(trainingDate);
    }

    /**
     * @PostConstruct wird nach Dependency Injection aufgerufen zur
     * Initialisierung Es darf nur eine solch annotierte Methode geben!
     */
    //@PostConstruct
    public void retrieveAllParticipators() {
        log.info("retrieveAllParticipators");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        participations = participationRepository.getAll();
        calculateParticipationAttributes();
    }
    
    public void retrieveAllParticipatorsForSpecificDate() {
        if (trainingDate != null) {
            log.info("retrieveAllParticipators for " + trainingDate.toString());
            participations = participationRepository.getAllForSpecificDateOrderedByName(trainingDate);
            calculateParticipationAttributes();

            //participations = participationRepository.getAll();
        } else {
            log.info("trainingDate is null");
        }
    }
    

    /**
     * unbedingt notwendig, damit Änderungen aktualisiert werden
     * ohne dem führt ein re-rendering nicht zu einer Aktualisierung der View
     * Warum auch immer...
     * 
     * @param participation 
     */
    public void onParticipationListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Participation participation) {
        //retrieveAllParticipators();
        retrieveAllParticipatorsForSpecificDate();
    }

    private void calculateParticipationAttributes() {
        numberOfParticipators = 0;
        for (Participation p : participations) {
            log.info(" Just read -> " + p.getId() + "; " + p.getTrainingItem().getCurrentDate());
            if (p.isParticipating()) {
                numberOfParticipators++;
            }
            if (p.isDrivingBack()) {
                numberOfSeatsBackAvailable = numberOfDriversBack + p.getPlayer().getCarsize();
            }
            if (p.isDrivingForth()) {
                numberOfSeatsForthAvailable = numberOfDriversForth + p.getPlayer().getCarsize();
            }
        }
        numberOfSeatsBackRequired = numberOfParticipators - numberOfSeatsBackAvailable;
        numberOfSeatsForthRequired = numberOfParticipators - numberOfSeatsForthAvailable;

        log.info("Calculated NumberOfSeatsBackRequired = " + numberOfSeatsBackRequired);
        log.info("Calculated NumberOfSeatsForthRequired = " + numberOfSeatsForthRequired);
        log.info("Calculated NumberOfParticipators = " + numberOfParticipators);

    }
}
