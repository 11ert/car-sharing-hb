/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.model;

import java.util.Collection;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author qtk100
 */
@Entity
@Table(name="mailinglist", 
       uniqueConstraints=@UniqueConstraint(columnNames={"description"}))
public class MailingList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
 
    private String description;

    @ElementCollection(fetch=FetchType.EAGER)
    private Collection<String> eMailAdresses;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the eMailAdresses
     */
    public Collection<String> geteMailAdresses() {
        return eMailAdresses;
    }

    /**
     * @param eMailAdresses the eMailAdresses to set
     */
    public void seteMailAdresses(Collection<String> eMailAdresses) {
        this.eMailAdresses = eMailAdresses;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

}
