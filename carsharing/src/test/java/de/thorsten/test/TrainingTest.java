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

import de.thorsten.data.MemberRepository;
import de.thorsten.data.TeamRepository;
import de.thorsten.data.TrainingRepository;
import de.thorsten.model.Member;
import de.thorsten.model.SportsEvent;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import de.thorsten.model.Team;
import de.thorsten.model.Training;
import de.thorsten.model.SportsEventDetails;
import de.thorsten.service.MemberRegistration;
import de.thorsten.service.TeamService;
import de.thorsten.service.TrainingService;
import de.thorsten.util.Resources;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TrainingTest {

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Inject
    TrainingRepository trainingRepository;

    @Inject
    MemberRepository memberRepository;

    @Inject
    TeamRepository teamRepository;

    @Inject
    TeamService teamService;

    @Inject
    TrainingService trainingService;

    @Inject
    MemberRegistration memberRegistration;

    @Inject
    Logger log;

    private SportsEventDetails trainingDay;

    private SportsEventDetails trainingDayEins;

    private SportsEventDetails trainingDayZwei;

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test-carsharing.war")
                .addClasses(Training.class,
                        Resources.class,
                        Team.class,
                        Training.class,
                        TrainingRepository.class,
                        de.thorsten.model.SportsEventDetails.class,
                        SportsEvent.class,
                        TeamService.class,
                        SportsEventDetails.class,
                        MemberRegistration.class,
                        TrainingService.class,
                        MemberRepository.class,
                        Member.class,
                        TeamRepository.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Test
    public void test() throws Exception {
        generateTeams();
        generateMembers();
        generateTrainingDays();
        generateTrainings();

        utx.begin();
        em.joinTransaction();

        List<Team> teams = teamRepository.findAll();
        Assert.assertEquals(2, teams.size());

        Team teamEins = teamRepository.findById(new Long(1));
        Team teamZwei = teamRepository.findById(new Long(2));

        log.fine("**TEST** Alle Teams:");
        for (Team t : teams) {
            log.fine("**TEST** Team = " + t.toString());
        }

        List<Member> members = memberRepository.findAllOrderedByName();
        Assert.assertEquals(2, members.size());

        log.fine("**TEST** Alle Member:");
        for (Member t : members) {
            log.fine("**TEST** Member = " + t.toString());
        }

        List<Training> trainings = trainingRepository.findAll();
        Assert.assertEquals(2, trainings.size());

        log.fine("**TEST** Alle Trainings:");
        for (Training t : trainings) {
            log.fine("**TEST** Training = " + t.toString());
        }

        List<Training> trainingsForTeamEins = trainingRepository.findAllOfTeam(teamEins);
        Assert.assertEquals(1, trainingsForTeamEins.size());

        log.fine("**TEST** Alle Trainings für Team " + teamEins.getLongName());
        for (Training t : trainingsForTeamEins) {
            log.fine("**TEST** Training = " + t.toString());
        }

        List<Training> trainingsForTeamZwei = trainingRepository.findAllOfTeam(teamZwei);
        Assert.assertEquals(2, trainingsForTeamZwei.size());

        log.fine("**TEST** Alle Trainings für Team " + teamEins.getLongName());
        for (Training t : trainingsForTeamZwei) {
            log.fine("**TEST** Training = " + t.toString());
        }

        utx.commit();
    }

    private void generateTeams() throws Exception {
        // clear database  
        utx.begin();
        em.joinTransaction();

        Team teamEins = new Team();
        teamEins = new Team();
        teamEins.setShortName("*1*");
        teamEins.setLongName("Eins");

        try {
            teamService.update(teamEins);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Konnte Team " + teamEins + " nicht persistieren");
        }

        Team teamZwei = new Team();

        teamZwei = new Team();
        teamZwei.setLongName("Zwei");
        teamZwei.setShortName("*2*");

        try {
            teamService.update(teamZwei);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Konnte Team " + teamZwei + " nicht persistieren");
        }
        utx.commit();
    }

    private void generateMembers() {
        Member memberEins = new Member();
        memberEins.setFirstname("Eins Firstname");
        memberEins.setName("Eins Name");

        Member memberZwei = new Member();
        memberZwei.setFirstname("Zwei Firstname");
        memberZwei.setName("Zwei Name");

        try {
            memberRegistration.register(memberEins);
            memberRegistration.register(memberZwei);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Konnte Member nicht persistieren");
        }
    }

    private void generateTrainings() {

        Team teamEins = teamRepository.findById(new Long(1));
        Team teamZwei = teamRepository.findById(new Long(2));

        List teams = new ArrayList();
        teams.add(teamEins);
        teams.add(teamZwei);
        Training trainingEins = new Training();
        trainingEins.setTeams(teams);
        trainingEins.setEventDate(new Date());
        trainingEins.setSportsEventDetail(trainingDayEins);

        Training trainingZwei = new Training();
        teams = new ArrayList();
        teams.add(teamZwei);
        trainingZwei.setTeams(teams);
        trainingZwei.setEventDate(new Date());
        trainingZwei.setSportsEventDetail(trainingDayZwei);

        try {
            trainingService.update(trainingEins);
            trainingService.update(trainingZwei);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Konnte Trainings nicht persistieren");
        }
    }

    private void generateTrainingDays() {
        SportsEventDetails trainingDay = new SportsEventDetails();
        trainingDay.setWeekday(1);

        trainingDayEins = new SportsEventDetails();
        trainingDayEins.setLocation("Entenhausen");
        trainingDayEins.setPickUpLocationSource("PickUpLocationSource");
        trainingDayEins.setPickUpLocationTarget("PickUpLocationTarget");
        trainingDayEins.setPickUpTimeSource("10:00");
        trainingDayEins.setPickUpTimeTarget("12:00");
        trainingDayEins.setWeekday(1);
        trainingDayEins.setTimeFrom("10:30");
        trainingDayEins.setTimeTo("10:35");

        trainingDayZwei = new SportsEventDetails();
        trainingDayZwei.setLocation("Entenhausen Zwei");
        trainingDayZwei.setPickUpLocationSource("PickUpLocationSource Zwei");
        trainingDayZwei.setPickUpLocationTarget("PickUpLocationTarget Zwei");
        trainingDayZwei.setPickUpTimeSource("10:02");
        trainingDayZwei.setPickUpTimeTarget("12:02");
        trainingDayZwei.setTimeFrom("10:30");
        trainingDayZwei.setTimeTo("10:35");

    }
}
