package de.thorsten.data;

import de.thorsten.controller.Current;
import de.thorsten.model.Team;
import de.thorsten.model.Training;
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
import javax.enterprise.event.Reception;

@RequestScoped
public class TrainingListProducer {

    @Inject
    private Logger log;

    @Inject
    private TrainingRepository trainingRepository;

    @Inject @Current
    private Team selectedTeam;

    private List<Training> trainings;

    private SortedSet<Date> trainingDates;

    // @Named provides access the return value via the EL variable name "trainings" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Training> getTrainings() {
        return trainings;
    }

    @Produces
    @Named
    public SortedSet<Date> getTrainingDates() {
        return trainingDates;
    }

    public void onSelectedTeamChanged(@Observes Team team) {
        log.fine("onSelectedTeamChanged() called, Team old=" + selectedTeam + " , Team new=" + team.toString());
        if (team != null) {
            log.fine(" , Team new=" + team.toString());
        } else {
            log.fine(" to ALL Teams, because selectedTeam = null");
        }
        selectedTeam = team;
        retrieveAllTrainings();
    }

    @PostConstruct
    public void retrieveAllTrainings() {
        log.fine("TrainingListProducer.retrieveAllTrainings()");
        if (selectedTeam != null) {
            log.fine("TrainingListProducer.retrieveAllTrainings() * for selectedTeam = " + selectedTeam);
            trainings = trainingRepository.findAllOfTeam(selectedTeam);
        } else {
            log.fine("TrainingListProducer.retrieveAllTrainings() * independent of Team");
            trainings = trainingRepository.findAll();
        }
        trainingDates = new TreeSet<Date>();

        for (Training t : trainings) {
            trainingDates.add(t.getEventDate());
        }
    }

}
