package de.thorsten.view;

import de.thorsten.data.MemberListProducer;
import de.thorsten.model.Member;
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

import de.thorsten.model.ParticipationGroup;
import static java.lang.StrictMath.log;
import java.util.Collection;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 * Backing bean for ParticipationGroup entities.
 * <p>
 * This class provides CRUD functionality for all ParticipationGroup entities.
 * It focuses purely on Java EE 6 standards (e.g.
 * <tt>&#64;ConversationScoped</tt> for state management,
 * <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */
@Named
@Stateful
@ConversationScoped
public class ParticipationGroupBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving ParticipationGroup entities
     */
    private Long id;

    @Inject 
    private Logger log;
    
    private Collection<Member> allAvalaibleMembers;

    @Inject
    private MemberListProducer memberListProducer;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private ParticipationGroup participationGroup;

    public ParticipationGroup getParticipationGroup() {
        return this.participationGroup;
    }

    @Inject
    private Conversation conversation;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        log.fine("ParticipationGroupBean.init()");
        allAvalaibleMembers = memberListProducer.getMembers();
        if (allAvalaibleMembers != null) {
            log.fine(allAvalaibleMembers.size() + " Members available");
        }

    }

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
            this.participationGroup = this.example;
        } else {
            this.participationGroup = findById(getId());
        }
    }

    public ParticipationGroup findById(Long id) {

        return this.entityManager.find(ParticipationGroup.class, id);
    }

    /*
     * Support updating and deleting ParticipationGroup entities
     */
    public String update() {
        this.conversation.end();

        try {
            if (this.id == null) {
                this.entityManager.persist(this.participationGroup);
                return "search?faces-redirect=true";
            } else {
                this.entityManager.merge(this.participationGroup);
                return "view?faces-redirect=true&id=" + this.participationGroup.getId();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String delete() {
        this.conversation.end();

        try {
            ParticipationGroup deletableEntity = findById(getId());

            this.entityManager.remove(deletableEntity);
            this.entityManager.flush();
            return "search?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    /*
     * Support searching ParticipationGroup entities with pagination
     */
    private int page;
    private long count;
    private List<ParticipationGroup> pageItems;

    private ParticipationGroup example = new ParticipationGroup();

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 10;
    }

    public ParticipationGroup getExample() {
        return this.example;
    }

    public void setExample(ParticipationGroup example) {
        this.example = example;
    }

    public void search() {
        this.page = 0;
    }

    public void paginate() {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<ParticipationGroup> root = countCriteria.from(ParticipationGroup.class);
        countCriteria = countCriteria.select(builder.count(root)).where(
                getSearchPredicates(root));
        this.count = this.entityManager.createQuery(countCriteria)
                .getSingleResult();

      // Populate this.pageItems
        CriteriaQuery<ParticipationGroup> criteria = builder.createQuery(ParticipationGroup.class);
        root = criteria.from(ParticipationGroup.class);
        TypedQuery<ParticipationGroup> query = this.entityManager.createQuery(criteria
                .select(root).where(getSearchPredicates(root)));
        query.setFirstResult(this.page * getPageSize()).setMaxResults(
                getPageSize());
        this.pageItems = query.getResultList();
    }

    private Predicate[] getSearchPredicates(Root<ParticipationGroup> root) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        String description = this.example.getDescription();
        if (description != null && !"".equals(description)) {
            predicatesList.add(builder.like(root.<String>get("description"), '%' + description + '%'));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    public List<ParticipationGroup> getPageItems() {
        return this.pageItems;
    }

    public long getCount() {
        return this.count;
    }

    /*
     * Support listing and POSTing back ParticipationGroup entities (e.g. from inside an
     * HtmlSelectOneMenu)
     */
    public List<ParticipationGroup> getAll() {

        CriteriaQuery<ParticipationGroup> criteria = this.entityManager
                .getCriteriaBuilder().createQuery(ParticipationGroup.class);
        return this.entityManager.createQuery(
                criteria.select(criteria.from(ParticipationGroup.class))).getResultList();
    }

    @Resource
    private SessionContext sessionContext;

    public Converter getConverter() {

        final ParticipationGroupBean ejbProxy = this.sessionContext.getBusinessObject(ParticipationGroupBean.class);

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

                return String.valueOf(((ParticipationGroup) value).getId());
            }
        };
    }

    /*
     * Support adding children to bidirectional, one-to-many tables
     */
    private ParticipationGroup add = new ParticipationGroup();

    public ParticipationGroup getAdd() {
        return this.add;
    }

    public ParticipationGroup getAdded() {
        ParticipationGroup added = this.add;
        this.add = new ParticipationGroup();
        return added;
    }

    /**
     * @return the allAvalaibleMembers
     */
    public Collection<Member> getAllAvalaibleMembers() {
        return allAvalaibleMembers;
    }

    /**
     * @param allAvalaibleMembers the allAvalaibleMembers to set
     */
    public void setAllAvalaibleMembers(Collection<Member> allAvalaibleMembers) {
        this.allAvalaibleMembers = allAvalaibleMembers;
    }
}
