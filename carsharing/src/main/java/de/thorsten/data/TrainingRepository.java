package de.thorsten.data;

import de.thorsten.model.Team;
import de.thorsten.model.Training;
import de.thorsten.model.SportsEventDetails;
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

    public List<Training> findAllOfTeam(Team team) {
        Query q = em.createQuery("SELECT t FROM Training t WHERE :team MEMBER OF t.teams");
        q.setParameter("team", team);
        return q.getResultList();
    }
    
    // todo ungetestet und nicht im Einsatz
    public List<Training> findAllOfTrainingDay(SportsEventDetails sportsEventDetails) {
        Query q = em.createQuery("SELECT t FROM Training t WHERE t.sportsEventDetails = :sportsEventDetails");
        q.setParameter("sportsEventDetails", sportsEventDetails);
        return q.getResultList();
    }
    
    // todo ungetestet und nicht im Einsatz
    public List<Training> findAllOfTrainingDayBetweenTimeRange(SportsEventDetails sportsEventDetails, Date from, Date to) {
        String query = "SELECT t FROM Training t WHERE t.sportsEventDetails = :sportsEventDetails"
                + " AND (t.eventDate BETWEEN  :from AND :to)";
        Query q = em.createQuery(query);
        q.setParameter("sportsEventDetails", sportsEventDetails);
        q.setParameter("from", from);
        q.setParameter("to", to);
        return q.getResultList();
    }
    
}
