package de.thorsten.data;

import de.thorsten.model.ParticipationGroup;
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
public class ParticipationGroupRepository {

    @Inject
    private EntityManager em;

    public ParticipationGroup findById(Long id) {
        return em.find(ParticipationGroup.class, id);
    }


    public List<ParticipationGroup> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ParticipationGroup> criteria = cb.createQuery(ParticipationGroup.class);
        Root<ParticipationGroup> mailingList = criteria.from(ParticipationGroup.class);
        criteria.select(mailingList);
        criteria.orderBy(cb.asc(mailingList.get("description")));
        return em.createQuery(criteria).getResultList();
    }

}
