package de.thorsten.data;

import de.thorsten.controller.Current;
import de.thorsten.model.Team;
import de.thorsten.model.Training;
import de.thorsten.model.TrainingDay;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;

@RequestScoped
public class TrainingListProducer {

    @Inject
    private Logger log;

    @Inject
    private TrainingRepository trainingRepository;

    @Inject
    @Current
    private Team selectedTeam;

    // todo ungenutzt, nur wieder wenn TrainingWizard fortgeführt wird.
    //@Inject
    //@Current
    private TrainingDay selectedTrainingDay;
    
    private List<Training> trainings;

    private List<Training> trainingsOfSelectedTeam;
    
    private List<Training> trainingsOfSelectedTrainingDay;

    private SortedSet<Date> trainingDates;

    private SortedSet<Date> trainingDatesOfSelectedTeam;

    // @Named provides access the return value via the EL variable name "trainings" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Training> getAllTrainings() {
        return trainings;
    }

    @Produces
    @Named
    public List<Training> getTrainingsOfSelectedTeam() {
        return trainingsOfSelectedTeam;
    }

    @Produces
    @Named
    public SortedSet<Date> getAllTrainingDates() {
        return trainingDates;
    }

    @Produces
    @Named
    public SortedSet<Date> getTrainingDatesOfSelectedTeam() {
        return trainingDatesOfSelectedTeam;
    }
    
    @Produces
    @Named
    public List<Training> getTrainingsOfSelectedTrainingDay() {
        return trainingsOfSelectedTrainingDay;
    }

    public void onSelectedTeamChanged(@Observes Team team) {
        log.fine("onSelectedTeamChanged() called, Team old=" + selectedTeam + " , Team new=" + team.toString());
        if (team != null) {
            log.fine(" , Team new=" + team.toString());
        } else {
            log.fine(" to ALL Teams, because selectedTeam = null");
        }
        selectedTeam = team;
        
        // todo - herauslösen...
        this.loadTrainings();
    }


        public void onSelectedTrainingDayChanged(@Observes TrainingDay trainingDay) {
        log.fine("onSelectedTrainingDayChanged() called, TrainingDay old=" + selectedTrainingDay + " , TrainingDay new=" + trainingDay.toString());
        if (trainingDay != null) {
            log.fine(" , TrainingDay new=" + trainingDay.toString());
        } else {
            log.fine(" to ALL TrainingDays, because selectedTrainingDay = null");
        }
        this.loadTrainings();
    }

    
    @PostConstruct
    public void loadTrainings() {
        log.fine("TrainingListProducer.loadTrainings()");
        this.retrieveAllTrainings();
        this.retrieveTrainingsOfSelectedTeam();
        this.retrieveTrainingsOfSelectedTrainingDay();
    }

    private void retrieveAllTrainings() {
        log.fine("TrainingListProducer.retrieveAllTrainings() * independent of Team");
        this.trainings = trainingRepository.findAll();

        trainingDates = new TreeSet<Date>();

        for (Training t : trainings) {
            trainingDates.add(t.getEventDate());
        }
    }

    private void retrieveTrainingsOfSelectedTeam() {
        log.fine("TrainingListProducer.retrieveTrainingsOfSelectedTeam()");
        
        if (selectedTeam != null) {
            log.fine("TrainingListProducer.retrieveAllTrainings() * for selectedTeam = " + selectedTeam);
            trainingsOfSelectedTeam = trainingRepository.findAllOfTeam(selectedTeam);
        } else {
            log.warning("TrainingListProducer.retrieveAllTrainings() * selectedTeam is null");
        }
        trainingDatesOfSelectedTeam = new TreeSet<Date>();

        for (Training t : trainingsOfSelectedTeam) {
            trainingDatesOfSelectedTeam.add(t.getEventDate());
        }
    }

    // todo ungetestet und nicht im Einsatz
    private void retrieveTrainingsOfSelectedTrainingDay() {
        if (selectedTrainingDay != null) {
            trainingsOfSelectedTrainingDay = trainingRepository.findAllOfTrainingDay(selectedTrainingDay);
        
            trainingDates = new TreeSet<Date>();

            for (Training t : trainings) {
                trainingDates.add(t.getEventDate());
            }
        } else {
            log.warning("TrainingListProducer.retrieveAllTrainings() * selectedTeam is null");
        }        
    }
}
