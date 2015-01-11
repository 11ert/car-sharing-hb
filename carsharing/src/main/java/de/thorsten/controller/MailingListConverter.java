/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.controller;

import de.thorsten.data.MailingListRepository;
import de.thorsten.model.MailingList;
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
 * @author thorsten.elfert@gmail.com
 */
@FacesConverter("MailingListConverter")
public class MailingListConverter implements Converter {

    // Injection funktioniert bei FacesConverter in JSF 2.0 noch nicht
    private MailingListRepository mailingListRepository
            = BeanProvider.getContextualReference(MailingListRepository.class);
    
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String newValue) {
        System.out.println("MailingListConverter.getAsObject()");

        MailingList mailingList = null;
        try {
            mailingList = mailingListRepository.findById(Long.parseLong(newValue));
        } catch (Throwable ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            FacesMessage msg = new FacesMessage(bundle.getString("message.team_convertion_message_error"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            ex.printStackTrace();
            throw new ConverterException(msg);
        }
        return mailingList;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        System.out.println("MailingListConverter.getAsString() " + value.toString());
        String val = null;
        try {
            MailingList mailingList = (MailingList) value;
            val = mailingList.getDescription();
            System.out.println("...mailingList.getDescription: " + val);
        } catch (Throwable ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            FacesMessage msg = new FacesMessage(bundle.getString("message.team_convertion_message_error"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
        System.out.println("return " + val);
        return val;
    }

}
