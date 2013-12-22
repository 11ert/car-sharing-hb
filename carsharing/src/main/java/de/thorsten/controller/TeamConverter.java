/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.controller;

import de.thorsten.data.ParticipationRepository;
import de.thorsten.model.Team;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Thorsten Elfert
 */
@ApplicationScoped
@FacesConverter("TeamConverter")
public class TeamConverter implements Converter {

    private List<Team> teams;

    @Inject
    private ParticipationRepository participationRepository;

    @Inject
    private Logger log;

    @PostConstruct
    public void loadTeams() {
        teams = participationRepository.getAllTeams();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        log.info("TeamConverter.getAsObject() called");
        Team team = null;
        for (Team t : teams) {
            if (t.getName().equalsIgnoreCase(value)) {
                team = t;
            }
        }
        if (team == null) {
            FacesMessage msg = new FacesMessage("Conversion error",
                            "Team for " + value + " not found");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }

        return team;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        log.info("TeamConverter.getAsString() called");
        if (value != null)
            return value.toString();
        return null;
    }
}
