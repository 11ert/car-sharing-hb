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

import de.thorsten.model.ParticipationGroup;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;
import org.omnifaces.cdi.ViewScoped;


@ViewScoped
public class ParticipationGroupListProducer implements Serializable {

    public ParticipationGroupListProducer() {
    }

    @Inject
    private ParticipationGroupRepository participationGroupRepository;

    private List<ParticipationGroup> participationGroups;
    
    @Inject
    Logger log;

    @Produces
    @Named
    public List<ParticipationGroup> getParticipationGroups() {
        return participationGroups;
    }


    @PostConstruct
    public void loadStaticData() {
        participationGroups = participationGroupRepository.findAll();
    }

    public ParticipationGroup getParticipationGroupById(Long id) {
        ParticipationGroup group = null;
        for (ParticipationGroup currentParticipationGroup: participationGroups) {
            if (currentParticipationGroup.getId().equals(id)) {
                group = currentParticipationGroup;
            }
        }
        return group;
    }
}
