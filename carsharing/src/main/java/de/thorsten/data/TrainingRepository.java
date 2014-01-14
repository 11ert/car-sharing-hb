package de.thorsten.data;

import de.thorsten.model.Training;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;

@ApplicationScoped
public class TrainingRepository {

    @Inject
    private EntityManager em;

    public Training findById(Long id) {
        return em.find(Training.class, id);
    }

    /**
     * ToDo: Sortierung funktioniert nicht !!!
     *
     * @return
     */
    public List<Training> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Training> criteria = cb.createQuery(Training.class);
        Root<Training> training = criteria.from(Training.class);
        criteria.select(training);
        criteria.orderBy(cb.asc(training.get("eventDate")));
        return em.createQuery(criteria).getResultList();
    }

    public List<Training> findByDate(Date eventDate) {
        Query q = em.createQuery("SELECT t FROM Training t WHERE t.eventDate = :eventDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        return q.getResultList();
    }
    
    public List<Training> findAllGeDate(Date eventDate) {
        Query q = em.createQuery("SELECT t FROM Training t WHERE t.eventDate >= :eventDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        return q.getResultList();
    }

}
