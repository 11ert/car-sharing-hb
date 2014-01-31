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
import de.thorsten.model.Team;
import java.util.Date;
import java.util.logging.Logger;
import javax.persistence.Query;
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
        return em.createQuery("SELECT p FROM Participation p WHERE p.trainingItem.eventDate = :curDate order by p.player.firstname, p.player.name")
                .setParameter("curDate", curDate, TemporalType.DATE).getResultList();
    }

    public List<Participation> getAllForDateGreaterEqualOrderedByDateAndName(Date curDate) {
        log.info("Getting all training participations for = " + curDate + "...");
        return em.createQuery("SELECT p FROM Participation p WHERE p.trainingItem.eventDate >= :curDate order by p.trainingItem.eventDate, p.player.firstname, p.player.name")
                .setParameter("curDate", curDate, TemporalType.DATE).getResultList();
    }

    public List<Participation> getAllForSpecificDateAndTeamOrderedByName(Date curDate, Team curTeam) {
        log.info("Getting all participations for = " + curDate + " team " + curTeam.toString());
//        Query q = em.createQuery("SELECT p FROM Participation p WHERE p.trainingItem.eventDate = :curDate AND :curTeam MEMBER OF p.trainingItem.teams order by p.trainingItem.eventDate, p.player.firstname, p.player.name");
        Query q = em.createQuery("SELECT p FROM Participation p WHERE p.trainingItem.eventDate = :curDate AND :curTeam in elements(p.trainingItem.teams) order by p.trainingItem.eventDate, p.player.firstname, p.player.name");

        q.setParameter("curDate", curDate, TemporalType.DATE);
        q.setParameter("curTeam", curTeam);
        return q.getResultList();
    }

}
