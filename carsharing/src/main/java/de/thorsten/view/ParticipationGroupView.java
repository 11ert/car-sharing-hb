/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.view;

import de.thorsten.controller.Current;
import de.thorsten.data.ParticipationGroupListProducer;
import de.thorsten.model.ParticipationGroup;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.os890.cdi.ext.scope.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author Thorsten
 */
@ViewAccessScoped
@Named
public class ParticipationGroupView implements Serializable {

    private ParticipationGroup selectedParticipationGroup;

    @Inject
    private List<ParticipationGroup> participationGroups;

    @Inject
    private Logger log;

    @Inject
    private ParticipationGroupListProducer participationGroupListProducer;

    @PostConstruct
    public void init() {
        // initial einfach erste laden

        if (participationGroups != null && participationGroups.size() != 0) {
            selectedParticipationGroup = participationGroups.get(0);
        }
    }


    public void participationGroupChanged(ValueChangeEvent event) {
        log.fine("participationGroupChanged ");
        if (event.getNewValue() != null) {
            Long tmpId = Long.valueOf((String) event.getNewValue());
            selectedParticipationGroup = participationGroupListProducer.getParticipationGroupById(tmpId);
            log.fine("...to new participationGroup = " + selectedParticipationGroup.toString());
        }
    }

    /**
     * @return the selectedParticipationGroup
     */
    @Named
    @Produces @Current @RequestScoped
    public ParticipationGroup getSelectedParticipationGroup() {
        return selectedParticipationGroup;
    }

    /**
     * @param selectedParticipationGroup the selectedParticipationGroup to set
     */
    public void setSelectedParticipationGroup(ParticipationGroup selectedParticipationGroup) {
        this.selectedParticipationGroup = selectedParticipationGroup;
    }

}
