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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

/**
 * @author Ilya Shaikovsky
 *
 */
public class CalendarDataModelImpl implements CalendarDataModel {

    /* (non-Javadoc)
     * @see org.richfaces.component.CalendarDataModel#getData(java.util.Date[])
     */
    
    private CalendarDataModelItem[] items;
    
    private String currentDescription;
    private String currentShortDescription;
    private Date currentDate;
    private boolean currentDisabled;
    
    
    /* (non-Javadoc)
     * @see org.richfaces.model.CalendarDataModel#getData(java.util.Date[])
     */
    public CalendarDataModelItem[] getData(Date[] dateArray) {
        if (dateArray == null) {
            return null;
        }
        if (items==null) {  
            items = new CalendarDataModelItem[dateArray.length];
            for (int i = 0; i < dateArray.length; i++) {
                items[i] = createDataModelItem(dateArray[i]);
            }
        }
        return items;
    }

    /**
     * @param date
     * @return CalendarDataModelItem for date
     */
    protected CalendarDataModelItem createDataModelItem(Date date) {
        CalendarDataModelItemImpl item = new CalendarDataModelItemImpl();
        Map data = new HashMap();
        data.put("shortDescription", "Nothing planned");
        data.put("description", "");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        item.setDay(c.get(Calendar.DAY_OF_MONTH));
        item.setEnabled(true);
        item.setStyleClass("rel-hol");
        item.setData(data); 
        return item;
    } 

    /* (non-Javadoc)
     * @see org.richfaces.model.CalendarDataModel#getToolTip(java.util.Date)
     */
    public Object getToolTip(Date date) {
    
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return items
     */
    public CalendarDataModelItem[] getItems() {
        return items;
    }

    /**
     * @param setter for items
     */
    public void setItems(CalendarDataModelItem[] items) {
        this.items = items;
    }

    /**
     * @param valueChangeEvent handling
     */
    public void valueChanged(ValueChangeEvent event) {
        System.out.println(event.getNewValue()+"selected");
        setCurrentDate((Date)event.getNewValue());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCurrentDate());
        setCurrentDescription((String)((HashMap)items[calendar.get(Calendar.DAY_OF_MONTH)-1].getData()).get("description"));
        setCurrentShortDescription((String)((HashMap)items[calendar.get(Calendar.DAY_OF_MONTH)-1].getData()).get("shortDescription"));
    }

    /**
     * Storing changes action
     */
    public void storeDayDetails() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCurrentDate());
        ((HashMap)items[calendar.get(Calendar.DAY_OF_MONTH)-1].getData()).put("shortDescription", getCurrentShortDescription());
        ((HashMap)items[calendar.get(Calendar.DAY_OF_MONTH)-1].getData()).put("description", getCurrentDescription());
    }
    
    /**
     * @return currentDescription
     */
    public String getCurrentDescription() {
        return currentDescription;
    }

    /**
     * @param currentDescription
     */
    public void setCurrentDescription(String currentDescription) {
        this.currentDescription = currentDescription;
    }

    /**
     * @return currentDisabled
     */
    public boolean isCurrentDisabled() {
        return currentDisabled;
    }

    /**
     * @param currentDisabled
     */
    public void setCurrentDisabled(boolean currentDisabled) {
        this.currentDisabled = currentDisabled;
    }

    /**
     * @return currentShortDescription
     */
    public String getCurrentShortDescription() {
        return currentShortDescription;
    }

    /**
     * @param currentShortDescription
     */
    public void setCurrentShortDescription(String currentShortDescription) {
        this.currentShortDescription = currentShortDescription;
    }

    /**
     * @return currentDate
     */
    public Date getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
    
}
