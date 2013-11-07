/*
 * Die verschiedenen Trainingstage in der Woche
 * Eindeutig pro Wochentag
 */
package de.thorsten.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "weekday"))
public class TrainingDay implements Serializable {


    @Id
    @NotNull
    private int weekday;

    @NotNull
    @NotEmpty
    private String timeFrom;

    @NotNull
    @NotEmpty
    private String timeTo;

    @NotNull
    @NotEmpty
    private String pickUpTimeSource;
    
    @NotNull
    @NotEmpty
    private String pickUpTimeTarget;
    
    @NotNull
    private String location;

    /**
     * @return the weekday
     */
    public int getWeekday() {
        return weekday;
    }

    /**
     * @param weekday the weekday to set
     */
    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    /**
     * @return the timeFrom
     */
    public String getTimeFrom() {
        return timeFrom;
    }

    /**
     * @param timeFrom the timeFrom to set
     */
    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    /**
     * @return the timeTo
     */
    public String getTimeTo() {
        return timeTo;
    }

    /**
     * @param timeTo the timeTo to set
     */
    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    /**
     * @return the pickUpTimeSource
     */
    public String getPickUpTimeSource() {
        return pickUpTimeSource;
    }

    /**
     * @param pickUpTimeSource the pickUpTimeSource to set
     */
    public void setPickUpTimeSource(String pickUpTimeSource) {
        this.pickUpTimeSource = pickUpTimeSource;
    }

    /**
     * @return the pickUpTimeTarget
     */
    public String getPickUpTimeTarget() {
        return pickUpTimeTarget;
    }

    /**
     * @param pickUpTimeTarget the pickUpTimeTarget to set
     */
    public void setPickUpTimeTarget(String pickUpTimeTarget) {
        this.pickUpTimeTarget = pickUpTimeTarget;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

}
