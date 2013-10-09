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
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class ParticipationRepository {

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
    
    public List<Participation> getAllByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Participation> criteria = cb.createQuery(Participation.class);
        Root<Participation> participation = criteria.from(Participation.class);
        criteria.select(participation).orderBy(cb.asc((participation.get("player").get("name")))); 
        return em.createQuery(criteria).getResultList();
    }

    /**
     * TODO: Noch die Where Clause hinzufügen!!!
     * @param calWeek
     * @return 
     */
     public List<Participation> getAllByNameAndForCalendarWeek(int calWeek) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Participation> criteria = cb.createQuery(Participation.class);
        Root<Participation> participation = criteria.from(Participation.class);
        criteria.select(participation).orderBy(cb.asc((participation.get("player").get("name")))); 
        //criteria.where(cb.equal(participation.get(Training_.currentDate.get(Calendar.WEEK_OF_YEAR), calWeek)));
        
        return em.createQuery(criteria).getResultList();
    }   
     
     /**
     * TODO: Noch die Where Clause hinzufügen!!!
     */
     public List<Participation> getAllByNameAndForSpecificDate(Date curDate) {
        return em.createQuery("SELECT p FROM Participation p WHERE p.trainingItem.currentDate = :curDate")
                  .setParameter("curDate", curDate, TemporalType.DATE).getResultList();
    }

     /**
      * Nur ein Test!!!
      * @return 
      */
     public List<Participation> testQuery() {
         Query q = em.createQuery ("SELECT x FROM Magazine x");
         return q.getResultList();
     }
}
