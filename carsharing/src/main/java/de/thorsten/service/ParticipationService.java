package de.thorsten.service;

import de.thorsten.model.Participation;
import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class ParticipationService implements Serializable {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Participation> participationEventSrc;

    // todo - nicht player und participation gleichzeitig in dieser methode
    // speichern!
    public void update(Participation participation) throws Exception {
        participation.setLastChanged(new Date());
        log.info("Updating " + participation);
        
        //em.merge(participation.getTrainingItem());
        em.merge(participation);
        em.merge(participation.getPlayer());
        participationEventSrc.fire(participation);
 //       em.flush(); // TE
    }
    
    
}
