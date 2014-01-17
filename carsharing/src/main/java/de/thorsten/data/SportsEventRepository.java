package de.thorsten.data;

import de.thorsten.model.SportsEvent;
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
public class SportsEventRepository {

    @Inject
    private EntityManager em;

    public SportsEvent findById(Long id) {
        return em.find(SportsEvent.class, id);
    }

    /**
     * ToDo: Sortierung funktioniert nicht !!!
     *
     * @return
     */
    public List<SportsEvent> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SportsEvent> criteria = cb.createQuery(SportsEvent.class);
        Root<SportsEvent> sportsEvent = criteria.from(SportsEvent.class);
        criteria.select(sportsEvent);
        criteria.orderBy(cb.asc(sportsEvent.get("eventDate")));
        return em.createQuery(criteria).getResultList();
    }

    public List<SportsEvent> findByDate(Date eventDate) {
        Query q = em.createQuery("SELECT t FROM SportsEvent t WHERE t.eventDate = :eventDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        return q.getResultList();
    }
    
    public List<SportsEvent> findAllGeDate(Date eventDate) {
        Query q = em.createQuery("SELECT t FROM SportsEvent t WHERE t.eventDate >= :eventDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        return q.getResultList();
    }

}
