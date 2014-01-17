package de.thorsten.data;

import de.thorsten.model.SportsEvent;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SportsEventListProducer {

    @Inject
    private Logger log;

    @Inject
    private SportsEventRepository sportsEventRepository;

    private List<SportsEvent> sportsEvents;

    private SortedSet<Date> sportsEventDates;
    
    
    // @Named provides access the return value via the EL variable name "sportsEvents" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named
    public List<SportsEvent> getSportsEvents() {
        return sportsEvents;
    }

    
    
    @PostConstruct
    public void retrieveAllSportsEvents() {
        sportsEvents = sportsEventRepository.findAll();
        sportsEventDates = new TreeSet<Date>();
        for (SportsEvent t : sportsEvents) {
            sportsEventDates.add(t.getEventDate());
        }
    }
}
