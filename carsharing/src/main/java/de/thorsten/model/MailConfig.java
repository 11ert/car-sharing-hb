/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.thorsten.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Thorsten
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "type"))
public class MailConfig {
    
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )    
    private Long id;
    
    private String sender;
    private String recipient;
    private String signature;
    private String preConfiguredText;
    private String postConfiguredText;
    private String subject;
    private String type;

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * @param recipient the recipient to set
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return the preConfiguredText
     */
    public String getPreConfiguredText() {
        return preConfiguredText;
    }

    /**
     * @param preConfiguredText the preConfiguredText to set
     */
    public void setPreConfiguredText(String preConfiguredText) {
        this.preConfiguredText = preConfiguredText;
    }

    /**
     * @return the postConfiguredText
     */
    public String getPostConfiguredText() {
        return postConfiguredText;
    }

    /**
     * @param postConfiguredText the postConfiguredText to set
     */
    public void setPostConfiguredText(String postConfiguredText) {
        this.postConfiguredText = postConfiguredText;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
