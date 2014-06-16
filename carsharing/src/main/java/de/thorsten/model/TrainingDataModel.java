/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.model;

import de.thorsten.data.TrainingDayListProducer;
import de.thorsten.util.DateUtil;
import java.util.Date;
import java.util.Iterator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.richfaces.model.CalendarDataModelItem;

/**
 *
 * @author Thorsten
 */
@ApplicationScoped
@Named("trainingCalendar")
public class TrainingDataModel extends TrainingCalendarModel {

    private static final String POTENTIAL_TRAINING_DAY_CLASS = "ptdc";

    @Inject
    private TrainingDayListProducer trainingDayListProducer;

    public void TrainingDataModel() {
        super.setDefaultModeEditable(true);
    }
    
   // @Inject
   // private Team selectedTeam;

    public CalendarDataModelItem[] getData(Date[] datesInCalendar) {
        CalendarDataModelItem[] modelItems = new CalendarDataModelItemImpl[datesInCalendar.length];
        for (int i = 0; i < datesInCalendar.length; i++) {
            CalendarDataModelItemImpl modelItem = new CalendarDataModelItemImpl();
            modelItem.setEnabled(isDefaultModeEditable()); // default!

            // Grau, wenn es sich um einen potentiellen Trainingstag handelt
            for (Integer t : this.getTrainingDays()) {
                if (t != null) {
                    if (DateUtil.getWeekday(datesInCalendar[i]) == t) {
                        modelItem.setStyleClass(POTENTIAL_TRAINING_DAY_CLASS);
                        modelItem.setEnabled(true); // Potentielles Trainingsdatum kann aktiviert werden
                    }
                }
            }

            // Grün, wenn ein Trainingstag aktiviert ist
            for (Date d : getDatesToBeHighlighted()) {
                if (d != null) {
                    if (DateUtil.getDatePortion(d).compareTo(
                            DateUtil.getDatePortion(datesInCalendar[i])) == 0) {
                        modelItem.setStyleClass(super.TRAINING_DAY_CLASS);
                        // bereits eingetragen lässt sich nicht mehr ändern
                        // 22.04.14: Jetzt doch wieder, da mehrere Trainings am Tag möglich sind.
                        //modelItem.setEnabled(false);
                    }
                }
            }
            modelItems[i] = modelItem;
        }
        return modelItems;
    }

    protected Integer[] getTrainingDays() {
        Integer[] trainingDays = new Integer[this.trainingDayListProducer.getTrainingDays().size()];
        int i = 0;
        for (final Iterator it = trainingDayListProducer.getTrainingDays().iterator(); it.hasNext();) {
            trainingDays[i] = ((TrainingDay) it.next()).getWeekday();
            i++;
        };
        return trainingDays;
    }
}
