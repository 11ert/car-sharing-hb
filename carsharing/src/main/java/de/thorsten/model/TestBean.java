/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.model;

import java.util.Date;
import javax.inject.Named;

/**
 *
 * @author Thorsten
 */
@Named(value = "testBean")
public class TestBean {

    private Date testDate;
    
    /**
     * Creates a new instance of TestBean
     */
    public TestBean() {
        testDate = new Date();
    }

    public Date getTestDate() {
        return testDate;
    }
    
    public void setTestDate(Date x) {
        testDate = x;
    }

}
