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


@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "weekday"))
public class TrainingDay extends SportsEvent implements Serializable {


    @NotNull
    private int weekday;

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

}
