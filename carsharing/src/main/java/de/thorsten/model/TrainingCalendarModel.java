package de.thorsten.model;

/**
 *
 * @author Thorsten
 */
import de.thorsten.data.GameListProducer;
import de.thorsten.data.TrainingListProducer;
import de.thorsten.util.DateUtil;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

public class TrainingCalendarModel implements CalendarDataModel {

    protected static final String TRAINING_DAY_CLASS = "tdc";

    @Inject
    private TrainingListProducer trainingListProducer;

    @Inject
    private GameListProducer gameListProducer;

    @Inject
    protected Logger log;

    private Date selectedDate;

    private boolean defaultModeEditable = false;
    
   public String getSelectedDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(selectedDate);
    }

    public CalendarDataModelItem[] getData(Date[] datesInCalendar) {
        CalendarDataModelItem[] modelItems = new CalendarDataModelItemImpl[datesInCalendar.length];
        for (int i = 0; i < datesInCalendar.length; i++) {
            CalendarDataModelItemImpl modelItem = new CalendarDataModelItemImpl();
            modelItem.setEnabled(isDefaultModeEditable()); // default!
            for (Date d : getDatesToBeHighlighted()) {
                if (d != null) {
                    if (DateUtil.getDatePortion(d).compareTo(
                            DateUtil.getDatePortion(datesInCalendar[i])) == 0) {
                        modelItem.setStyleClass(TRAINING_DAY_CLASS);
                        modelItem.setEnabled(true);
                    }
                }
            }
            modelItems[i] = modelItem;
        }
        return modelItems;
    }

    public TrainingCalendarModel() {
        selectedDate = new Date();
    }

    public Object getToolTip(Date date) {
        return "Training";
    }

    protected Date[] getDatesToBeHighlighted() {

        SortedSet<Date> sportEventDates = trainingListProducer.getTrainingDates();
 
        Date[] dates = new Date[sportEventDates.size()];

        int i = 0;
        for (final Iterator it = sportEventDates.iterator(); it.hasNext();) {
            dates[i] = (Date) it.next();
            i++;
        };
        return dates;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
        log.info("setSelectedDate() called");
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    /**
     * @return the defaultModeEditable
     */
    public boolean isDefaultModeEditable() {
        return defaultModeEditable;
    }

    /**
     * @param defaultModeEditable the defaultModeEditable to set
     */
    public void setDefaultModeEditable(boolean defaultModeEditable) {
        this.defaultModeEditable = defaultModeEditable;
    }

}
