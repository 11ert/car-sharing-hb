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
import de.thorsten.data.ParticipationRepository;
import de.thorsten.data.TeamRepository;
import de.thorsten.data.TrainingRepository;
import de.thorsten.model.Member;
import de.thorsten.model.Participation;
import de.thorsten.model.SportsEvent;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import de.thorsten.model.Team;
import de.thorsten.model.Training;
import de.thorsten.model.SportsEventDetails;
import de.thorsten.service.MemberRegistration;
import de.thorsten.service.ParticipationService;
import de.thorsten.service.TeamService;
import de.thorsten.service.TrainingService;
import de.thorsten.util.Resources;
import java.util.ArrayList;
import java.util.Calendar;
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
    ParticipationService participationService;

    @Inject
    ParticipationRepository participationRepository;

    @Inject
    Logger log;

    private Member memberEins;
    private Member memberZwei;

    private Training trainingEins;
    private Training trainingZwei;
    private Training trainingDrei;
    private Training trainingVier;

    private SportsEventDetails trainingDayEins;
    private SportsEventDetails trainingDayZwei;

    private Participation participation1;
    private Participation participation2;
    private Participation participation3;
    private Participation participation4;

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
                        Participation.class,
                        ParticipationService.class,
                        ParticipationRepository.class,
                        TeamRepository.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Test
    public void test() throws Exception {
        utx.begin();
        em.joinTransaction();

        generateTeams();
        generateMembers();
        generateTrainingDays();
        generateTrainings();

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
        Assert.assertEquals(4, trainings.size());

        log.fine("**TEST** Alle Trainings:");
        for (Training t : trainings) {
            log.fine("**TEST** Training = " + t.toString());
        }

        List<Training> trainingsForTeamEins = trainingRepository.findAllOfTeam(teamEins);
        Assert.assertEquals(3, trainingsForTeamEins.size());

        log.fine("**TEST** Alle Trainings für Team " + teamEins.getLongName());
        for (Training t : trainingsForTeamEins) {
            log.fine("**TEST** Training = " + t.toString());
        }

        List<Training> trainingsForTeamZwei = trainingRepository.findAllOfTeam(teamZwei);
        Assert.assertEquals(4, trainingsForTeamZwei.size());

        log.fine("**TEST** Alle Trainings für Team " + teamEins.getLongName());
        for (Training t : trainingsForTeamZwei) {
            log.fine("**TEST** Training = " + t.toString());
        }

        Calendar cal = Calendar.getInstance();
        cal.set(2010, Calendar.JANUARY, 1); //Year, month and day of month
        Date von = cal.getTime();

        cal.set(2015, Calendar.JANUARY, 1); //Year, month and day of month
        Date bis = cal.getTime();

        List< Training> trainingsFromTo = trainingRepository.findAllOfTrainingDayBetweenTimeRange(trainingDayEins, von, bis);
        Assert.assertEquals(2, trainingsFromTo.size());

        participation1 = new Participation();

        participation1.setPlayer(memberEins);
        participation1.setPlayer(memberZwei);
        participation1.setTraining(trainingEins);
        log.fine("**TEST** Participation1 = " + participation1.toString());

        participationService.update(participation1);

        List<Participation> listOfParticipations = participationRepository.getAll();
        Assert.assertEquals(1, listOfParticipations.size());

        Participation tmpParticipation = listOfParticipations.get(0);
        Assert.assertNotNull(tmpParticipation);

        participationService.remove(tmpParticipation);

        listOfParticipations = participationRepository.getAll();
        Assert.assertEquals(0, listOfParticipations.size());

        utx.commit();
    }

    private void generateTeams() throws Exception {
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
    }

    private void generateMembers() throws Exception {

        memberEins = new Member();
        memberEins.setFirstname("Eins Firstname");
        memberEins.setName("Eins Name");

        memberZwei = new Member();
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

    private void generateTrainings() throws Exception {

        Team teamEins = teamRepository.findById(new Long(1));
        Team teamZwei = teamRepository.findById(new Long(2));

        Calendar cal = Calendar.getInstance();
        cal.set(2014, Calendar.JANUARY, 1); //Year, month and day of month
        Date januar = cal.getTime();

        cal.set(2014, Calendar.MARCH, 1);
        Date maerz = cal.getTime();

        cal.set(2014, Calendar.DECEMBER, 1);
        Date dezember = cal.getTime();

        cal.set(2020, Calendar.DECEMBER, 1);
        Date dezember_2020 = cal.getTime();

        List teams = new ArrayList();
        teams.add(teamEins);
        teams.add(teamZwei);
        trainingEins = new Training();
        trainingEins.setTeams(teams);
        trainingEins.setEventDate(januar);
        trainingEins.setSportsEventDetail(trainingDayEins);

        trainingZwei = new Training();
        teams = new ArrayList();
        teams.add(teamZwei);
        trainingZwei.setTeams(teams);
        trainingZwei.setEventDate(maerz);
        trainingZwei.setSportsEventDetail(trainingDayZwei);

        trainingDrei = new Training();
        teams = new ArrayList();
        teams.add(teamEins);
        teams.add(teamZwei);
        trainingDrei.setTeams(teams);
        trainingDrei.setSportsEventDetail(trainingDayEins);
        trainingDrei.setEventDate(dezember);

        teams = new ArrayList();
        teams.add(teamEins);
        teams.add(teamZwei);
        trainingVier = new Training();
        trainingVier.setTeams(teams);
        trainingVier.setEventDate(dezember_2020);
        trainingVier.setSportsEventDetail(trainingDayEins);

        try {
            trainingService.update(trainingEins);
            trainingService.update(trainingZwei);
            trainingService.update(trainingDrei);
            trainingService.update(trainingVier);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Konnte Trainings nicht persistieren");
        }
    }

    private void generateTrainingDays() throws Exception {

        SportsEventDetails tmpTrainingDay = new SportsEventDetails();

        tmpTrainingDay = new SportsEventDetails();
        tmpTrainingDay.setLocation("Entenhausen");
        tmpTrainingDay.setPickUpLocationSource("PickUpLocationSource");
        tmpTrainingDay.setPickUpLocationTarget("PickUpLocationTarget");
        tmpTrainingDay.setPickUpTimeSource("10:00");
        tmpTrainingDay.setPickUpTimeTarget("12:00");
        tmpTrainingDay.setWeekday(1);
        tmpTrainingDay.setTimeFrom("10:30");
        tmpTrainingDay.setTimeTo("10:35");

        trainingDayEins = (SportsEventDetails) em.merge(tmpTrainingDay);

        Assert.assertNotNull(trainingDayEins);
        Assert.assertNotNull(trainingDayEins.getId());

        tmpTrainingDay = new SportsEventDetails();

        tmpTrainingDay.setLocation("Entenhausen Zwei");
        tmpTrainingDay.setPickUpLocationSource("PickUpLocationSource Zwei");
        tmpTrainingDay.setPickUpLocationTarget("PickUpLocationTarget Zwei");
        tmpTrainingDay.setPickUpTimeSource("10:02");
        tmpTrainingDay.setPickUpTimeTarget("12:02");
        tmpTrainingDay.setWeekday(2);
        tmpTrainingDay.setTimeFrom("10:30");
        tmpTrainingDay.setTimeTo("10:35");

        trainingDayZwei = (SportsEventDetails) em.merge(tmpTrainingDay);

        Assert.assertNotNull(trainingDayZwei);
        Assert.assertNotNull(trainingDayZwei.getId());
    }
}
