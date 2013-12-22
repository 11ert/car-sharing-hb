/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 *
 * @author Thorsten
 */
public final class DateUtil {
    
//This method skips the time part and retuns the date part only
    public static Date getDatePortion(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static int getWeekday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getSelectedDateAsFormattedString(Date date) {
        String dateString;
        if (date != null ) {
            DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMANY);
            dateString = sdf.format(date);
        } else {
            dateString = "";
        }
        return dateString;
    }

    
}
