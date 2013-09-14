 /* Das eigentliche Training 
 * Wer nimmt alles teil, wann findet es statt ?
 */
package de.thorsten.model;

import javax.persistence.OneToOne;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;



@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "currentdate"))
public class Training implements Serializable {

    public Training() {
    }

    public Training(String calendarWeek, Date date, List<Member> players, TrainingDay trainingDay) {
        this.calendarWeek = calendarWeek;
        this.currentDate = date;
        this.trainingDay = trainingDay;
    }

    private String calendarWeek;

    @Id
    private Date currentDate;
    
    @OneToOne
    private TrainingDay trainingDay;

    /**
     * @return the calendarWeek
     */
    public String getCalendarWeek() {
        return calendarWeek;
    }

    /**
     * @param calendarWeek the calendarWeek to set
     */
    public void setCalendarWeek(String calendarWeek) {
        this.calendarWeek = calendarWeek;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return getCurrentDate();
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.setCurrentDate(date);
    }

    /**
     * @return the trainingDay
     */
    public TrainingDay getTrainingDay() {
        return trainingDay;
    }

    /**
     * @param trainingDay the trainingDay to set
     */
    public void setTrainingDay(TrainingDay trainingDay) {
        this.trainingDay = trainingDay;
    }

    /**
     * @return the currentDate
     */
    public Date getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the currentDate to set
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }


}
