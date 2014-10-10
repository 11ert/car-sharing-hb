package de.thorsten.view;

import de.thorsten.model.SportsEventDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.thorsten.model.Training;
import javax.annotation.PostConstruct;

/**
 * Backing bean for Training entities.
 * <p>
 * This class provides CRUD functionality for all Training entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */
@Named
@Stateful
@ConversationScoped
public class TrainingBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving Training entities
     */
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Training training;

    public Training getTraining() {
        return this.training;
    }

    @Inject
    private Conversation conversation;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public String create() {

        this.conversation.begin();
        return "createTraining?faces-redirect=true";
    }

    public void retrieve() {

        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        if (this.id == null) {
            this.training = this.example;
        } else {
            this.training = findById(getId());
        }
    }

    public Training findById(Long id) {

        return this.entityManager.find(Training.class, id);
    }

    /*
     * Support updating and deleting Training entities
     */
    public String update() {
        this.conversation.end();

        try {
            if (this.id == null) {
                this.entityManager.persist(this.training);
                return "searchTraining?faces-redirect=true";
            } else {
                this.entityManager.merge(this.training);
                return "viewTraining?faces-redirect=true&id=" + this.training.getId();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }
    
    @PostConstruct
    public void init() {
        this.training = new Training();
        this.example = new Training();
        SportsEventDetails sportsEventDetails = new SportsEventDetails();
        this.example.setSportsEventDetail(sportsEventDetails);
    }

    public String delete() {
        this.conversation.end();

        try {
            Training deletableEntity = findById(getId());

            this.entityManager.remove(deletableEntity);
            this.entityManager.flush();
            return "searchTraining?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    /*
     * Support searching Training entities with pagination
     */
    private int page;
    private long count;
    private List<Training> pageItems;

    private Training example = new Training();

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 10;
    }

    public Training getExample() {
        return this.example;
    }

    public void setExample(Training example) {
        this.example = example;
    }

    public void search() {
        this.page = 0;
    }

    public void paginate() {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Training> root = countCriteria.from(Training.class);
        countCriteria = countCriteria.select(builder.count(root)).where(
                getSearchPredicates(root));
        this.count = this.entityManager.createQuery(countCriteria)
                .getSingleResult();

      // Populate this.pageItems
        CriteriaQuery<Training> criteria = builder.createQuery(Training.class);
        root = criteria.from(Training.class);
        TypedQuery<Training> query = this.entityManager.createQuery(criteria
                .select(root).where(getSearchPredicates(root)));
        query.setFirstResult(this.page * getPageSize()).setMaxResults(
                getPageSize());
        this.pageItems = query.getResultList();
    }

    private Predicate[] getSearchPredicates(Root<Training> root) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        if (example != null) {
            String timeFrom = this.example.getSportsEventDetail().getTimeFrom();
            if (timeFrom != null && !"".equals(timeFrom)) {
                predicatesList.add(builder.like(root.<String>get("timeFrom"), '%' + timeFrom + '%'));
            }
            String timeTo = this.example.getSportsEventDetail().getTimeTo();
            if (timeTo != null && !"".equals(timeTo)) {
                predicatesList.add(builder.like(root.<String>get("timeTo"), '%' + timeTo + '%'));
            }
            String pickUpTimeSource = this.example.getSportsEventDetail().getPickUpTimeSource();
            if (pickUpTimeSource != null && !"".equals(pickUpTimeSource)) {
                predicatesList.add(builder.like(root.<String>get("pickUpTimeSource"), '%' + pickUpTimeSource + '%'));
            }
            String pickUpTimeTarget = this.example.getSportsEventDetail().getPickUpTimeTarget();
            if (pickUpTimeTarget != null && !"".equals(pickUpTimeTarget)) {
                predicatesList.add(builder.like(root.<String>get("pickUpTimeTarget"), '%' + pickUpTimeTarget + '%'));
            }
            String location = this.example.getSportsEventDetail().getLocation();
            if (location != null && !"".equals(location)) {
                predicatesList.add(builder.like(root.<String>get("location"), '%' + location + '%'));
            }
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    public List<Training> getPageItems() {
        return this.pageItems;
    }

    public long getCount() {
        return this.count;
    }

    /*
     * Support listing and POSTing back Training entities (e.g. from inside an
     * HtmlSelectOneMenu)
     */
    public List<Training> getAll() {

        CriteriaQuery<Training> criteria = this.entityManager
                .getCriteriaBuilder().createQuery(Training.class);
        return this.entityManager.createQuery(
                criteria.select(criteria.from(Training.class))).getResultList();
    }

    @Resource
    private SessionContext sessionContext;

    public Converter getConverter() {

        final TrainingBean ejbProxy = this.sessionContext.getBusinessObject(TrainingBean.class);

        return new Converter() {

            @Override
            public Object getAsObject(FacesContext context,
                    UIComponent component, String value) {

                return ejbProxy.findById(Long.valueOf(value));
            }

            @Override
            public String getAsString(FacesContext context,
                    UIComponent component, Object value) {

                if (value == null) {
                    return "";
                }

                return String.valueOf(((Training) value).getId());
            }
        };
    }

    /*
     * Support adding children to bidirectional, one-to-many tables
     */
    private Training add = new Training();

    public Training getAdd() {
        return this.add;
    }

    public Training getAdded() {
        Training added = this.add;
        this.add = new Training();
        return added;
    }
}
