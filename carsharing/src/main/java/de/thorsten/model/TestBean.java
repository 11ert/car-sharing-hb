/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.model;

import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Thorsten
 */
@Named(value = "testBean")
@Dependent
public class TestBean {

    private Date date;
    
    /**
     * Creates a new instance of TestBean
     */
    public TestBean() {
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

}
