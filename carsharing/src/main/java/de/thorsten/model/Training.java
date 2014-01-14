 /* Das eigentliche Training 
 * Wer nimmt alles teil, wann findet es statt ?
 */
package de.thorsten.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = "eventdate"))
//@DiscriminatorValue("T")
public class Training extends SportsEvent implements Serializable {


    public Training() {
    }

    public Training(Date date, List<Member> players, TrainingDay trainingDay) {
        this.eventDate = date;
        this.trainingDay = trainingDay;
    }

    @OneToOne
    private TrainingDay trainingDay;

    @Override
    public String toString() {
        return super.toString() + "Training{" + "trainingDay=" + trainingDay + '}';
    }

    /**
     * @return the calendarWeek
     */
    /*
    public int getCalendarWeek() {
        return eventDate.get(Calendar.WEEK_OF_YEAR);
    }*/

    public String getTrainingDateAsString() {
        String dateString = null;
        if (eventDate != null) {
            DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMANY);
            dateString = sdf.format(eventDate.getTime());
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

    public void initializeTrainingWithTrainingDayTemplateData() {
        this.location = trainingDay.getLocationTemplate();
        this.pickUpLocationSource = trainingDay.getPickUpLocationSourceTemplate();
        this.pickUpLocationTarget = trainingDay.getPickUpLocationTargetTemplate();
        this.pickUpTimeSource = trainingDay.getPickUpTimeSourceTemplate();
        this.pickUpTimeTarget = trainingDay.getPickUpTimeTargetTemplate();
        this.timeFrom = trainingDay.getTimeFromTemplate();
        this.timeTo = trainingDay.getTimeToTemplate();
        
    }
    
}
