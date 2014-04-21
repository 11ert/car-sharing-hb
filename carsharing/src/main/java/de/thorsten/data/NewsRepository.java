package de.thorsten.data;

import de.thorsten.model.News;
import de.thorsten.model.News_;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        criteria.orderBy(cb.desc(news.get(News_.creationDate)));
        return em.createQuery(criteria).getResultList();
    }

    public List<News> findAllActivNews() {
        Query q = em.createQuery("SELECT t FROM News t WHERE t.activ = TRUE order by t.creationDate DESC");
        return q.getResultList();
    }

    
    public List<News> findByDate(Date eventDate) {
        Query q = em.createQuery("SELECT t FROM News t WHERE t.creationDate = :creationDate order by t.creationDate");
        q.setParameter("eventDate", eventDate, TemporalType.DATE);
        return q.getResultList();
    }

}
