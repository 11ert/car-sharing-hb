package de.thorsten.data;

import de.thorsten.model.News;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;


@RequestScoped
public class NewsListProducer {

    @Inject
    private NewsRepository teamRepository;

    @Inject 
    private Logger log;
    
    private List<News> teams;

    @Produces
    @Named
    public List<News> getNews() {
        return teams;
    }
    
    public void onNewsListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final News news) {
        log.fine("*** onNewsListChanged() called!");
        retrieveAllNews();
    }

    @PostConstruct
    public void retrieveAllNews() {
        log.fine("*** retrieveAllNews called!");
        teams = teamRepository.findAll();
    }
}
