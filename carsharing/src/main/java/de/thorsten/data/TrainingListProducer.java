package de.thorsten.data;

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

@RequestScoped
public class TrainingListProducer {

    @Inject
    private Logger log;

    @Inject
    private TrainingRepository trainingRepository;

    private List<Training> trainings;

    private SortedSet<Integer> calWeeks;
    
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
    public SortedSet<Integer> getCalWeeks() {
        return calWeeks;
    }
    
    @Produces
    @Named
    public SortedSet<Date> getTrainingDates() {
        return trainingDates;
    }

    
    @PostConstruct
    public void retrieveAllTrainings() {
        trainings = trainingRepository.findAll();

        calWeeks = new TreeSet<Integer>();
        trainingDates = new TreeSet<Date>();
        for (Training t : trainings) {
            //calWeeks.add(t.getCalendarWeek());
            //trainingDates.add(t.getCurrentDate().getTime());
            trainingDates.add(t.getCurrentDate());
        }
        log.info("CalWeek Anzahl Eintraege" + calWeeks.size());
        log.info("TrainingDates Anzahl Eintraege" + trainingDates.size());

    }
}
