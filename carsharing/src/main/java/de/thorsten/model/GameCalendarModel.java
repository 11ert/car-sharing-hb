package de.thorsten.model;

/**
 *
 * @author Thorsten
 */
import de.thorsten.data.GameListProducer;
import de.thorsten.util.DateUtil;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

@ApplicationScoped
@Named("gameCalendarModel")
public class GameCalendarModel implements CalendarDataModel {

    private static final String GAME_DAY_CLASS = "gdc";

    @Inject
    private GameListProducer gameListProducer;
    
    @Inject
    protected Logger log;

    private Date selectedDate;

    private boolean defaultModeEditable = true;

    public String getSelectedDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(selectedDate);
    }

    public CalendarDataModelItem[] getData(Date[] datesInCalendar) {
        CalendarDataModelItem[] modelItems = new CalendarDataModelItemImpl[datesInCalendar.length];

        for (int i = 0; i < datesInCalendar.length; i++) {
            CalendarDataModelItemImpl modelItem = new CalendarDataModelItemImpl();
            modelItem.setEnabled(isDefaultModeEditable()); // default!
            // Matches ermitteln
            for (Game element : gameListProducer.getGames()) {
                if (element != null) {
                    if (DateUtil.getDatePortion(element.getEventDate()).compareTo(
                            DateUtil.getDatePortion(datesInCalendar[i])) == 0) {
                        modelItem.setStyleClass(GAME_DAY_CLASS);
                        modelItem.setEnabled(true);
                    }
                }
            }
            modelItems[i] = modelItem;
        }
        return modelItems;
    }
        
    

    public GameCalendarModel() {
        selectedDate = new Date();
    }

    public Object getToolTip(Date date) {
        return "Spiel";
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
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
