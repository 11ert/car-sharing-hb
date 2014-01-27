package de.thorsten.service;

import de.thorsten.model.News;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Thorsten
 */
@Stateless
public class NewsService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public News update(News news) throws Exception {
        log.info("Updating " + news);
        news.setCreationDate((new Date()));
        
        return em.merge(news);
    }
}
