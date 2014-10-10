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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import de.thorsten.model.SportsEventDetails;
import javax.persistence.Query;

@ApplicationScoped
public class TrainingDayRepository {

    @Inject
    private EntityManager em;

    public SportsEventDetails findById(Long id) {
        return em.find(SportsEventDetails.class, id);
    }


    public List<SportsEventDetails> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SportsEventDetails> criteria = cb.createQuery(SportsEventDetails.class);
        Root<SportsEventDetails> trainingDay = criteria.from(SportsEventDetails.class);
        criteria.select(trainingDay);
        return em.createQuery(criteria).getResultList();
    }
    
    public List<SportsEventDetails> findByWeekday(int weekday) {
        Query q = em.createQuery("SELECT t FROM SportsEventDetails t WHERE t.weekday = :weekday");
        q.setParameter("weekday", weekday);
        return q.getResultList();
    }
    
    public List<SportsEventDetails> findAllForTeam(Team team) {
        Query q = em.createQuery("SELECT t FROM SportsEventDetailss t WHERE WHERE :team MEMBER OF t.teams");
        q.setParameter("team", team);
        return q.getResultList();
    }
    
}
