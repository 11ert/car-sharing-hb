package de.thorsten.view;

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

import de.thorsten.model.TrainingDay;
import static de.thorsten.model.TrainingDay_.weekday;
import de.thorsten.util.DateUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import javax.faces.event.ValueChangeEvent;

/**
 * Backing bean for TrainingDay entities.
 * <p>
 * This class provides CRUD functionality for all TrainingDay entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */
@Named
@Stateful
@ConversationScoped
public class TrainingDayBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving TrainingDay entities
     */
    private Long id;
    
    @Inject
    Logger log;
    
    private int selectedWeekday = -1;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    private TrainingDay trainingDay;
    
    public TrainingDay getTrainingDay() {
        return this.trainingDay;
    }
    
    @Inject
    private Conversation conversation;
    
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    
    public String create() {
        
        this.conversation.begin();
        return "create?faces-redirect=true";
    }
    
    public void retrieve() {
        
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }
        
        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }
        
        if (this.id == null) {
            this.trainingDay = this.example;
        } else {
            this.trainingDay = findById(getId());
        }
    }
    
    public TrainingDay findById(Long id) {
        
        return this.entityManager.find(TrainingDay.class, id);
    }

    /*
     * Support updating and deleting TrainingDay entities
     */
    public String update() {
        this.conversation.end();
        
        try {
            if (this.id == null) {
                this.entityManager.persist(this.trainingDay);
                return "search?faces-redirect=true";
            } else {
                this.entityManager.merge(this.trainingDay);
                return "view?faces-redirect=true&id=" + this.trainingDay.getId();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }
    
    public String delete() {
        this.conversation.end();
        
        try {
            TrainingDay deletableEntity = findById(getId());
            
            this.entityManager.remove(deletableEntity);
            this.entityManager.flush();
            return "search?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    /*
     * Support searching TrainingDay entities with pagination
     */
    private int page;
    private long count;
    private List<TrainingDay> pageItems;
    
    private TrainingDay example = new TrainingDay();
    
    public int getPage() {
        return this.page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getPageSize() {
        return 10;
    }
    
    public TrainingDay getExample() {
        return this.example;
    }
    
    public void setExample(TrainingDay example) {
        this.example = example;
    }
    
    public void search() {
        this.page = 0;
    }
    
    public void paginate() {
        
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<TrainingDay> root = countCriteria.from(TrainingDay.class);
        countCriteria = countCriteria.select(builder.count(root)).where(
                getSearchPredicates(root));
        this.count = this.entityManager.createQuery(countCriteria)
                .getSingleResult();

      // Populate this.pageItems
        CriteriaQuery<TrainingDay> criteria = builder.createQuery(TrainingDay.class);
        root = criteria.from(TrainingDay.class);
        TypedQuery<TrainingDay> query = this.entityManager.createQuery(criteria
                .select(root).where(getSearchPredicates(root)));
        query.setFirstResult(this.page * getPageSize()).setMaxResults(
                getPageSize());
        this.pageItems = query.getResultList();
    }
    
    private Predicate[] getSearchPredicates(Root<TrainingDay> root) {
        
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        
        int weekday = this.example.getWeekday();
        if (weekday != 0) {
            predicatesList.add(builder.equal(root.get("weekday"), weekday));
        }
        Long id = this.example.getId();
        if (id != null && id.intValue() != 0) {
            predicatesList.add(builder.equal(root.get("id"), id));
        }
        String timeFromTemplate = this.example.getTimeFromTemplate();
        if (timeFromTemplate != null && !"".equals(timeFromTemplate)) {
            predicatesList.add(builder.like(root.<String>get("timeFromTemplate"), '%' + timeFromTemplate + '%'));
        }
        String timeToTemplate = this.example.getTimeToTemplate();
        if (timeToTemplate != null && !"".equals(timeToTemplate)) {
            predicatesList.add(builder.like(root.<String>get("timeToTemplate"), '%' + timeToTemplate + '%'));
        }
        String pickUpTimeSourceTemplate = this.example.getPickUpTimeSourceTemplate();
        if (pickUpTimeSourceTemplate != null && !"".equals(pickUpTimeSourceTemplate)) {
            predicatesList.add(builder.like(root.<String>get("pickUpTimeSourceTemplate"), '%' + pickUpTimeSourceTemplate + '%'));
        }
        
        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }
    
    public List<TrainingDay> getPageItems() {
        return this.pageItems;
    }
    
    public long getCount() {
        return this.count;
    }

    /*
     * Support listing and POSTing back TrainingDay entities (e.g. from inside an
     * HtmlSelectOneMenu)
     */
    public List<TrainingDay> getAll() {
        
        CriteriaQuery<TrainingDay> criteria = this.entityManager
                .getCriteriaBuilder().createQuery(TrainingDay.class);
        return this.entityManager.createQuery(
                criteria.select(criteria.from(TrainingDay.class))).getResultList();
    }
    
    @Resource
    private SessionContext sessionContext;
    
    public Converter getConverter() {
        
        final TrainingDayBean ejbProxy = this.sessionContext.getBusinessObject(TrainingDayBean.class);
        
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
                
                return String.valueOf(((TrainingDay) value).getId());
            }
        };
    }

    /*
     * Support adding children to bidirectional, one-to-many tables
     */
    private TrainingDay add = new TrainingDay();
    
    public TrainingDay getAdd() {
        return this.add;
    }
    
    public TrainingDay getAdded() {
        TrainingDay added = this.add;
        this.add = new TrainingDay();
        return added;
    }
    
    public String getWeekdayAsString() {
        return DateUtil.getWeekdayAsString(this.trainingDay.getWeekday());
    }
    
    public void weekdayChanged(ValueChangeEvent event) {
        log.info("weekdayChanged ");
        if (event.getNewValue() != null) {
            String test = (String)event.getNewValue();
            Integer intTest = Integer.parseInt(test); 
            selectedWeekday = intTest;
        this.trainingDay.setWeekday(selectedWeekday);
            log.fine("...to " + selectedWeekday);
            // todo - was mit attribut "weekday" ?
        }
        
    }

    /**
     * @return the selectedWeekday
     */
    public int getSelectedWeekday() {
        return selectedWeekday;
    }

    /**
     * @param selectedWeekday the selectedWeekday to set
     */
    public void setSelectedWeekday(int selectedWeekday) {
        this.selectedWeekday = selectedWeekday;
    }
}
