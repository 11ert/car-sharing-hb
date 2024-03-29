/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thorsten.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Thorsten
 */
@Entity
@Inheritance
public abstract class SportsEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

//    @NotNull
//    @NotEmpty
//    protected String timeFrom;
//
//    @NotNull
//    @NotEmpty
//    protected String timeTo;
//
//    @NotNull
//    @NotEmpty
//    protected String pickUpTimeSource;
//
//    @NotNull
//    @NotEmpty
//    protected String pickUpTimeTarget;
//
//    @NotNull
//    protected String location;
//
//    protected String pickUpLocationSource;
//
//    protected String pickUpLocationTarget;
    @NotNull
    protected Date eventDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Team> teams;
    //    public Training(Date date, List<Member> players, SportsEventDetails sportsEventDetail) {
    //        this.eventDate = date;
    //        this.sportsEventDetail = sportsEventDetail;
    //    }
    // (cascade = CascadeType.ALL)
    @ManyToOne(cascade = CascadeType.MERGE)
    protected SportsEventDetails sportsEventDetail;

//    /**
//     * @return the timeFrom
//     */
//    public String getTimeFrom() {
//        return timeFrom;
//    }
//
//    /**
//     * @param timeFrom the timeFrom to set
//     */
//    public void setTimeFrom(String timeFrom) {
//        this.timeFrom = timeFrom;
//    }
//
//    /**
//     * @return the timeTo
//     */
//    public String getTimeTo() {
//        return timeTo;
//    }
//
//    /**
//     * @param timeTo the timeTo to set
//     */
//    public void setTimeTo(String timeTo) {
//        this.timeTo = timeTo;
//    }
//
//    /**
//     * @return the pickUpTimeSource
//     */
//    public String getPickUpTimeSource() {
//        return pickUpTimeSource;
//    }
//
//    /**
//     * @param pickUpTimeSource the pickUpTimeSource to set
//     */
//    public void setPickUpTimeSource(String pickUpTimeSource) {
//        this.pickUpTimeSource = pickUpTimeSource;
//    }
//
//    /**
//     * @return the pickUpTimeTarget
//     */
//    public String getPickUpTimeTarget() {
//        return pickUpTimeTarget;
//    }
//
//    /**
//     * @param pickUpTimeTarget the pickUpTimeTarget to set
//     */
//    public void setPickUpTimeTarget(String pickUpTimeTarget) {
//        this.pickUpTimeTarget = pickUpTimeTarget;
//    }
//
//    /**
//     * @return the location
//     */
//    public String getLocation() {
//        return location;
//    }
//
//    /**
//     * @param location the location to set
//     */
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    /**
//     * @return the pickUpLocationSource
//     */
//    public String getPickUpLocationSource() {
//        return pickUpLocationSource;
//    }
//
//    /**
//     * @param pickUpLocationSource the pickUpLocationSource to set
//     */
//    public void setPickUpLocationSource(String pickUpLocationSource) {
//        this.pickUpLocationSource = pickUpLocationSource;
//    }
//
//    /**
//     * @return the pickUpLocationTarget
//     */
//    public String getPickUpLocationTarget() {
//        return pickUpLocationTarget;
//    }
//
//    /**
//     * @param pickUpLocationTarget the pickUpLocationTarget to set
//     */
//    public void setPickUpLocationTarget(String pickUpLocationTarget) {
//        this.pickUpLocationTarget = pickUpLocationTarget;
//    }
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the eventDate of the training
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * @param eventDate the eventDate of the training to set
     */
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getDateAsString() {
        String dateString = null;
        if (eventDate != null) {
            DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMANY);
            dateString = sdf.format(eventDate.getTime());
        }
        return dateString;
    }

    @Override
    public String toString() {
        return "SportsEvent{" + "id=" + id + ", eventDate=" + eventDate + ", teams=" + teams + ", trainingDay=" + sportsEventDetail + '}';
    }

    /**
     * @return the teams
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * @param teams the teams to set
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getTeamsAsFormattedString() {
        StringBuffer retString = new StringBuffer(50);
        if (teams != null) {
            int x = 1;
            for (Team currentTeam : teams) {
                retString.append(currentTeam.getShortName());
                if ((teams.size() > 1) && (teams.size() != x)) {
                    retString.append(", ");
                }
                x++;
            }
        }
        return retString.toString();
    }

    /**
     * @param sportsEventDetail the sportsEventDetail to set (Mo. - Fr.)
     */
    public void setSportsEventDetail(SportsEventDetails sportsEventDetail) {
        this.sportsEventDetail = sportsEventDetail;
    }

    public SportsEventDetails getSportsEventDetail() {
        return this.sportsEventDetail;
    }

}
