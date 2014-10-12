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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.Query;

@ApplicationScoped
public class TeamRepository {

    @Inject
    private EntityManager em;

    public Team findById(Long id) {
        return em.find(Team.class, id);
    }

    public List<Team> findAll() {
        Query q = em.createQuery("SELECT t FROM Team t order by t.shortName");
        return q.getResultList();
    }
    
    public Team findByShortName(String shortName) {
        Query q = em.createQuery("SELECT t FROM Team t where t.shortName = :shortName order by t.shortName");
        q.setParameter("shortName", shortName);
        return (Team)q.getSingleResult();
    }

}
