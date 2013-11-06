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

    public void update(Participation participation) throws Exception {
        log.info("Updating " + participation);

        participation.setLastChanged(new Date());
        em.merge(participation);
        participationEventSrc.fire(participation);
 //       em.flush(); // TE
    }
    
    
}
