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

    public void update(Training training) throws Exception {
        log.info("Updating " + training);

        em.merge(training);
    }
        
}
