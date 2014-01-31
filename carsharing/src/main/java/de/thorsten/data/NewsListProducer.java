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
    private NewsRepository newsRepository;

    @Inject 
    private Logger log;
    
    private List<News> news;

    private List<News> allNews;
    
    @Produces
    @Named
    public List<News> getNews() {
        return news;
    }
    
    @Produces
    @Named
    public List<News> getAllNews() {
        return allNews;
    }
    
    public void onNewsListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final News news) {
        log.fine("*** onNewsListChanged() called!");
        loadAll();
    }

    @PostConstruct
    public void loadAll() {
        log.fine("*** loadAll called!");
        news = newsRepository.findAllActivNews();
        allNews = newsRepository.findAll();
    }
}
