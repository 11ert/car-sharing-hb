package de.thorsten.data;

import de.thorsten.model.MailingList;
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
public class MailingListRepository {

    @Inject
    private EntityManager em;

    public MailingList findById(Long id) {
        return em.find(MailingList.class, id);
    }


    public List<MailingList> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MailingList> criteria = cb.createQuery(MailingList.class);
        Root<MailingList> mailingList = criteria.from(MailingList.class);
        criteria.select(mailingList);
        criteria.orderBy(cb.asc(mailingList.get("description")));
        return em.createQuery(criteria).getResultList();
    }

    public List<MailingList> findAllAvailableEMailAdresses() {
        Query q = em.createQuery("SELECT m FROM MailingList m WHERE m.description = 'ALL'");
        return q.getResultList();
    }
   

}
