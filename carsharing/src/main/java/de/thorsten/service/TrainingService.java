package de.thorsten.service;

import de.thorsten.model.Training;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Thorsten
 */
@Stateless
public class TrainingService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public Training update(Training training) throws Exception {
        log.info("Updating " + training);
        return em.merge(training);
    }
    
    public void delete(Training training) throws Exception {
        log.info("Deleting " + training);
        em.remove(training);
    }
        
}
