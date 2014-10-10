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


    @Override
    public String toString() {
        return super.toString() + "Training{" + "trainingDay=" + sportsEventDetail + '}';
    }


//    public void initializeTrainingWithTrainingDayTemplateData() {
//        this.location = sportsEventDetail.getLocationTemplate();
//        this.pickUpLocationSource = sportsEventDetail.getPickUpLocationSourceTemplate();
//        this.pickUpLocationTarget = sportsEventDetail.getPickUpLocationTargetTemplate();
//        this.pickUpTimeSource = sportsEventDetail.getPickUpTimeSourceTemplate();
//        this.pickUpTimeTarget = sportsEventDetail.getPickUpTimeTargetTemplate();
//        this.timeFrom = sportsEventDetail.getTimeFromTemplate();
//        this.timeTo = sportsEventDetail.getTimeToTemplate();
//
//    }
//
//    public void newTrainingDay() {
//        this.sportsEventDetail = new SportsEventDetails();
//    }

    public String getWeekdayOfTrainingDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.eventDate);
        return DateUtil.getWeekdayAsString(cal.get(Calendar.DAY_OF_WEEK));
    }

}

