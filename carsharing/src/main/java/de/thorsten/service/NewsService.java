package de.thorsten.service;

import de.thorsten.model.News;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;

/**
 *
 * @author Thorsten
 */
@Stateless
public class NewsService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<News> newsEventSrc;

    @Inject
    private MailSender mailSender;

    public News update(News news) throws Exception {
        log.info("Updating " + news);
        news.setCreationDate((new Date()));
        News newNews = em.merge(news);
        newsEventSrc.fire(newNews);
        //this.sendMail();
        return newNews;
    }

    private void sendMail() {
        try {
            log.info("send mail...");
            String mailFrom = "von.mir@mail.com";
            String mailTo = "thorsten.elfert@db.com";
            String replyTo = "test@mail.de";
            String subject = "Betreff=blabla";
            String text = "Nachrichtentext.....";
            mailSender.sendMail(mailFrom, mailTo, replyTo, subject, text);
            log.info("...fertig");
        } catch (MessagingException e) {
            log.warning("error sending mail: " + e.getMessage());
        }
    }
}
