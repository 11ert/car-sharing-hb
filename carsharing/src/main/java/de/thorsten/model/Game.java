/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Thorsten
 */
@SuppressWarnings("serial")
@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = "eventDate"))
//@DiscriminatorValue("G")
public class Game extends SportsEvent implements Serializable {
    
    private String opponent;
    
    private String mapURL;
    
 
    /**
     * @return the opponent
     */
    public String getOpponent() {
        return opponent;
    }

    /**
     * @param opponent the opponent to set
     */
    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    /**
     * @return the mapURL
     */
    public String getMapURL() {
        return mapURL;
    }

    /**
     * @param mapURL the mapURL to set
     */
    public void setMapURL(String mapURL) {
        this.mapURL = mapURL;
    }
}
