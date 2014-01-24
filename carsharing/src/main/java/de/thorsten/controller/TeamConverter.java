/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.controller;
        
import de.thorsten.data.TeamRepository;
import de.thorsten.model.Team;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author QTK100
 */
@FacesConverter("TeamConverter")
public class TeamConverter implements Converter {
             
    @Inject
    private TeamRepository teamRepository;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String newValue) {
        Team team = new Team();
        try {
            team = teamRepository.findById(Long.parseLong(newValue));
        } catch (Throwable ex) {
//            ResourceBundle bundle = ResourceBundle.getBundle("messages");
//            FacesMessage msg = new FacesMessage(bundle.getString("faculty_convertion_message"));
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ConverterException(msg);
            
        }
        return team;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        String val = null;
        try {
            Team team = (Team) value;
            val = Long.toString(team.getId());
        } catch (Throwable ex) {
//            ResourceBundle bundle = ResourceBundle.getBundle("messages");
//            FacesMessage msg = new FacesMessage(bundle.getString("faculty_convertion_message"));
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ConverterException(msg);
        }
        return val;
    }

}
