package de.thorsten.data;

import de.thorsten.model.MailConfig;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@ApplicationScoped
public class MailConfigRepository {

    @Inject
    private EntityManager em;

    public MailConfig findById(Long id) {
        return em.find(MailConfig.class, id);
    }

    /**
     * @return List of MailConfig
     */
    public List<MailConfig> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MailConfig> criteria = cb.createQuery(MailConfig.class);
        return em.createQuery(criteria).getResultList();
    }

    public MailConfig findMailConfigForNews() throws MailConfigException {
        Query q = em.createQuery("SELECT t FROM MailConfig t WHERE t.type = 'NEWS'");
        if (q.getResultList().size() != 1) {
            throw new MailConfigException("No unique MailConfig for News found");
        }
        return (MailConfig)q.getResultList().get(0);
    }

    
   
}
