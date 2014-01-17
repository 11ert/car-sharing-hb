package de.thorsten.data;

import de.thorsten.model.Game;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import de.thorsten.model.Participation;
import de.thorsten.model.SportsEvent;
import de.thorsten.model.Training;
import de.thorsten.util.DateUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

// Nur solange ViewAccessScoped nicht von DeltaSpike unterstützt wird
import org.os890.cdi.ext.scope.api.scope.conversation.ViewAccessScoped;

@ViewAccessScoped
@Named
public class ParticipationListProducer implements Serializable {

    private static String TRAINING = "Training";
    private static String GAME = "Spiel";

    private String sportEventType;

    @Inject
    private ParticipationRepository participationRepository;

    @Inject
    SportsEventRepository sportsEventRepository;

    @Inject
    private Logger log;

    private List<Participation> participations;

    private Date sportEventDate;

    private SportsEvent selectedSportsEvent;

    private int numberOfParticipators;

    private int numberOfSeatsForthAvailable;

    private int numberOfSeatsBackAvailable;

    private int numberOfSeatsBackRequired;

    private int numberOfSeatsForthRequired;

    public int getNumberOfParticipators() {
        return numberOfParticipators;
    }

    public int getNumberOfSeatsBackRequired() {
        return numberOfSeatsBackRequired;
    }

    public int getNumberOfSeatsForthRequired() {
        return numberOfSeatsForthRequired;
    }

    public int getNumberOfSeatsBackAvailable() {
        return numberOfSeatsBackRequired;
    }

    public int getNumberOfSeatsForthAvailable() {
        return numberOfSeatsForthRequired;
    }

    @Named
    @Produces
    public List<Participation> getParticipations() {
        return participations;
    }

    public void setSportEventDate(Date x) {
        sportEventDate = x;
    }

    public void sportEventDateChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            sportEventDate = (Date) event.getNewValue();
            retrieveAllParticipatorsForSpecificDate();
            calculateParticipationAttributes();
            updateSelectedSportsEvent(sportEventDate);
        }
    }

    public Date getSportEventDate() {
        return sportEventDate;
    }

    public String getSportEventDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(sportEventDate);
    }

    /**
     * @PostConstruct wird nach Dependency Injection aufgerufen zur
     * Initialisierung Es darf nur eine solch annotierte Methode geben!
     */
    //@PostConstruct
    public void retrieveAllParticipators() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        participations = participationRepository.getAll();
        calculateParticipationAttributes();
    }

    // todo: Nicht intuitiv - besser mit  Injection
    public void retrieveAllParticipatorsForSpecificDate() {
        if (sportEventDate != null) {
            participations = participationRepository.getAllForSpecificDateOrderedByName(sportEventDate);
            log.info(participations.size() + " Participations gefunden");
            calculateParticipationAttributes();
        } else {
            log.info("sportEventDate is null");
        }
    }

    /**
     *
     * @param participation
     */
    public void onParticipationListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Participation participation) {
        //retrieveAllParticipators();
        retrieveAllParticipatorsForSpecificDate();
    }

    private void calculateParticipationAttributes() {
        numberOfParticipators = 0;
        numberOfSeatsBackAvailable = 0;
        numberOfSeatsBackRequired = 0;
        numberOfSeatsForthRequired = 0;
        numberOfSeatsForthAvailable = 0;

        for (Participation p : participations) {
            if (p.isParticipating()) {
                numberOfParticipators++;
            }
            if (p.isDrivingBack()) {
                numberOfSeatsBackAvailable = numberOfSeatsBackAvailable + p.getPlayer().getCarsize();
            }
            if (p.isDrivingForth()) {
                numberOfSeatsForthAvailable = numberOfSeatsForthAvailable + p.getPlayer().getCarsize();
            }
        }
        numberOfSeatsBackRequired = numberOfParticipators - numberOfSeatsBackAvailable;
        numberOfSeatsForthRequired = numberOfParticipators - numberOfSeatsForthAvailable;
        
        log.fine("Calculated numberOfSeatsBackAvailable = " + numberOfSeatsBackAvailable);
        log.fine("Calculated numberOfSeatsForthAvailable = " + numberOfSeatsForthAvailable);
        log.fine("Calculated NumberOfSeatsBackRequired = " + numberOfSeatsBackRequired);
        log.fine("Calculated NumberOfSeatsForthRequired = " + numberOfSeatsForthRequired);
        log.fine("Calculated NumberOfParticipators = " + numberOfParticipators);
        
    }

    /**
     * @return the selectedSportsEvent
     */
    @Named
    @Produces
    public SportsEvent getSelectedSportsEvent() {
        return selectedSportsEvent;
    }

    /**
     * @param selectedSportsEvent the selectedSportsEvent to set
     */
    public void setSelectedSportsEvent(SportsEvent selectedSportsEvent) {
        this.selectedSportsEvent = selectedSportsEvent;
    }

    private void updateSelectedSportsEvent(Date selectedDate) {
        log.info("updateSelectedSportsEvent()");
        List<SportsEvent> events;
        events = sportsEventRepository.findByDate(selectedDate);
        if (events != null) {
            if (events.size() != 0) {
                selectedSportsEvent = events.get(0);
                determineSportEventType(); // todo: käse
                log.info("found selected Sport Event " + selectedSportsEvent.getDateAsString());
            }
        }
    }

    @PostConstruct
    public void initializeSelectedSportsEvent() {
        log.info("initializeSelectedSportsEvent()");
        List<SportsEvent> sportEvents;
        sportEvents = sportsEventRepository.findAllGeDate(new Date());
        if (sportEvents == null) {
            log.info("updateSelectedSportsEvent() - Training null");
        } else if (sportEvents.size() == 0) {
            log.info("updateSelectedSportsEvent() - Training null");
        } else {
            selectedSportsEvent = sportEvents.get(0);
            sportEventDate = selectedSportsEvent.getEventDate();
            retrieveAllParticipatorsForSpecificDate();
            calculateParticipationAttributes();
            determineSportEventType();
        }
    }

    /**
     * @return the sportEventType
     */
    public String getSportEventType() {
        return sportEventType;
    }

    /**
     * @param sportEventType the sportEventType to set
     */
    public void setSportEventType(String sportEventType) {
        this.sportEventType = sportEventType;
    }

    //todo 
    private void determineSportEventType() {
        if (selectedSportsEvent instanceof Training) {
            sportEventType = TRAINING;
        } else if (selectedSportsEvent instanceof Game) {
            sportEventType = GAME;
        }

    }
}
