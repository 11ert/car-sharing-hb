package de.thorsten.data;

import de.thorsten.model.Game;
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
public class GameRepository {

    @Inject
    private EntityManager em;

    public Game findById(Long id) {
        return em.find(Game.class, id);
    }

    /**
     * ToDo: Sortierung funktioniert nicht !!!
     *
     * @return
     */
    public List<Game> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> training = criteria.from(Game.class);
        criteria.select(training);
        criteria.orderBy(cb.asc(training.get("eventDate")));
        return em.createQuery(criteria).getResultList();
    }

    public List<Game> findByDate(Date eventDate) {
        Query q = em.createQuery("SELECT t FROM Game t WHERE t.eventDate = :eventDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        return q.getResultList();
    }
    
    public List<Game> findAllGeDate(Date eventDate) {
        Query q = em.createQuery("SELECT t FROM Game t WHERE t.eventDate >= :eventDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        return q.getResultList();
    }

}
