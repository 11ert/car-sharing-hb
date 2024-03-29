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

import de.thorsten.model.Team;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;


@RequestScoped
public class TeamListProducer {

    @Inject
    private TeamRepository teamRepository;

    private List<Team> teams;
    
    @Inject
    private Logger log;

    @Produces
    @Named
    public List<Team> getTeams() {
        log.fine("TeamListProducer.getTeams()");
        for (Team t : teams) {
            log.fine("Team loaded: " + t.toString());
        }
        return teams;
    }
    
    public Team getTeamByShortName(String shortName) {
        return teamRepository.findByShortName(shortName);
    }


    @PostConstruct
    public void retrieveAllTeams() {
        log.fine("TeamListProducer.retrieveAllTeams()");
        teams = teamRepository.findAll();
        for (Team t : teams) {
            log.fine("Team loaded: " + t.toString());
        }
    }
}
