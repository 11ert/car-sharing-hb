
package de.thorsten.model;

/**
 *
 * @author Thorsten
 */
import de.thorsten.data.TrainingListProducer;
import de.thorsten.util.DateUtil;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.ValueChangeEvent;

import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;


@ApplicationScoped
@Named("calendarModel")
public class CalendarModel implements CalendarDataModel {

    private static final String TRAINING_DAY_CLASS = "tdc";

    @Inject
    private TrainingListProducer trainingListProducer;

    @Inject
    private Logger log;

    private Date selectedDate;
    
    public String getSelectedDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(selectedDate);
    }
            
            
    public CalendarDataModelItem[] getData(Date[] datesInCalendar) {
        CalendarDataModelItem[] modelItems = new CalendarDataModelItemImpl[datesInCalendar.length];
        for (int i = 0; i < datesInCalendar.length; i++) {
            CalendarDataModelItemImpl modelItem = new CalendarDataModelItemImpl();
            modelItem.setEnabled(false); // default!
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

    public CalendarModel () {
        selectedDate = new Date();
    }
    public Object getToolTip(Date date) {
        return "Training";
    }

    private Date[] getDatesToBeHighlighted() {
        Date[] dates = new Date[trainingListProducer.getTrainingDates().size()];
        int i = 0;
        for (final Iterator it = trainingListProducer.getTrainingDates().iterator(); it.hasNext();) {
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


}
