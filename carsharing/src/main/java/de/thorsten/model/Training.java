/* Das eigentliche Training 
 * Wer nimmt alles teil, wann findet es statt ?
 */
package de.thorsten.model;

import de.thorsten.util.DateUtil;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = "eventdate"))
//@DiscriminatorValue("T")
public class Training extends SportsEvent implements Serializable {

    public Training() {
    }

   //    public Training(Date date, List<Member> players, TrainingDay trainingDay) {
    //        this.eventDate = date;
    //        this.trainingDay = trainingDay;
    //    }
    //@OneToOne(cascade = CascadeType.ALL)
    private transient TrainingDay trainingDay;

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
    /**
     * @return the trainingDay (Mo. - Fr.)
     */
//    public TrainingDay getTrainingDay() {
//        return trainingDay;
//    }

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

    public void newTrainingDay() {
        this.trainingDay = new TrainingDay();
    }

    public String getWeekdayOfTrainingDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.eventDate);
        return DateUtil.getWeekdayAsString(cal.get(Calendar.DAY_OF_WEEK));
    }
}
