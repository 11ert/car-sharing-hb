/*
 * 
 */
package de.thorsten.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import de.thorsten.model.Participation;
import java.util.Date;
import java.util.logging.Logger;
import javax.persistence.TemporalType;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class ParticipationRepository {

    @Inject
    private Logger log;
    
    @Inject
    private EntityManager em;

    public Participation findById(Long id) {
        return em.find(Participation.class, id);
    }
 
    public List<Participation> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Participation> criteria = cb.createQuery(Participation.class);
        Root<Participation> participation = criteria.from(Participation.class);
        criteria.select(participation);
        return em.createQuery(criteria).getResultList();
    }
    
    public List<Participation> getAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Participation> criteria = cb.createQuery(Participation.class);
        Root<Participation> participation = criteria.from(Participation.class);
        criteria.select(participation).orderBy(cb.asc((participation.get("player").get("name")))); 
        return em.createQuery(criteria).getResultList();
    }

     
     public List<Participation> getAllForSpecificDateOrderedByName(Date curDate) {
        log.info("Getting all training participations for = " +  curDate + "...");
        return em.createQuery("SELECT p FROM Participation p WHERE p.trainingItem.currentDate = :curDate order by p.player.name")
                  .setParameter("curDate", curDate, TemporalType.DATE).getResultList();
    }
     
     public List<Participation> getAllForDateGreaterEqualOrderedByDateAndName(Date curDate) {
        log.info("Getting all training participations for = " +  curDate + "...");
        return em.createQuery("SELECT p FROM Participation p WHERE p.trainingItem.currentDate >= :curDate order by p.trainingItem.currentDate, p.player.name")
                  .setParameter("curDate", curDate, TemporalType.DATE).getResultList();
    }
}
