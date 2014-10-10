/*
 * Die verschiedenen Trainingstage/-zeiten in der Woche
 */
package de.thorsten.model;

import de.thorsten.util.DateUtil;
import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(
        uniqueConstraints
        = @UniqueConstraint(columnNames = {"weekday", "location", "timeFrom", "timeTo"})
)
public class SportsEventDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "timeFrom")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]")
    private String timeFrom;

    @NotNull
    @NotEmpty
    @Column(name = "timeTo")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]")
    private String timeTo;

    @NotNull
    @NotEmpty
    @Column(name = "pickUpTimeSource")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]")
    private String pickUpTimeSource;

    @NotNull
    @NotEmpty
    @Column(name = "pickUpTimeTarget")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]")
    private String pickUpTimeTarget;

    @NotNull
    @Column(name = "location")
    private String location;

    @Column(name = "pickUpLocationSource")
    private String pickUpLocationSource;

    @Column(name = "pickUpLocationTarget")
    private String pickUpLocationTarget;

    @NotNull
    private int weekday;

    private String comment;

    /**
     * @return the weekday
     */
    public int getWeekday() {
        return weekday;
    }

    public String getWeekdayAsString() {
        return DateUtil.getWeekdayAsString(weekday);
    }

    /**
     * @param weekday the weekday to set
     */
    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the timeFrom
     */
    public String getTimeFrom() {
        return timeFrom;
    }

    /**
     * @param timeFromTemplate the timeFrom to set
     */
    public void setTimeFrom(String timeFromTemplate) {
        this.timeFrom = timeFromTemplate;
    }

    /**
     * @return the timeTo
     */
    public String getTimeTo() {
        return timeTo;
    }

    /**
     * @param timeToTemplate the timeTo to set
     */
    public void setTimeTo(String timeToTemplate) {
        this.timeTo = timeToTemplate;
    }

    /**
     * @return the pickUpTimeSource
     */
    public String getPickUpTimeSource() {
        return pickUpTimeSource;
    }

    /**
     * @param pickUpTimeSourceTemplate the pickUpTimeSource to set
     */
    public void setPickUpTimeSource(String pickUpTimeSourceTemplate) {
        this.pickUpTimeSource = pickUpTimeSourceTemplate;
    }

    /**
     * @return the pickUpTimeTarget
     */
    public String getPickUpTimeTarget() {
        return pickUpTimeTarget;
    }

    /**
     * @param pickUpTimeTargetTemplate the pickUpTimeTarget to set
     */
    public void setPickUpTimeTarget(String pickUpTimeTargetTemplate) {
        this.pickUpTimeTarget = pickUpTimeTargetTemplate;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param locationTemplate the location to set
     */
    public void setLocation(String locationTemplate) {
        this.location = locationTemplate;
    }

    /**
     * @return the pickUpLocationSource
     */
    public String getPickUpLocationSource() {
        return pickUpLocationSource;
    }

    /**
     * @param pickUpLocationSourceTemplate the pickUpLocationSource to
 set
     */
    public void setPickUpLocationSource(String pickUpLocationSourceTemplate) {
        this.pickUpLocationSource = pickUpLocationSourceTemplate;
    }

    /**
     * @return the pickUpLocationTarget
     */
    public String getPickUpLocationTarget() {
        return pickUpLocationTarget;
    }

    /**
     * @param pickUpLocationTargetTemplate the pickUpLocationTarget to
 set
     */
    public void setPickUpLocationTarget(String pickUpLocationTargetTemplate) {
        this.pickUpLocationTarget = pickUpLocationTargetTemplate;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "TrainingDay{" + "id=" + id + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo + ", pickUpTimeSource=" + pickUpTimeSource + ", pickUpTimeTarget=" + pickUpTimeTarget + ", location=" + location + ", pickUpLocationSource=" + pickUpLocationSource + ", pickUpLocationTarget=" + pickUpLocationTarget + ", weekday=" + weekday + ", comment=" + comment + '}';
    }

 

}
