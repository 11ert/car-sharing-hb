package de.thorsten.controller;

import de.thorsten.data.MemberRepository;
import de.thorsten.data.TrainingDayRepository;
import de.thorsten.model.Member;
import de.thorsten.model.Participation;
import de.thorsten.model.Training;
import de.thorsten.model.TrainingDay;
import de.thorsten.service.ParticipationService;
import de.thorsten.service.TrainingService;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.os890.cdi.ext.scope.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author Thorsten
 */
@Named("trainingController")
@ViewAccessScoped
public class CreateNewTrainingController implements Serializable {

    @Inject
    private Logger log;

    @Inject
    private TrainingDayRepository trainingDayRepository;

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ParticipationService participationService;

    @Inject
    TrainingService trainingService;

    private Date nextTrainingDate;

    private List<Member> initialMembers;

    @PostConstruct
    public void loadMembers() {
        initialMembers = memberRepository.findAllOrderedByName();
        if (initialMembers != null) {
            log.info(initialMembers.size() + " initial Members loaded.");
        }
    }

    /**
     * @return the nextTrainingDate
     */
    public Date getNextTrainingDate() {
        return nextTrainingDate;
    }

    /**
     * @param nextTrainingDate the nextTrainingDate to set
     */
    public void setNextTrainingDate(Date nextTrainingDate) {
        this.nextTrainingDate = nextTrainingDate;
    }

    @SuppressWarnings("empty-statement")
    public void createParticipationsForNextTrainingDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextTrainingDate);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        List trDayList = trainingDayRepository.findByWeekday(weekday);

        if (trDayList == null) {
            this.createErrorMessage("Kein gültiges Trainingsdatum (Wochentag?)");
        }
        if (trDayList.size() == 0) {
            this.createErrorMessage("Kein gültiges Trainingsdatum (Wochentag?)");
        }
        Training newTraining = new Training();
        newTraining.setEventDate(nextTrainingDate);
        newTraining.setTrainingDay((TrainingDay) trDayList.get(0));
        newTraining.initializeTrainingWithTrainingDayTemplateData();

        log.info("Neuer Trainingseintrag " + newTraining.getDateAsString()
                + ", "
                + ", " + newTraining.getLocation()
                + ", " + newTraining.getTimeFrom()
                + " - " + newTraining.getTimeTo());
        
        FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fehler!", "Neues Training konnte nicht gespeichert werden !");
        FacesMessage successMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Erfolgreich!", "Neues Training gespeichert!");

        try {

            Training tr = trainingService.update(newTraining);
            for (final Iterator it = initialMembers.iterator(); it.hasNext();) {
                Participation newParticipation = new Participation();
                newParticipation.setPlayer((Member) it.next());
                newParticipation.setTraining(tr);
                try {
                    participationService.update(newParticipation);
                } catch (Exception e) {
                    facesContext.addMessage(null, errorMsg);
                    log.info("Participation could not be stored");
                    e.printStackTrace();
                }
            };
        } catch (Exception e) {
            facesContext.addMessage(null, errorMsg);
            log.info("Training konnte nicht persistiert werden");
            e.printStackTrace();
        }

        facesContext.addMessage(null, successMsg);

    }

    public void trainingDateChanged(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            nextTrainingDate = (Date) event.getNewValue();
        }
    }

    public void createErrorMessage(String msg) {
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fehler!",
                msg);
        facesContext.addMessage(null, m);
    }

    public void removeMemberFromList(Member member) {
        log.info("removeMemberFromList called");
    }

    /**
     * @return the members
     */
    public List<Member> getInitialMembers() {
        return initialMembers;
    }

    /**
     * @param initialMembers the members to set
     */
    public void setInitialMembers(List<Member> initialMembers) {
        this.initialMembers = initialMembers;
    }

}
