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

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import de.thorsten.model.Member;
import de.thorsten.model.Team;
import de.thorsten.service.MemberRegistration;
import de.thorsten.util.Resources;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MemberRegistrationTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Member.class, MemberRegistration.class, Resources.class, Team.class, MemberRepository.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    MemberRegistration memberRegistration;

    @Inject
    MemberRepository memberRepository;

    @Inject
    Logger log;

    @Before
    public void testRegister() throws Exception {
        Member newMember = new Member();
        newMember.setName("Jane");
        newMember.setEmail("jane@mailinator.com");
        newMember.setPhoneNumber("2125551234");
        Team newTeam = new Team();
        newTeam.setId(new Long(1));
        newTeam.setLongName("First team");
        newTeam.setShortName("1. Team");
        memberRegistration.register(newMember);
        log.info("**TEST** " + newMember.getName() + " was persisted");

        Member newMember2 = new Member();
        newMember2.setName("Tarzan");
        newMember2.setEmail("tarzan@mailinator.com");
        newMember2.setPhoneNumber("4623764");
        newTeam = new Team();
        newTeam.setId(new Long(1));
        newTeam.setLongName("Second team");
        newTeam.setShortName("2. Team");
        memberRegistration.register(newMember2);
        log.info("**TEST** " + newMember2.getName() + " was persisted");

    }

    @Test()
    public void testFindAll() throws Exception {
        List<Member> allMembers = new ArrayList();
        allMembers = memberRepository.findAllOrderedByName();
        log.info("**TEST** MemberRegistration.testFindAll()");
        for (Member m : allMembers) {
            log.info("**TEST** Member: " + m.toString());
        }
        Assert.assertEquals(2, allMembers.size());

        Member member = memberRepository.findByEmail("jane@mailinator.com");
        Assert.assertEquals("Jane", member.getName());
    }

}
