package de.thorsten.data;

import de.thorsten.model.News;
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
public class NewsRepository {

    @Inject
    private EntityManager em;

    public News findById(Long id) {
        return em.find(News.class, id);
    }

    /**
     * @return List of News
     */
    public List<News> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<News> criteria = cb.createQuery(News.class);
        Root<News> news = criteria.from(News.class);
        return em.createQuery(criteria).getResultList();
    }

    public List<News> findAllActivNews() {
        Query q = em.createQuery("SELECT t FROM News t WHERE t.activ = TRUE order by t.creationDate");
        return q.getResultList();
    }

    
    public List<News> findByDate(Date eventDate) {
        Query q = em.createQuery("SELECT t FROM News t WHERE t.creationDate = :creationDate order by t.creationDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        return q.getResultList();
    }

}
