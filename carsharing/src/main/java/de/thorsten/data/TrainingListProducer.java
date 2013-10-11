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

import de.thorsten.model.Training;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

@RequestScoped
public class TrainingListProducer {

    @Inject
    private Logger log;

    @Inject
    private TrainingRepository trainingRepository;

    private List<Training> trainings;

    private SortedSet<Integer> calWeeks;
    
    private SortedSet<Date> trainingDates;
    
    
    // @Named provides access the return value via the EL variable name "trainings" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Training> getTrainings() {
        return trainings;
    }

    
    @Produces
    @Named
    public SortedSet<Integer> getCalWeeks() {
        return calWeeks;
    }
    
    @Produces
    @Named
    public SortedSet<Date> getTrainingDates() {
        return trainingDates;
    }

    
    @PostConstruct
    public void retrieveAllTrainings() {
        trainings = trainingRepository.findAll();

        calWeeks = new TreeSet<Integer>();
        trainingDates = new TreeSet<Date>();
        for (Training t : trainings) {
            //calWeeks.add(t.getCalendarWeek());
            //trainingDates.add(t.getCurrentDate().getTime());
            trainingDates.add(t.getCurrentDate());
        }
        log.info("CalWeek Anzahl Eintraege" + calWeeks.size());
        log.info("TrainingDates Anzahl Eintraege" + trainingDates.size());

    }
}
