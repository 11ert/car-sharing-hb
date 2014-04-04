/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.controller;

import de.thorsten.data.ParticipationGroupListProducer;
import de.thorsten.data.ParticipationGroupRepository;
import de.thorsten.model.ParticipationGroup;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.deltaspike.core.api.provider.BeanProvider;

/**
 * Wird derzeit doch nicht mehr verwendet - dient noch als Beispiel
 *
 * @author Thorsten Elfert
 */
@FacesConverter("ParticipationGroupConverter")
public class ParticipationGroupConverter implements Converter {

    // Injection funktioniert bei FacesConverter in JSF 2.0 noch nicht
    private ParticipationGroupRepository participationGroupRepository
            = BeanProvider.getContextualReference(ParticipationGroupRepository.class);
    
    private ParticipationGroupListProducer participationGroupListProducer
            = BeanProvider.getContextualReference(ParticipationGroupListProducer.class);
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String newValue) {
        ParticipationGroup participationGroup = null;
        try {
            //participationGroup = participationGroupRepository.findById(Long.parseLong(newValue));
            participationGroup = participationGroupListProducer.getParticipationGroupById(Long.parseLong(newValue));
        } catch (NumberFormatException ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            FacesMessage msg = new FacesMessage(bundle.getString("message.participationGroup_convertion_message_error"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            ex.printStackTrace();
            throw new ConverterException(msg);
        }
        return participationGroup;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        String val = null;
        try {
            ParticipationGroup participationGroup = (ParticipationGroup) value;
            val = Long.toString(participationGroup.getId());
        } catch (Throwable ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            FacesMessage msg = new FacesMessage(bundle.getString("message.participationGroup_convertion_message_error"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
        return val;
    }

}
