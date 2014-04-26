/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.thorsten.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Thorsten
 */
@ApplicationScoped
@Named
public class StaticData {
    
    private HashMap<String,Integer> weekdays;
    
    @Inject
    private Logger log;
    
    @PostConstruct
    public void init() {
        weekdays = new HashMap<String,Integer>();
        weekdays.put("Samstag", Calendar.SATURDAY);
        weekdays.put("Sonntag", Calendar.SUNDAY);
        weekdays.put("Montag", Calendar.MONDAY);
        weekdays.put("Dienstag", Calendar.TUESDAY);
        weekdays.put("Mittwoch", Calendar.WEDNESDAY);
        weekdays.put("Donnerstag", Calendar.THURSDAY);
        weekdays.put("Freitag", Calendar.FRIDAY);
        
        System.out.println("StaticData.init, weekdays=" + weekdays);
    }

    /**
     * @return the weekdays
     */
    @Named
    @Produces
    public HashMap<String, Integer> getWeekdays() {
        System.out.println("getWeekdays " + weekdays);
        return weekdays;
    }

    /**
     * @param weekdays the weekdays to set
     */
    public void setWeekdays(HashMap<String, Integer> weekdays) {
        this.weekdays = weekdays;
    }
}
