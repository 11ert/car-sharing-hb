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
import de.thorsten.model.TrainingDay;
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
public class TeamTest {

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

    private TrainingDay trainingDay;

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test-carsharing.war")
                .addClasses(Training.class,
                        Resources.class,
                        Team.class,
                        Training.class,
                        TrainingRepository.class,
                        de.thorsten.model.TrainingDay.class,
                        SportsEvent.class,
                        TeamService.class,
                        TrainingDay.class,
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
        
        Assert.assertEquals(teamEins, teamRepository.findByShortName("*1*"));
        Assert.assertEquals(teamZwei, teamRepository.findByShortName("*2*"));

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

}
