package de.thorsten.service;

import de.thorsten.model.Game;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Thorsten
 */
@Stateless
public class GameService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public Game update(Game game) throws Exception {
        log.info("Updating " + game);
        
        return em.merge(game);
    }
}
