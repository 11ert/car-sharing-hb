package de.thorsten.service;

import de.thorsten.model.News;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
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

    @Inject
    private Event<News> newsEventSrc;

    public News update(News news) throws Exception {
        log.info("Updating " + news);
        news.setCreationDate((new Date()));
        News newNews = em.merge(news);
        newsEventSrc.fire(newNews);
        return newNews;
    }
}
