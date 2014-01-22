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
package de.thorsten.test;

import de.thorsten.controller.CreateNewTrainingController;
import de.thorsten.data.MemberRepository;
import de.thorsten.data.TrainingDayRepository;
import de.thorsten.data.TrainingRepository;
import de.thorsten.model.Game;
import de.thorsten.model.Member;
import de.thorsten.model.Participation;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import de.thorsten.model.SportsEvent;
import de.thorsten.model.Team;
import de.thorsten.model.Training;
import de.thorsten.model.TrainingDay;
import de.thorsten.service.ParticipationService;
import de.thorsten.service.TeamService;
import de.thorsten.service.TrainingService;
import de.thorsten.util.Resources;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import junit.framework.Assert;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SportEventTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(SportsEvent.class,
                CreateNewTrainingController.class,
                Resources.class,
                Training.class,
                Team.class,
                TrainingRepository.class,
                TrainingDay.class,
                TrainingDayRepository.class,
                MemberRepository.class,
                ParticipationService.class,
                Participation.class,
                TrainingService.class,
                TeamService.class,
                Member.class,
                Game.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    Logger log;

    @Inject
    private TrainingRepository trainingRepository;

    @Inject
    private TrainingService trainingService;

    @Inject
    private TeamService teamService;

    @Test
    public void testCreateTraining() throws Exception {
        Training newTraining = new Training();
        newTraining.setEventDate(new Date());
        newTraining.setLocation("irgendwo");
        newTraining.setTimeFrom("10:00");
        newTraining.setTimeTo("12:00");
        newTraining.setPickUpLocationSource("Von");
        newTraining.setPickUpLocationTarget("Bis");
        newTraining.setPickUpTimeSource("09:00");
        newTraining.setPickUpTimeTarget("13:00");

        Team firstTeam = new Team();
        Team secondTeam = new Team();

        firstTeam.setId(new Long(1));
        firstTeam.setShortName("first");
        firstTeam.setLongName("first team");
        teamService.update(firstTeam);

        secondTeam.setId(new Long(2));
        secondTeam.setShortName("second");
        secondTeam.setLongName("second Team");
        teamService.update(secondTeam);
        List teams = new ArrayList();
        teams.add(firstTeam);
        teams.add(secondTeam);

        newTraining.setTeams(teams);

        trainingService.update(newTraining);

        List<Training> allTrainings = trainingRepository.findAll();
        Assert.assertEquals(1, allTrainings.size());

        Training tempTraining = new Training();
        for (Training t : allTrainings) {
            log.info("Neues Training hat ID " + t.getId() + " bekommen");
            tempTraining = t;
            Assert.assertEquals("irgendwo", tempTraining.getLocation());
        }

        Assert.assertEquals(2, tempTraining.getTeams().size());

        newTraining.setEventDate(new Date());
        newTraining.setLocation("irgendwo_2");
        newTraining.setTimeFrom("10:00");
        newTraining.setTimeTo("12:00");
        newTraining.setPickUpLocationSource("Von");
        newTraining.setPickUpLocationTarget("Bis");
        newTraining.setPickUpTimeSource("09:00");
        newTraining.setPickUpTimeTarget("13:00");

        teams.add(firstTeam);

        Training updatedTraining = trainingService.update(newTraining);

        log.info("Neues Training hat ID " + updatedTraining.getId() + " bekommen");
        Assert.assertEquals("irgendwo_2", trainingRepository.findById(updatedTraining.getId()).getLocation());

    }
}
