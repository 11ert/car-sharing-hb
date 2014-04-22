/*
 * Die verschiedenen Trainingstage in der Woche
 */
package de.thorsten.model;

import java.io.Serializable;
import javax.persistence.Column;

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
@Table(
        uniqueConstraints = 
                @UniqueConstraint(columnNames = {"weekday","location","timeFrom","timeTo"})
)
public class TrainingDay implements Serializable {

    @Id
    private Long id;

    @Column(name = "timeFrom")
    private String timeFromTemplate;

    @NotNull
    @NotEmpty
    @Column(name = "timeTo")
    private String timeToTemplate;

    @NotNull
    @NotEmpty
    @Column(name = "pickUpTimeSource")
    private String pickUpTimeSourceTemplate;

    @NotNull
    @NotEmpty
    @Column(name = "pickUpTimeTarget")
    private String pickUpTimeTargetTemplate;

    @NotNull
    @Column(name = "location")
    private String locationTemplate;

    @Column(name = "pickUpLocationSource")
    private String pickUpLocationSourceTemplate;

    @Column(name = "pickUpLocationTarget")
    private String pickUpLocationTargetTemplate;

    @NotNull
    private int weekday;
    
    private String comment;
            

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the timeFromTemplate
     */
    public String getTimeFromTemplate() {
        return timeFromTemplate;
    }

    /**
     * @param timeFromTemplate the timeFromTemplate to set
     */
    public void setTimeFromTemplate(String timeFromTemplate) {
        this.timeFromTemplate = timeFromTemplate;
    }

    /**
     * @return the timeToTemplate
     */
    public String getTimeToTemplate() {
        return timeToTemplate;
    }

    /**
     * @param timeToTemplate the timeToTemplate to set
     */
    public void setTimeToTemplate(String timeToTemplate) {
        this.timeToTemplate = timeToTemplate;
    }

    /**
     * @return the pickUpTimeSourceTemplate
     */
    public String getPickUpTimeSourceTemplate() {
        return pickUpTimeSourceTemplate;
    }

    /**
     * @param pickUpTimeSourceTemplate the pickUpTimeSourceTemplate to set
     */
    public void setPickUpTimeSourceTemplate(String pickUpTimeSourceTemplate) {
        this.pickUpTimeSourceTemplate = pickUpTimeSourceTemplate;
    }

    /**
     * @return the pickUpTimeTargetTemplate
     */
    public String getPickUpTimeTargetTemplate() {
        return pickUpTimeTargetTemplate;
    }

    /**
     * @param pickUpTimeTargetTemplate the pickUpTimeTargetTemplate to set
     */
    public void setPickUpTimeTargetTemplate(String pickUpTimeTargetTemplate) {
        this.pickUpTimeTargetTemplate = pickUpTimeTargetTemplate;
    }

    /**
     * @return the locationTemplate
     */
    public String getLocationTemplate() {
        return locationTemplate;
    }

    /**
     * @param locationTemplate the locationTemplate to set
     */
    public void setLocationTemplate(String locationTemplate) {
        this.locationTemplate = locationTemplate;
    }

    /**
     * @return the pickUpLocationSourceTemplate
     */
    public String getPickUpLocationSourceTemplate() {
        return pickUpLocationSourceTemplate;
    }

    /**
     * @param pickUpLocationSourceTemplate the pickUpLocationSourceTemplate to
     * set
     */
    public void setPickUpLocationSourceTemplate(String pickUpLocationSourceTemplate) {
        this.pickUpLocationSourceTemplate = pickUpLocationSourceTemplate;
    }

    /**
     * @return the pickUpLocationTargetTemplate
     */
    public String getPickUpLocationTargetTemplate() {
        return pickUpLocationTargetTemplate;
    }

    /**
     * @param pickUpLocationTargetTemplate the pickUpLocationTargetTemplate to
     * set
     */
    public void setPickUpLocationTargetTemplate(String pickUpLocationTargetTemplate) {
        this.pickUpLocationTargetTemplate = pickUpLocationTargetTemplate;
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
        return "TrainingDay{" + "id=" + id + ", timeFromTemplate=" + timeFromTemplate + ", timeToTemplate=" + timeToTemplate + ", pickUpTimeSourceTemplate=" + pickUpTimeSourceTemplate + ", pickUpTimeTargetTemplate=" + pickUpTimeTargetTemplate + ", locationTemplate=" + locationTemplate + ", pickUpLocationSourceTemplate=" + pickUpLocationSourceTemplate + ", pickUpLocationTargetTemplate=" + pickUpLocationTargetTemplate + ", weekday=" + weekday + ", comment=" + comment + '}';
    }
    
    

}
