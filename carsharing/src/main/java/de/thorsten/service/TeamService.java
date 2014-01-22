package de.thorsten.service;

import de.thorsten.model.Team;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Thorsten
 */
@Stateless
public class TeamService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public Team update(Team team) throws Exception {
        log.info("Updating " + team);
        
        return em.merge(team);
        
        
    }
        
}
