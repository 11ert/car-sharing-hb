/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.thorsten.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import de.thorsten.model.Participation;
import de.thorsten.util.DateUtil;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

@RequestScoped
@Named
public class ParticipationListProducer {

    @Inject
    private ParticipationRepository participationRepository;

    @Inject
    private Logger log;

    private List<Participation> participations;

    private Date trainingDate = new Date();
    
    private int numberOfParticipators;
    
    private int numberOfDriversBack;
    
    private int numberOfDriversForth;
    
    private int numberOfSeatsForthAvailable;
    
    private int numberOfSeatsBackAvailable;
    
    private int numberOfSeatsBackRequired;
    
    private int numberOfSeatsForthRequired;
    
    @Produces
    @Named
    public int getNumberOfParticipators() {
        return numberOfParticipators;
    }
    
    public int getNumberOfDriversBack() {
        return numberOfDriversBack;
    }
    
    public int getNumberOfDriversForth() {
        return numberOfDriversForth;
    }
    
    @Produces
    @Named
    public int getNumberOfSeatsBackRequired() {
        log.info("return numberOfSeatsBackRequired" + numberOfSeatsBackRequired);
        return numberOfSeatsBackRequired;
    }
    
    @Produces
    @Named
    public int getNumberOfSeatsForthRequired() {
        log.info("return numberOfSeatsForthRequired=" + numberOfSeatsForthRequired);
        return numberOfSeatsForthRequired;
    }

    public int getNumberOfSeatsBackAvailable() {
        return numberOfSeatsBackRequired;
    }
    
    public int getNumberOfSeatsForthAvailable() {
        return numberOfSeatsForthRequired;
    }

    
    
    // @Named provides access the return value via the EL variable name "participation" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Participation> getParticipations() {
        log.info("GetParticipations called " + participations.size() + " Elements");
        return participations;
    }
    
    public void setTrainingDate(Date x) {
        log.info("setTrainingDate called " + x.toString());
        trainingDate = x;
        retrieveAllParticipators();
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public String getTrainingDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(trainingDate);
    }

    /**
     * funktioniert nicht !!
     * @param participation 
     */
     public void onParticipationListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Participation participation) {
         log.info("Observer called");
        retrieveAllParticipators();
     }
     
    @PostConstruct
    public void retrieveAllParticipators() {
        log.info("retrieveAllParticipators for "+ trainingDate.toString());
        participations = participationRepository.getAllForSpecificDateOrderedByName(trainingDate);
        calculateParticipationAttributes();
    }

    private void calculateParticipationAttributes() {
        numberOfParticipators = 0;
        for (Participation p : participations) {
            log.info(" Just read -> " + p.getId() + "; " + p.getTrainingItem().getCurrentDate());
            if (p.isParticipating())
                numberOfParticipators++;
            if (p.isDrivingBack())
                numberOfSeatsBackAvailable = numberOfDriversBack + p.getPlayer().getCarsize();
            if (p.isDrivingForth())
                numberOfSeatsForthAvailable = numberOfDriversForth + p.getPlayer().getCarsize();
        }
        numberOfSeatsBackRequired = numberOfParticipators - numberOfSeatsBackAvailable;
        numberOfSeatsForthRequired = numberOfParticipators - numberOfSeatsForthAvailable;
        
        log.info("Calculated NumberOfSeatsBackRequired = " + numberOfSeatsBackRequired);
        log.info("Calculated NumberOfSeatsForthRequired = " + numberOfSeatsForthRequired);
        log.info("Calculated NumberOfParticipators = " + numberOfParticipators);
        
    }
}
