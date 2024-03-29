package de.thorsten.controller;

import de.thorsten.data.TeamRepository;
import de.thorsten.data.SportEventDetailRepository;
import de.thorsten.data.TrainingRepository;
import de.thorsten.model.Member;
import de.thorsten.model.Participation;
import de.thorsten.model.ParticipationGroup;
import de.thorsten.model.Team;
import de.thorsten.model.Training;
import de.thorsten.model.SportsEventDetails;
import de.thorsten.service.ParticipationService;
import de.thorsten.service.TrainingService;
import de.thorsten.util.DateComparator;
import de.thorsten.util.DateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
    private TrainingRepository trainingRepository;

//    @Inject
//    private Event<Team> teamChangedEvent;

    @Inject
    private SportEventDetailRepository sportEventDetailRepository;

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

    private List<Training> allExistingTrainings;

    private List<Training> allMissingTrainings;
    private List<Date> allMissingTrainingDates;

    private List<Training> listOfNewTrainings;
    private List<Date> listOfNewTrainingDates;

    private List<SportsEventDetails> allTrainingDays;

    private Date nextTrainingDateFromTemp;
    private Date nextTrainingDateToTemp;

    @Inject
    @Current
    private ParticipationGroup selectedParticipationGroup;

    // SportsEventDetails EintrÃ¤ge pro Weekday, potentiell mehr wenn mehrmals am Tag trainiert wird.
    private List<SportsEventDetails> trDayList;

    private SportsEventDetails selectedTrainingDay;

//    @Inject
//    private List<ParticipationGroup> participationGroups;
//
//    @Inject
//    private ParticipationGroupListProducer participationGroupListProducer;

    @PostConstruct
    public void init() {
        log.fine("TrainingController.init() * @PostConstruct");
        selectedTeams = new ArrayList();
        selectedTeamIds = null;
        allTrainingDays = sportEventDetailRepository.findAll();
        listOfNewTrainings = new ArrayList();
        setListOfNewTrainingDates(new ArrayList<Date>());
    }

    public void determineMissingTrainingDays() {

        Date currentDate = this.nextTrainingDateFromTemp;

        Calendar currentCal = Calendar.getInstance();
        while (currentDate.before(this.nextTrainingDateToTemp)) {
            currentCal.roll(Calendar.DATE, true);
        }

    }

    public void trainingDateFromTempChanged(ValueChangeEvent event) {
        log.fine("TrainingController.trainingDateFromTempChanged() ");
        if (event.getNewValue() != null) {
            this.nextTrainingDateFromTemp = ((Date) event.getNewValue());
            log.fine("TrainingController.nextTrainingDateFromTemp * ...to " + nextTrainingDateFromTemp);
            calculateMissingTrainings();
        }
    }

    public void trainingDateToTempChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            this.nextTrainingDateToTemp = ((Date) event.getNewValue());
            log.fine("TrainingController.nextTrainingDateToTemp * ...to " + nextTrainingDateToTemp);
            calculateMissingTrainings();
        }
    }

    public void registerNewTrainingDates(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            this.listOfNewTrainings = ((List) event.getNewValue());
            log.fine("TrainingController.registerNewTrainingDates * Added " + listOfNewTrainings.size() + " new TrainingsDates");
        }
    }

    public void printNewTrainingDates() {
        log.fine(listOfNewTrainingDates.size() + " Elemente");
        for (Iterator<Date> it = listOfNewTrainingDates.iterator(); it.hasNext();) {
            Date d = (Date) it.next();
            log.fine("Neues Datum = " + d.toString());
        }
    }

    public void trainingDateChanged(ValueChangeEvent event) {
        int weekday = -1;
        Calendar cal = Calendar.getInstance();
        if (event.getNewValue() != null) {
            nextTrainingDate = ((Date) event.getNewValue());
            log.fine("TrainingController.trainingDateChanged() * ...to " + nextTrainingDate);
            cal.setTime(nextTrainingDate);
            weekday = cal.get(Calendar.DAY_OF_WEEK);
            log.fine("Weekday = " + weekday);
            trDayList = sportEventDetailRepository.findByWeekday(weekday);
            log.fine(trDayList.size() + " EintrÃ¤ge gefunden");
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
            this.createErrorMessage("Kein gÃ¼ltiges Trainingsdatum (Wochentag?)");
        }

        Training newTraining = new Training();

        newTraining.setEventDate(nextTrainingDate);
        newTraining.setSportsEventDetail(selectedTrainingDay);
        //newTraining.initializeTrainingWithTrainingDayTemplateData();

        newTraining.setTeams(getSelectedTeams());

        String specificErrorMsg = new String();
        if (getSelectedTeams().size() == 0) {
            specificErrorMsg = "Mindestens ein Team muÃŸ ausgewÃ¤hlt sein";
        }
        log.info("Neuer Trainingseintrag " + newTraining.getDateAsString()
                + ", "
                + ", " + newTraining.getSportsEventDetail().getLocation()
                + ", " + newTraining.getSportsEventDetail().getTimeFrom()
                + " - " + newTraining.getSportsEventDetail().getTimeTo());

        FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fehler!", "Neues Training konnte nicht gespeichert werden !" + specificErrorMsg);
        FacesMessage successMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Neue Trainingszeiten hinzugefügt!", "Neues Training gespeichert!");

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

    public void createParticipationsForListOfNextTrainingDates() {
        if (selectedTrainingDay == null) {
            this.createErrorMessage("Kein gueltiges Trainingsdatum (Wochentag?)");
        }

        Training newTraining = new Training();

        newTraining.setSportsEventDetail(selectedTrainingDay);
        //newTraining.initializeTrainingWithTrainingDayTemplateData();

        newTraining.setTeams(getSelectedTeams());

        String specificErrorMsg = new String();
        if (getSelectedTeams().size() == 0) {
            specificErrorMsg = "Mindestens ein Team muss ausgewÃ¤hlt sein";
        }
        log.info("Neuer Trainingseintrag " + newTraining.getEventDate()
                + ", " + newTraining.getSportsEventDetail().getLocation()
                + ", " + newTraining.getSportsEventDetail().getTimeFrom()
                + " - " + newTraining.getSportsEventDetail().getTimeTo());

        FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fehler!", "Neues Training konnte nicht gespeichert werden !" + specificErrorMsg);
        FacesMessage successMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Neues Training hinzugefuegt!", "Neues Training gespeichert!");

        List<Member> members = null;
        Training tr = new Training();
        members = selectedParticipationGroup.getMembers();

        try {
            for (Date d : this.listOfNewTrainingDates) {
                newTraining.setEventDate(d);

                tr = trainingService.update(newTraining);
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
            };
        } catch (Exception e) {
            facesContext.addMessage(null, errorMsg);
            log.warning("TrainingController.createParticipationsForListOfNextTrainingDate() * Training(s) konnte(n) nicht persistiert werden");
            e.printStackTrace();
        }

        facesContext.addMessage(
                null, successMsg);
        init();

    }

    public void trainingDayChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            Long tmpId = Long.valueOf((String) event.getNewValue());
            selectedTrainingDay = sportEventDetailRepository.findById(tmpId);
            log.fine("TrainingController.trainingDayChanged() * ...to new trainingDay = " + selectedTrainingDay.toString());
        }
    }

    public void teamChanged(ValueChangeEvent event) {
        log.fine("TrainingController.teamChanged() * teamChanged");
        selectedTeamIds = (Long[]) event.getNewValue();
        for (int x = 0; x < selectedTeamIds.length; x++) {
            Team currentTeam = teamRepository.findById(Long.valueOf(selectedTeamIds[x]));
            log.fine("log.fine(\"TrainingController.teamChanged() * CurrentTeam = " + currentTeam.toString());
            getSelectedTeams().add(currentTeam);
            log.fine("log.fine(\"TrainingController.teamChanged() * ..hinzugefuegt, selectedTeams.size() jetzt " + getSelectedTeams().size());
        }
    }

    public void createErrorMessage(String msg) {
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fehler!",
                msg);
        facesContext.addMessage(null, m);
    }

//    public void removeMemberFromList(Member member) {
//        log.info("removeMemberFromList called");
//    }
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
    public List<SportsEventDetails> getTrDayList() {
        return trDayList;
    }

    /**
     * @return the selectedTrainingDay
     */
    public SportsEventDetails getSelectedTrainingDay() {
        return selectedTrainingDay;
    }

    /**
     * @return the allExistingTrainingDates
     */
    public List<SportsEventDetails> getAllTrainingDays() {
        return this.allTrainingDays;
    }

    /**
     * @return the nextTrainingDateFromTemp
     */
    public Date getNextTrainingDateFromTemp() {
        return nextTrainingDateFromTemp;
    }

    /**
     * @param nextTrainingDateFromTemp the nextTrainingDateFromTemp to set
     */
    public void setNextTrainingDateFromTemp(Date nextTrainingDateFromTemp) {
        this.nextTrainingDateFromTemp = nextTrainingDateFromTemp;
    }

    /**
     * @return the nextTrainingDateToTemp
     */
    public Date getNextTrainingDateToTemp() {
        return nextTrainingDateToTemp;
    }

    /**
     * @param nextTrainingDateToTemp the nextTrainingDateToTemp to set
     */
    public void setNextTrainingDateToTemp(Date nextTrainingDateToTemp) {
        this.nextTrainingDateToTemp = nextTrainingDateToTemp;
    }

    /**
     * @return the allMissingTrainings
     */
    public List<Training> getAllMissingTrainings() {
        return allMissingTrainings;
    }

    /**
     * @return the allMissingTrainings
     */
    public List<Date> getAllMissingTrainingDates() {
        return allMissingTrainingDates;
    }

    public void calculateMissingTrainings() {
        log.fine("Datümer...: + " + nextTrainingDateFromTemp + ", " + nextTrainingDateToTemp);
        if ((nextTrainingDateFromTemp != null) && (nextTrainingDateToTemp != null)) {
            this.allExistingTrainings = new ArrayList();
            this.allMissingTrainings = new ArrayList();
            this.allMissingTrainingDates = new ArrayList();
            this.allExistingTrainings = trainingRepository.findAllOfTrainingDayBetweenTimeRange(this.selectedTrainingDay, this.nextTrainingDateFromTemp, this.nextTrainingDateToTemp);
            log.fine("TrainingController.calculateMissingTrainings * allExistingTrainings.size() = " + allExistingTrainings.size());

            Training newTraining;

            List<Date> allExistingTrainingDates = new ArrayList<Date>();
            for (Training t : getAllExistingTrainings()) {
                allExistingTrainingDates.add(t.getEventDate());
            }

            Calendar calFrom = Calendar.getInstance();
            Calendar calTo = Calendar.getInstance();
            calFrom.setTime(nextTrainingDateFromTemp);
            calTo.setTime(nextTrainingDateToTemp);

            for (Calendar nextFreeTrainingDate = calFrom; nextFreeTrainingDate.before(calTo); nextFreeTrainingDate.add(Calendar.DATE, 1)) {
                log.fine("--> NÃ¤chstes Datum " + DateUtil.getSelectedDateAsFormattedString(nextFreeTrainingDate.getTime()));
                if (nextFreeTrainingDate.get(Calendar.DAY_OF_WEEK) == selectedTrainingDay.getWeekday()) {
                    if (containsEventDate(allExistingTrainingDates, nextFreeTrainingDate.getTime()) == false) {
                        log.fine("--> Training hinzu ");
                        newTraining = new Training();
                        newTraining.setTeams(getSelectedTeams());
                        newTraining.setSportsEventDetail(selectedTrainingDay);
                        newTraining.setEventDate(nextFreeTrainingDate.getTime());
                        log.fine(newTraining.toString());
                        allMissingTrainings.add(newTraining);
                        allMissingTrainingDates.add(nextFreeTrainingDate.getTime());
                    }
                }
            }
            log.fine("TrainingController.calculateMissingTrainings * allMissingTrainings.size() = " + allMissingTrainings.size());
            log.fine("TrainingController.calculateMissingTrainings * allMissingTrainingDates.size() = " + allMissingTrainingDates.size());
        }
    }

    public boolean containsEventDate(List<Date> dates, Date eventDate) {

        int index = Collections.binarySearch(dates, eventDate, new DateComparator());
        if (index >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the allExistingTrainings
     */
    public List<Training> getAllExistingTrainings() {
        return allExistingTrainings;
    }

    /**
     * @param allExistingTrainings the allExistingTrainings to set
     */
    public void setAllExistingTrainings(List<Training> allExistingTrainings) {
        this.allExistingTrainings = allExistingTrainings;
    }

    /**
     * @param allMissingTrainings the allMissingTrainings to set
     */
    public void setAllMissingTrainings(List<Training> allMissingTrainings) {
        this.allMissingTrainings = allMissingTrainings;
    }

    /**
     *
     * @return the listOfNewTrainings
     */
    public List<Training> getListOfNewTrainings() {
        return listOfNewTrainings;
    }

    /**
     * @param listOfNewTrainings the listOfNewTrainings to set
     */
    public void setListOfNewTrainings(List<Training> pListOfNewTrainings) {
        log.fine(" setListOfNewTrainings " + pListOfNewTrainings.size() + " Elements");
        this.listOfNewTrainings = pListOfNewTrainings;
    }

    /**
     * @return the selectedTeams
     */
    public List<Team> getSelectedTeams() {
        return selectedTeams;
    }

    /**
     * @return the listOfNewTrainingDates
     */
    public List<Date> getListOfNewTrainingDates() {
        return listOfNewTrainingDates;
    }

    /**
     * @param listOfNewTrainingDates the listOfNewTrainingDates to set
     */
    public void setListOfNewTrainingDates(List<Date> listOfNewTrainingDates) {
        this.listOfNewTrainingDates = listOfNewTrainingDates;
    }

}
