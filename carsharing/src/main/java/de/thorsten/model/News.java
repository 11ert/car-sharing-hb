/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.thorsten.model;

import de.thorsten.util.DateUtil;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author thorsten.elfert@gmail.com
 */
@Entity
public class News {
    
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    
    private String text;
    
    private String author;
    
    private Date creationDate;
    
    private boolean mail;
    
    @OneToOne
    private MailingList mailingList;

    private boolean activ = true;
    
    
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

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public String getCreationDateAsFormattedString() {
        return DateUtil.getSelectedDateAsFormattedString(creationDate);
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the activ
     */
    public boolean isActiv() {
        return activ;
    }

    /**
     * @param activ the activ to set
     */
    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    /**
     * @return the mailingList
     */
    public MailingList getMailingList() {
        return mailingList;
    }

    /**
     * @param mailingList the mailingList to set
     */
    public void setMailingList(MailingList mailingList) {
        this.mailingList = mailingList;
    }

    /**
     * @return the mail
     */
    @Override
    public String toString() {
        return "News{" + "id=" + id + ", text=" + text + ", author=" + author + ", creationDate=" + creationDate + ", mail=" + mail + ", mailingList=" + mailingList + ", activ=" + activ + '}';
    }

    public boolean isMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(boolean mail) {
        this.mail = mail;
    }
    
}
