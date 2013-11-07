 /* Das eigentliche Training 
 * Wer nimmt alles teil, wann findet es statt ?
 */
package de.thorsten.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

    public Training(Date date, List<Member> players, TrainingDay trainingDay) {
        this.currentDate = date;
        this.trainingDay = trainingDay;
    }

    @Id
    private Date currentDate;

    @OneToOne
    private TrainingDay trainingDay;

    /**
     * @return the calendarWeek
     */
    /*
    public int getCalendarWeek() {
        return currentDate.get(Calendar.WEEK_OF_YEAR);
    }*/

    public String getTrainingDateAsString() {
        String dateString = null;
        if (currentDate != null) {
            DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMANY);
            dateString = sdf.format(currentDate.getTime());
        }
        return dateString;
    }
   

    /**
     * @return the trainingDay (Mo. - Fr.)
     */
    public TrainingDay getTrainingDay() {
        return trainingDay;
    }

    /**
     * @param trainingDay the trainingDay to set (Mo. - Fr.)
     */
    public void setTrainingDay(TrainingDay trainingDay) {
        this.trainingDay = trainingDay;
    }

    /**
     * @return the currentDate of the training
     */
    public Date getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the currentDate of the training to set
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

}
