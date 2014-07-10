package de.thorsten.controller;

import de.thorsten.data.ParticipationGroupListProducer;
import de.thorsten.data.TeamRepository;
import de.thorsten.data.TrainingDayRepository;
import de.thorsten.model.Member;
import de.thorsten.model.Participation;
import de.thorsten.model.ParticipationGroup;
import de.thorsten.model.Team;
import de.thorsten.model.Training;
import de.thorsten.model.TrainingDay;
import de.thorsten.service.ParticipationService;
import de.thorsten.service.TrainingService;
import de.thorsten.util.DateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Thorsten
 */
@Named("trainingController")
@ViewScoped
public class TrainingController implements Serializable {

    @Inject
    private Logger log;

    @Inject
    private Event<Team> teamChangedEvent;

    @Inject
    private TrainingDayRepository trainingDayRepository;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ParticipationService participationService;

    @Inject
    private TeamRepository teamRepository;

    @Inject
    TrainingService trainingService;

    private Date nextTrainingDate;

    private Long[] selectedTeamIds;

    private List<Team> selectedTeams;

    @Inject
    @Current
    private ParticipationGroup selectedParticipationGroup;

    // TrainingDay Einträge pro Weekday, potentiell mehr wenn mehrmals am Tag trainiert wird.
    private List<TrainingDay> trDayList;

    private TrainingDay selectedTrainingDay;

    @Inject
    private List<ParticipationGroup> participationGroups;

    @Inject
    private ParticipationGroupListProducer participationGroupListProducer;

    @PostConstruct
    public void init() {
        log.fine("TrainingController.init() * @PostConstruct");
        selectedTeams = new ArrayList();
        selectedTeamIds = null;
    }

    public void trainingDateChanged(ValueChangeEvent event) {
        log.fine("TrainingController.trainingDateChanged() ");
        int weekday = -1;
        Calendar cal = Calendar.getInstance();
        if (event.getNewValue() != null) {
            nextTrainingDate = ((Date) event.getNewValue());
            log.fine("TrainingController.trainingDateChanged() * ...to " + nextTrainingDate);
            cal.setTime(nextTrainingDate);
            weekday = cal.get(Calendar.DAY_OF_WEEK);
            trDayList = trainingDayRepository.findByWeekday(weekday);
        }

    }

    /**
     * @return the nextTrainingDate
     */
    public Date getNextTrainingDate() {
        return nextTrainingDate;
    }

    public String getNextTrainingDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(nextTrainingDate);
    }

    /**
     * @param nextTrainingDate the nextTrainingDate to set
     */
    public void setNextTrainingDate(Date nextTrainingDate) {
        this.nextTrainingDate = nextTrainingDate;
    }

    public void createParticipationsForNextTrainingDate() {
        if (selectedTrainingDay == null) {
            this.createErrorMessage("Kein gültiges Trainingsdatum (Wochentag?)");
        }

        Training newTraining = new Training();

        // todo: Achtung: Hier das richtige übergeben!!!!!!!
        newTraining.setEventDate(nextTrainingDate);
        newTraining.setTrainingDay(selectedTrainingDay);
        newTraining.initializeTrainingWithTrainingDayTemplateData();

        newTraining.setTeams(selectedTeams);

        String specificErrorMsg = new String();
        if (selectedTeams.size() == 0) {
            specificErrorMsg = "Mindestens ein Team muß ausgewählt sein";
        }
        log.info("Neuer Trainingseintrag " + newTraining.getDateAsString()
                + ", "
                + ", " + newTraining.getLocation()
                + ", " + newTraining.getTimeFrom()
                + " - " + newTraining.getTimeTo());

        FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fehler!", "Neues Training konnte nicht gespeichert werden !" + specificErrorMsg);
        FacesMessage successMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Neues Training hinzugefügt!", "Neues Training gespeichert!");

        try {
            Training tr = trainingService.update(newTraining);
            List<Member> members = null;
            members = selectedParticipationGroup.getMembers();
            for (final Iterator it = members.iterator(); it.hasNext();) {
                Participation newParticipation = new Participation();
                newParticipation.setPlayer((Member) it.next());
                newParticipation.setTraining(tr);
                try {
                    participationService.update(newParticipation);
                } catch (Exception e) {
                    facesContext.addMessage(null, errorMsg);
                    log.warning("TrainingController.createParticipationsForNextTrainingDate() * Participation could not be stored");
                    e.printStackTrace();
                }
            };
        } catch (Exception e) {
            facesContext.addMessage(null, errorMsg);
            log.warning("TrainingController.createParticipationsForNextTrainingDate() * Training konnte nicht persistiert werden");
            e.printStackTrace();
        }

        facesContext.addMessage(null, successMsg);
        init();

    }

    public void trainingDayChanged(ValueChangeEvent event) {
        log.fine("TrainingController.trainingDayChanged()");
        if (event.getNewValue() != null) {
            Long tmpId = Long.valueOf((String) event.getNewValue());
            selectedTrainingDay = trainingDayRepository.findById(tmpId);
            log.fine("TrainingController.trainingDayChanged() * ...to new trainingDay = " + selectedTrainingDay.toString());
        }
    }

    public void teamChanged(ValueChangeEvent event) {
        log.fine("TrainingController.teamChanged() * teamChanged");
        selectedTeamIds = (Long[]) event.getNewValue();
        for (int x = 0; x < selectedTeamIds.length; x++) {
            Team currentTeam = teamRepository.findById(Long.valueOf(selectedTeamIds[x]));
            log.fine("log.fine(\"TrainingController.teamChanged() * CurrentTeam = " + currentTeam.toString());
            selectedTeams.add(currentTeam);
            log.fine("log.fine(\"TrainingController.teamChanged() * ..hinzugefügt, selectedTeams.size() jetzt " + selectedTeams.size());
        }
    }

    public void createErrorMessage(String msg) {
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fehler!",
                msg);
        facesContext.addMessage(null, m);
    }

    public void removeMemberFromList(Member member) {
        log.info("removeMemberFromList called");
    }

    /**
     * @return the selectedTeams
     */
    public Long[] getSelectedTeamIds() {
        return selectedTeamIds;
    }

    /**
     * @param selectedTeams the selectedTeams to set
     */
    public void setSelectedTeamIds(Long[] selectedTeams) {
        this.selectedTeamIds = selectedTeams;
    }

    /**
     * @return the trDayList
     */
    public List<TrainingDay> getTrDayList() {
        return trDayList;
    }

    /**
     * @return the selectedTrainingDay
     */
    public TrainingDay getSelectedTrainingDay() {
        return selectedTrainingDay;
    }

}
