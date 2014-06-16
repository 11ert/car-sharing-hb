package de.thorsten.data;

import de.thorsten.model.SportsEvent;
import de.thorsten.model.Team;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.TemporalType;

@ApplicationScoped
public class SportsEventRepository {

    @Inject
    private EntityManager em;
    
    @Inject
    private Logger log;

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

    public List<SportsEvent> findByDateAndTeam(Date eventDate, Team curTeam) {
        Query q = em.createQuery("SELECT t FROM SportsEvent t WHERE t.eventDate = :eventDate AND :curTeam in elements(t.teams)");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        q.setParameter("curTeam", curTeam);
        return q.getResultList();
    }
    
    public List<SportsEvent> findAllGeDateAndForSpecificTeam(Date eventDate, Team curTeam) {
        log.fine("SportsEventRepository.findAllGeDateAndForSpecificTeam");
        log.fine("EventDate = " + eventDate);
        log.fine("Team      = " + curTeam);
        Query q = em.createQuery("SELECT t FROM SportsEvent t WHERE t.eventDate >= :eventDate AND :curTeam in elements(t.teams) order by t.eventDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        q.setParameter("curTeam", curTeam);
        return q.getResultList();
    }

        public List<SportsEvent> findAllGeDate(Date eventDate) {
        log.fine("SportsEventRepository.findAllGeDate");
        log.fine("EventDate = " + eventDate);
        Query q = em.createQuery("SELECT t FROM SportsEvent t WHERE t.eventDate >= :eventDate order by t.eventDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        return q.getResultList();
    }

    
    
    
}
