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
import de.thorsten.model.Training_;
import java.util.Calendar;
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
     public List<Participation> getAllByNameAndForSpecificDate(Calendar curDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Participation> criteria = cb.createQuery(Participation.class);
        Root<Participation> participation = criteria.from(Participation.class);
        criteria.select(participation).orderBy(cb.asc((participation.get("player").get("name")))); 
   //     criteria.where(cb.equal(participation.get( Training_.currentDate ), curDate) );
        
        return em.createQuery(criteria).getResultList();
    }

}
