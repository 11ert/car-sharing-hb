    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Thorsten
 */
@ApplicationScoped
//@Named("trainingDataModelReadOnly")
public class TrainingDataModelReadOnly extends TrainingCalendarModel {
    
    public void TrainingDataModel() {
        log.info("TrainingDataModel()");
        super.setDefaultModeEditable(false);
    }
}
