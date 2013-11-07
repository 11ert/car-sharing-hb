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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import de.thorsten.model.TrainingDay;
import javax.persistence.Query;

@ApplicationScoped
public class TrainingDayRepository {

    @Inject
    private EntityManager em;

    public TrainingDay findById(Long id) {
        return em.find(TrainingDay.class, id);
    }


    public List<TrainingDay> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TrainingDay> criteria = cb.createQuery(TrainingDay.class);
        Root<TrainingDay> trainingDay = criteria.from(TrainingDay.class);
        criteria.select(trainingDay);
        return em.createQuery(criteria).getResultList();
    }
    
    public List<TrainingDay> findByWeekday(int weekday) {
        Query q = em.createQuery("SELECT t FROM TrainingDay t WHERE t.weekday = :weekday");
        q.setParameter("weekday", weekday);
        return q.getResultList();
  
    }
    
}
