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
        criteria.select(participation).orderBy(cb.asc((participation.get("player").get("name")))); // ob das funktioniert?
        return em.createQuery(criteria).getResultList();
    }
}
