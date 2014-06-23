package de.thorsten.data;

import de.thorsten.controller.Current;
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
import javax.enterprise.event.Event;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.event.ValueChangeEvent;
import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.param.ParamValue;

// Nur solange ViewAccessScoped nicht von DeltaSpike unterstützt wird
import org.os890.cdi.ext.scope.api.scope.conversation.ViewAccessScoped;

@ViewAccessScoped
@Named
public class ParticipationListProducer implements Serializable {

    private static String TRAINING = "Training";
    private static String GAME = "Spiel";
    private static String UNKNOWN = "Handball";

    @Inject
    @Param
    private ParamValue<Long> paramTeamID;

    private String sportEventType;

    @Inject
    private ParticipationRepository participationRepository;

    @Inject
    private TeamRepository teamRepository;

    @Inject
    SportsEventRepository sportsEventRepository;

    @Inject
    private Logger log;

    @Inject
    private Event<Team> teamChangedEvent;

    @Inject
    private List<Team> teams;

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
        log.fine("sportEventDateChanged ");
        if (event.getNewValue() != null) {
            this.sportEventDate = ((Date) event.getNewValue());
            log.fine("...to " + sportEventDate);
            this.recalculateParticipationDependencies();
        }
    }

    public void teamChanged(ValueChangeEvent event) {
        log.fine("teamChanged ");
        if (event.getNewValue() != null) {
            Long tmpId = Long.valueOf((String) event.getNewValue());
            selectedTeam = teamRepository.findById(tmpId);
            log.fine("...to new Team = " + selectedTeam.toString());
            teamChangedEvent.fire(selectedTeam);
            this.recalculateParticipationDependencies();
        }
    }

    public String getSportEventDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(getSportEventDate());
    }

    public void retrieveAllRelevantParticipators() {
        log.fine("ParticipationListProducer.retrieveAllRelevantParticipators()");

        if ((getSportEventDate() != null) && (this.selectedTeam != null)) {
            log.fine("ParticipationListProducer.retrieveAllRelevantParticipators() * ... for Team = " + selectedTeam.toString());
            log.fine("ParticipationListProducer.retrieveAllRelevantParticipators() *... and sportEventDate = " + getSportEventDate());
            participations = participationRepository.getAllForSpecificDateAndTeamOrderedByName(getSportEventDate(), selectedTeam);
        } else if (this.selectedTeam == null) {
            participations = participationRepository.getAllForSpecificDateAndTeamOrderedByName(getSportEventDate(), selectedTeam);
        } else {
            log.warning("ParticipationListProducer.retrieveAllRelevantParticipators() * sportEventDate is null");
        }

        if (participations != null) {
            log.fine("ParticipationListProducer.retrieveAllRelevantParticipators() * Found " + participations.size() + " Participations:");

            for (Participation p : participations) {
                log.fine("ParticipationListProducer.retrieveAllRelevantParticipators() * " + p.toString());
                log.fine("ParticipationListProducer.retrieveAllRelevantParticipators() * Teams: ");
                for (Team t : p.getTrainingItem().getTeams()) {
                    log.fine("ParticipationListProducer.retrieveAllRelevantParticipators() * Team: " + t.toString());
                }
            }
            calculateParticipationAttributes();
        }
    }

    /**
     *
     * @param participation
     */
    public void onParticipationListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Participation participation) {
        log.fine("onParticipationListChanged() called");
        retrieveAllRelevantParticipators();
    }

    private void calculateParticipationAttributes() {

        log.fine("calculateParticipationAttributes");

        numberOfParticipators = 0;
        numberOfSeatsBackAvailable = 0;
        numberOfSeatsBackRequired = 0;
        numberOfSeatsForthRequired = 0;
        numberOfSeatsForthAvailable = 0;
        int numberOfBicycleDrivers = 0;

        if (participations == null) {
            log.warning("Participations = null");
        } else {
            for (Participation p : participations) {
                if (p == null) {
                    log.severe("participation is null : " + p);

                }
                if (p.isParticipating()) {
                    numberOfParticipators++;
                }
                if (p.isDrivingBack()) {
                    numberOfSeatsBackAvailable = numberOfSeatsBackAvailable + p.getPlayer().getCarsize();
                }
                if (p.isDrivingForth()) {
                    numberOfSeatsForthAvailable = numberOfSeatsForthAvailable + p.getPlayer().getCarsize();
                }
                if (p.isDrivingBicycle()) {
                    numberOfBicycleDrivers++;
                }
            }
        }
        numberOfSeatsBackRequired = numberOfParticipators - numberOfSeatsBackAvailable - numberOfBicycleDrivers;
        numberOfSeatsForthRequired = numberOfParticipators - numberOfSeatsForthAvailable - numberOfBicycleDrivers;

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
        sportEventType = UNKNOWN;
        sportEventDate = new Date();
        // Selected Team via URL Parameter hat Vorrang
        if (getParamTeamID() != null) {
            // Wenn Übergabeparameter in URL vorhanden, dieses Team nehmen
            if (getParamTeamID().getValue() != null) {
                selectedTeam = teamRepository.findById(getParamTeamID().getValue());
                teamChangedEvent.fire(selectedTeam);
                log.fine("ParticipationListProducer.initialize() * Selected Team " + selectedTeam + " via URL Parameter");
            }
        }
        // Als Datum das aktuelle datum verwenden
        List<SportsEvent> sportEvents;

        if (selectedTeam == null) {
            // Wenn kein Team, dann erstes in der Liste
            selectedTeam = teams.get(0);
            log.fine("ParticipationListProducer.initialize() * Selected Team = Erstes Team aus Liste:" + selectedTeam);
        } else {
            log.fine("ParticipationListProducer.initialize() * Selected Team " + selectedTeam);
        }
        sportEvents = sportsEventRepository.findAllGeDateAndForSpecificTeam(new Date(), selectedTeam);

        if (sportEvents == null) {
            log.warning("ParticipationListProducer.initialize() *  SporEvents null");
        } else if (sportEvents.size() == 0) {
            log.warning("ParticipationListProducer.initialize() *  SportEvents null");
        } else {
            // 1. SportEvent nehmen (nächstes Datum)
            selectedSportsEvent = sportEvents.get(0);
            sportEventDate = selectedSportsEvent.getEventDate();
            this.recalculateParticipationDependencies();
        }
        log.fine("ParticipationListProducer.initialize() * final selectedTeam = " + selectedTeam);
        log.fine("ParticipationListProducer.initialize() * final sportEventDate = " + sportEventDate);
        log.fine("ParticipationListProducer.initialize() * final selectedSportsEvent = " + selectedSportsEvent);
        determineSportEventType();
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
    @Produces
    @Current
    @Named
    public Team getSelectedTeam() {
        return selectedTeam;
    }

    /**
     * @param selectedTeam the selectedTeam to set
     */
    public void setSelectedTeam(Team selectedTeam) {
        teamChangedEvent.fire(selectedTeam);
        this.selectedTeam = selectedTeam;
    }

    private void updateSelectedSportsEvent() {
        log.fine("updateSelectedSportsEvent() with " + sportEventDate + " and " + this.selectedTeam);
        List<SportsEvent> sportEvents;
        sportEvents = sportsEventRepository.findByDateAndTeam(sportEventDate, selectedTeam);
        if (sportEvents != null) {
            if (sportEvents.size() != 0) {
                selectedSportsEvent = sportEvents.get(0);
                determineSportEventType(); // todo: käse
                log.fine("found selected Sport Event " + selectedSportsEvent.toString());
            }
        }
        // 
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

    public void recalculateParticipationDependencies() {
        if (selectedTeam != null) {
            retrieveAllRelevantParticipators();
            calculateParticipationAttributes();
            updateSelectedSportsEvent();
        }
    }

    /**
     * @return the paramTeamID
     */
    public ParamValue<Long> getParamTeamID() {
        return paramTeamID;
    }
}
