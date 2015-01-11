package de.thorsten.data;

import de.thorsten.model.Game;
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
public class GameListProducer {

    @Inject
    private Logger log;

    @Inject
    private GameRepository gameRepository;

    private List<Game> games;

    private SortedSet<Date> gameDates;
    
    @Produces
    @Named
    public List<Game> getGames() {
        return games;
    }
    
    @Produces
    @Named
    public SortedSet<Date> getGameDates() {
        return gameDates;
    }
    
    @PostConstruct
    public void retrieveAllGames() {
        games = gameRepository.findAll();

        gameDates = new TreeSet<Date>();
        for (Game t : games) {
            gameDates.add(t.getEventDate());
        }
    }
}
