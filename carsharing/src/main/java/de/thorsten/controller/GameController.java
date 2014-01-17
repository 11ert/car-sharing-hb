package de.thorsten.controller;

import de.thorsten.data.MemberRepository;
import de.thorsten.model.Game;
import de.thorsten.model.Member;
import de.thorsten.model.Participation;
import de.thorsten.model.Training;
import de.thorsten.service.GameService;
import de.thorsten.service.ParticipationService;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
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
@Named("gameController")
@ViewAccessScoped
public class GameController implements Serializable {

    @Inject
    private Logger log;

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ParticipationService participationService;

    @Inject
    private GameService gameService;

    private Date nextDate;

    private List<Member> initialMembers;

    @Produces
    @Named
    private Game newGame;

    @PostConstruct
    public void loadMembers() {
        newGame = new Game();

        initialMembers = memberRepository.findAllOrderedByName();
        if (initialMembers != null) {
            log.info(initialMembers.size() + " initial Members loaded.");
        }

    }

    /**
     * @return the nextTrainingDate
     */
    public Date getNextDate() {
        return nextDate;
    }

    /**
     * @param nextDate the nextDate to set
     */
    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public void createParticipationsForNextDate() {

        newGame.setEventDate(nextDate);

        FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fehler!", "Neues Spiel konnte nicht gespeichert werden !");
        FacesMessage successMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Erfolgreich!", "Neues Spiel gespeichert!");
        try {

            Game game = gameService.update(newGame);

            for (final Iterator it = initialMembers.iterator(); it.hasNext();) {
                Participation newParticipation = new Participation();
                newParticipation.setPlayer((Member) it.next());
                newParticipation.setTraining(game);

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
            log.info("Spiel konnte nicht persistiert werden");
            e.printStackTrace();
        }
        facesContext.addMessage(null, successMsg);

    }

    public void trainingDateChanged(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            nextDate = (Date) event.getNewValue();
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
