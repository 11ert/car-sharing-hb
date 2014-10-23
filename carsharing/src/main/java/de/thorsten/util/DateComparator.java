/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author thorsten.elfert@gmail.com
 */
public class DateComparator implements Comparator<Date> {

    @Inject
    private Logger log;

    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public int compare(Date d1, Date d2) {
        System.out.println("d1 = " + DATE_FORMAT.format(d1) + ", d2 = " + DATE_FORMAT.format(d2));
        return DATE_FORMAT.format(d1).compareTo(DATE_FORMAT.format(d2));
    }

}
