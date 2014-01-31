package de.thorsten.data;

import de.thorsten.model.Game;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import de.thorsten.model.Participation;
import de.thorsten.model.SportsEvent;
import de.thorsten.model.Team;
import de.thorsten.model.Training;
import de.thorsten.util.DateUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
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
    private TeamRepository teamRepository;

    @Inject
    SportsEventRepository sportsEventRepository;

    @Inject
    private Logger log;

    private List<Participation> participations;

    private Date sportEventDate;

    private SportsEvent selectedSportsEvent;

    private Team selectedTeam;

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

  

    public void sportEventDateChanged(ValueChangeEvent event) {
        log.info("sportEventDateChanged");
        if (event.getNewValue() != null) {
            this.sportEventDate = ((Date) event.getNewValue());
            log.info("...to " + sportEventDate);
            // Neu!
            // 1. events für datum ermitteln und teamermitteln
            // 3. dann erst teilnmehmer
            retrieveAllParticipatorsForSelectedDateAndTeam();

            // retrieveAllParticipatorsForSpecificDate();
            calculateParticipationAttributes();
            updateSelectedSportsEvent();
        }
    }

    public void teamChanged(ValueChangeEvent event) {
        log.info("teamChanged()");
        if (event.getNewValue() != null) {
            Long tmpId = Long.valueOf((String) event.getNewValue());
            selectedTeam = teamRepository.findById(tmpId);
            log.info("...to new Team = " + selectedTeam.toString());

            // Neu!
            // 1. events für datum ermitteln und teamermitteln
            // 3. dann erst teilnmehmer
            retrieveAllParticipatorsForSelectedDateAndTeam();

            // retrieveAllParticipatorsForSpecificDate();
            calculateParticipationAttributes();
            updateSelectedSportsEvent();
        }
    }

    public String getSportEventDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(getSportEventDate());
    }

    /**
     * @PostConstruct wird nach Dependency Injection aufgerufen zur
     * Initialisierung Es darf nur eine solch annotierte Methode geben!
     */
    //@PostConstruct
//    public void retrieveAllParticipators() {
//        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//        participations = participationRepository.getAll();
//        calculateParticipationAttributes();
//    }

    // todo: Nicht intuitiv - besser mit  Injection
//    public void retrieveAllParticipatorsForSpecificDate() {
//        if (sportEventDate != null) {
//            participations = participationRepository.getAllForSpecificDateOrderedByName(sportEventDate);
//            calculateParticipationAttributes();
//        } else {
//            log.info("sportEventDate is null");
//        }
//    }

    public void retrieveAllParticipatorsForSelectedDateAndTeam() {
        log.fine("retrieveAllParticipatorsForSelectedDateAndTeam()");

        if ((getSportEventDate() != null) && (this.selectedTeam != null)) {
            log.fine("... for Team = " + selectedTeam.toString());
            log.fine("... and sportEventDate = " + getSportEventDate());
            participations = participationRepository.getAllForSpecificDateAndTeamOrderedByName(getSportEventDate(), selectedTeam);
            log.fine("found " + participations.size() + " Participations");

            // todo zum testen schleife durc participations und daranhängende Teams anzeigen!
            for (Participation p : participations) {
                log.fine(p.toString());
                log.fine("SportEvent - teams");
                for (Team t : p.getTrainingItem().getTeams()) {
                    log.fine("Team: " + t.toString());
                }
            }
        } else {
            log.warning("sportEventDate or selectedTeam is null");
        }
        calculateParticipationAttributes();
    }

    /**
     *
     * @param participation
     */
    public void onParticipationListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Participation participation) {
    log.fine("onParticipationListChanged() called");        
        retrieveAllParticipatorsForSelectedDateAndTeam();
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

    @PostConstruct
    public void initialize() {
        log.info("initialize() called");
        // Initial einfach erste Team nehmen        
        selectedTeam = teamRepository.findAll().get(0);

        if (selectedTeam != null) {
            log.fine("***Selected Team in PostConstruct = " + selectedTeam.toString());
        }
        // Als Datum das aktuelle datum verwenden
        List<SportsEvent> sportEvents;
        sportEvents = sportsEventRepository.findAllGeDateAndForSpecificTeam(new Date(), selectedTeam);
        if (sportEvents == null) {
            log.fine("initializte() - Training null");
        } else if (sportEvents.size() == 0) {
            log.fine("initialize() - Training null");
        } else {
            // 1. SportEvent nehmen
            selectedSportsEvent = sportEvents.get(0);
            sportEventDate = selectedSportsEvent.getEventDate();
            retrieveAllParticipatorsForSelectedDateAndTeam();
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

    /**
     * @return the selectedTeam
     */
    public Team getSelectedTeam() {
        return selectedTeam;
    }

    /**
     * @param selectedTeam the selectedTeam to set
     */
    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    private void updateSelectedSportsEvent() {
        log.info("updateSelectedSportsEvent() with " +  sportEventDate + " and " + this.selectedTeam);
        List<SportsEvent> events;
        events = sportsEventRepository.findByDateAndTeam(sportEventDate, selectedTeam);
        if (events != null) {
            if (events.size() != 0) {
                selectedSportsEvent = events.get(0);
                determineSportEventType(); // todo: käse
                log.info("found selected Sport Event " + selectedSportsEvent.toString());
            }
        }
    }

    /**
     * @return the sportEventDate
     */
    public Date getSportEventDate() {
        return sportEventDate;
    }

    /**
     * @param sportEventDate the sportEventDate to set
     */
    public void setSportEventDate(Date sportEventDate) {
        this.sportEventDate = sportEventDate;
    }
}
