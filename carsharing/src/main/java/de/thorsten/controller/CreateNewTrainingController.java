package de.thorsten.controller;

import de.thorsten.data.MemberRepository;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.os890.cdi.ext.scope.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author Thorsten
 */
@Named("trainingController")
@ViewAccessScoped
public class CreateNewTrainingController implements Serializable {

    @Inject
    private Logger log;

    @Inject
    private TrainingDayRepository trainingDayRepository;

    @Inject
    private MemberRepository memberRepository;

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

    private ParticipationGroup selectedParticipationGroup;

    @Inject
    private List<ParticipationGroup> participationGroups;

    @Inject
    private ParticipationGroupListProducer participationGroupListProducer;

    @PostConstruct
    public void init() {
        selectedTeams = new ArrayList();
         // initial einfach erste laden

        if (participationGroups != null && participationGroups.size() != 0) {
            selectedParticipationGroup = participationGroups.get(0);
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextTrainingDate);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        List trDayList = trainingDayRepository.findByWeekday(weekday);

        if (trDayList == null) {
            this.createErrorMessage("Kein gültiges Trainingsdatum (Wochentag?)");
        }
        if (trDayList.size() == 0) {
            this.createErrorMessage("Kein gültiges Trainingsdatum (Wochentag?)");
        }
        Training newTraining = new Training();
        newTraining.setEventDate(nextTrainingDate);
        newTraining.setTrainingDay((TrainingDay) trDayList.get(0));
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
            List<Member> members = selectedParticipationGroup.getMembers();
            for (final Iterator it = members.iterator(); it.hasNext();) {
                Participation newParticipation = new Participation();
                newParticipation.setPlayer((Member) it.next());
                newParticipation.setTraining(tr);
                try {
                    participationService.update(newParticipation);
                } catch (Exception e) {
                    facesContext.addMessage(null, errorMsg);
                    log.info("Participation could not be stored");
                    e.printStackTrace();
                }
            };
        } catch (Exception e) {
            facesContext.addMessage(null, errorMsg);
            log.info("Training konnte nicht persistiert werden");
            e.printStackTrace();
        }

        facesContext.addMessage(null, successMsg);

    }

    public void trainingDateChanged(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            nextTrainingDate = (Date) event.getNewValue();
        }
    }

    public void participationGroupChanged(ValueChangeEvent event) {
        log.fine("participationGroupChanged ");
        if (event.getNewValue() != null) {
            Long tmpId = Long.valueOf((String) event.getNewValue());
            selectedParticipationGroup = participationGroupListProducer.getParticipationGroupById(tmpId);
            log.fine("...to new participationGroup = " + selectedParticipationGroup.toString());
        }
    }

    public void teamChanged(ValueChangeEvent event) {
        log.info("teamChanged");
        selectedTeamIds = (Long[]) event.getNewValue();
        for (int x = 0; x < selectedTeamIds.length; x++) {
            Team currentTeam = teamRepository.findById(Long.valueOf(selectedTeamIds[x]));
            log.info("CurrentTeam = " + currentTeam.toString());
            selectedTeams.add(currentTeam);
            log.info("..hinzugefügt, selectedTeams.size() jetzt " + selectedTeams.size());
        }
        log.info("teamChanged - selectedTeams)");

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
     * @return the selectedParticipationGroup
     */
    public ParticipationGroup getSelectedParticipationGroup() {
        return selectedParticipationGroup;
    }

    /**
     * @param selectedParticipationGroup the selectedParticipationGroup to set
     */
    public void setSelectedParticipationGroup(ParticipationGroup selectedParticipationGroup) {
        this.selectedParticipationGroup = selectedParticipationGroup;
    }

}
