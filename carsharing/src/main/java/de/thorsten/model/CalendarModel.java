/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.model;

/**
 *
 * @author Thorsten
 */
import de.thorsten.data.TrainingListProducer;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

/**
 * @author Ilya Shaikovsky
 *
 */
@ApplicationScoped
@Named("calendarModel")
public class CalendarModel implements CalendarDataModel {

    private static final String TRAINING_DAY_CLASS = "tdc";
    
    @Inject
    private TrainingListProducer trainingListProducer;

    @Inject
    private Logger log;

    private Date selectedDate;

    public CalendarDataModelItem[] getData(Date[] datesInCalendar) {
        CalendarDataModelItem[] modelItems = new CalendarDataModelItemImpl[datesInCalendar.length];
        for (int i = 0; i < datesInCalendar.length; i++) {
            CalendarDataModelItemImpl modelItem = new CalendarDataModelItemImpl();
            modelItem.setEnabled(true);
            for (Date d : getDatesToBeHighlighted()) {
                if (d != null) {
                    if (getDatePortion(d).compareTo(
                            getDatePortion(datesInCalendar[i])) == 0) {
                            log.info("Datümer " + d.toString() + " sind gleich!");
                            modelItem.setEnabled(false);
                            modelItem.setStyleClass(TRAINING_DAY_CLASS);                    }
                }
            }
            modelItems[i] = modelItem;
        }
        return modelItems;
    }

    public Object getToolTip(Date date) {
        return null;
    }

    private Date[] getDatesToBeHighlighted() {
        Date[] dates = new Date[trainingListProducer.getTrainingDates().size()];
        int i = 0;
        for (final Iterator it = trainingListProducer.getTrainingDates().iterator(); it.hasNext();) {
            dates[i] = (Date) it.next();
            log.info(i + ".Trainingdate: " + ((Date)dates[i]).toString());
            i++;
        };
        return dates;
    }

    //This method skips the time part and retuns the date part only
    private Date getDatePortion(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;

    }

    public Date getSelectedDate() {
        return selectedDate;

    }

}
