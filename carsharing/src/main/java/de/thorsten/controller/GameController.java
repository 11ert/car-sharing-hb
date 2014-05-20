package de.thorsten.controller;

import de.thorsten.data.MemberRepository;
import de.thorsten.data.TeamListProducer;
import de.thorsten.data.TeamRepository;
import de.thorsten.model.Game;
import de.thorsten.model.Member;
import de.thorsten.model.Participation;
import de.thorsten.model.ParticipationGroup;
import de.thorsten.model.Team;
import de.thorsten.service.GameService;
import de.thorsten.service.ParticipationService;
import java.io.Serializable;
import java.util.ArrayList;
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

    private Team teamForGame;

    private List<Team> listOfTeamsForTheGame = new ArrayList();

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private TeamListProducer teamListProducer;

    @Produces
    @Named
    private Game newGame;

    @Inject
    @Current
    private ParticipationGroup selectedParticipationGroup;

    @PostConstruct
    public void load() {
        newGame = new Game();
        teamForGame = teamListProducer.getTeams().get(0);
        listOfTeamsForTheGame.add(teamForGame);
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
        newGame.setTeams(listOfTeamsForTheGame);

        FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fehler!", "Neues Spiel konnte nicht gespeichert werden !");
        FacesMessage successMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Erfolgreich!", "Neues Spiel gespeichert!");
        try {

            Game game = gameService.update(newGame);

            List<Member> members = null;
            members = selectedParticipationGroup.getMembers();

            for (final Iterator it = members.iterator(); it.hasNext();) {
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

    public void teamChanged(ValueChangeEvent event) {
        log.info("teamChanged()");
        listOfTeamsForTheGame.clear();
        if (event.getNewValue() != null) {
            Long tmpId = Long.valueOf((String) event.getNewValue());
            teamForGame = teamRepository.findById(tmpId);
            listOfTeamsForTheGame.add(teamForGame);
            log.info("...to new Team = " + teamForGame.toString());
        }
    }

}
