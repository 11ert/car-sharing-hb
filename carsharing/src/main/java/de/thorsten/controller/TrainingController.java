/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.controller;

import de.thorsten.data.TrainingDayRepository;
import de.thorsten.model.Training;
import de.thorsten.model.TrainingDay;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

/**
 *
 * @author QTK100
 */
@ManagedBean(name = "trainingController")
@ViewScoped
public class TrainingController implements Serializable {

    @Inject
    Logger log;

    @Inject
    TrainingDayRepository trainingDayRepository;

    private Date nextTrainingDate;

    /**
     * @return the nextTrainingDate
     */
    public Date getNextTrainingDate() {
        return nextTrainingDate;
    }

    /**
     * @param nextTrainingDate the nextTrainingDate to set
     */
    public void setNextTrainingDate(Date nextTrainingDate) {
        log.info("!!!!!!!!!!! setNextTrainingDate() " + nextTrainingDate);
        this.nextTrainingDate = nextTrainingDate;
    }

    public void createParticipationsForNextTrainingDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextTrainingDate);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        log.info("looking for trainingDay " + weekday);
        List trDayList = trainingDayRepository.findByWeekday(weekday);
        if (trDayList != null) {
            if (trDayList.size() > 0) {
                Training newTraining = new Training();
                newTraining.setCurrentDate(nextTrainingDate);
                newTraining.setTrainingDay((TrainingDay) trDayList.get(0));
                log.info("Neuer Trainingseintrag " + newTraining.getTrainingDateAsString()
                        + ", " + newTraining.getTrainingDay().getLocation()
                        + ", " + newTraining.getTrainingDay().getTimeFrom()
                        + " - " + newTraining.getTrainingDay().getTimeTo());
            }
        } else {
            log.info("Kein TrainingsDay für " + weekday);
        }

    }

    public void trainingDateChanged(ValueChangeEvent event) {
        log.info("trainingDateChanged called !");

        if (event.getNewValue() != null) {
            log.info("Next TrainingDate = " + event.getNewValue());
            nextTrainingDate = (Date) event.getNewValue();
        }
    }
}
