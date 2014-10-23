package de.thorsten.service;

import de.thorsten.data.ParticipationRepository;
import de.thorsten.model.Participation;
import de.thorsten.model.Training;
import java.util.Iterator;
import java.util.List;
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

    @Inject
    private ParticipationService participationService;

    @Inject
    private ParticipationRepository participationRepository;

    public Training update(Training training) throws Exception {
        log.info("Updating " + training);
        return em.merge(training);
    }

    public void delete(Training training) throws Exception {

        List<Participation> participationsOfTraining = participationRepository.getAllOfTraining(training);

        for (Participation tmpParticipation : participationsOfTraining) {
            participationService.remove(tmpParticipation);
        }

        log.info("Deleting " + training);
        em.remove(training);
    }

}
