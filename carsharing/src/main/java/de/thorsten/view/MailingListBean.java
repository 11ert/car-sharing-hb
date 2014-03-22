package de.thorsten.view;

import de.thorsten.data.MailingListListProducer;
import de.thorsten.data.MailingListRepository;
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

import de.thorsten.model.MailingList;
import java.util.Collection;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 * Backing bean for MailingList entities.
 * <p>
 * This class provides CRUD functionality for all MailingList entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */
@Named
@Stateful
@ConversationScoped
public class MailingListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving MailingList entities
     */
    private Long id;

    private Collection<String> allAvalaibleEMailAdresses;

    @Inject
    private MailingListListProducer mailingListProducer;

    @Inject
    Logger log;

    @PostConstruct
    public void init() {
        log.info("MailingListBean.init()");
        allAvalaibleEMailAdresses = mailingListProducer.getAllAvailableEMailAdresses();
        if (allAvalaibleEMailAdresses != null) {
            log.fine(allAvalaibleEMailAdresses.size() + " eMailAdresses available");
        }

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private MailingList mailingList;

    public MailingList getMailingList() {
        return this.mailingList;
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
            this.mailingList = this.example;
        } else {
            this.mailingList = findById(getId());
        }
    }

    public MailingList findById(Long id) {

        return this.entityManager.find(MailingList.class, id);
    }

    /*
     * Support updating and deleting MailingList entities
     */
    public String update() {
        this.conversation.end();

        try {
            if (this.id == null) {
                this.entityManager.persist(this.mailingList);
                return "search?faces-redirect=true";
            } else {
                this.entityManager.merge(this.mailingList);
                return "view?faces-redirect=true&id=" + this.mailingList.getId();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String delete() {
        this.conversation.end();

        try {
            MailingList deletableEntity = findById(getId());

            this.entityManager.remove(deletableEntity);
            this.entityManager.flush();
            return "search?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    /*
     * Support searching MailingList entities with pagination
     */
    private int page;
    private long count;
    private List<MailingList> pageItems;

    private MailingList example = new MailingList();

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 10;
    }

    public MailingList getExample() {
        return this.example;
    }

    public void setExample(MailingList example) {
        this.example = example;
    }

    public void search() {
        this.page = 0;
    }

    public void paginate() {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        // Populate this.count
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<MailingList> root = countCriteria.from(MailingList.class);
        countCriteria = countCriteria.select(builder.count(root)).where(
                getSearchPredicates(root));
        this.count = this.entityManager.createQuery(countCriteria)
                .getSingleResult();

        // Populate this.pageItems
        CriteriaQuery<MailingList> criteria = builder.createQuery(MailingList.class);
        root = criteria.from(MailingList.class);
        TypedQuery<MailingList> query = this.entityManager.createQuery(criteria
                .select(root).where(getSearchPredicates(root)));
        query.setFirstResult(this.page * getPageSize()).setMaxResults(
                getPageSize());
        this.pageItems = query.getResultList();
    }

    private Predicate[] getSearchPredicates(Root<MailingList> root) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        String description = this.example.getDescription();
        if (description != null && !"".equals(description)) {
            predicatesList.add(builder.like(root.<String>get("description"), '%' + description + '%'));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    public List<MailingList> getPageItems() {
        return this.pageItems;
    }

    public long getCount() {
        return this.count;
    }

    /*
     * Support listing and POSTing back MailingList entities (e.g. from inside an
     * HtmlSelectOneMenu)
     */
    public List<MailingList> getAll() {

        CriteriaQuery<MailingList> criteria = this.entityManager
                .getCriteriaBuilder().createQuery(MailingList.class);
        return this.entityManager.createQuery(
                criteria.select(criteria.from(MailingList.class))).getResultList();
    }

    @Resource
    private SessionContext sessionContext;

    public Converter getConverter() {

        final MailingListBean ejbProxy = this.sessionContext.getBusinessObject(MailingListBean.class);

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

                return String.valueOf(((MailingList) value).getId());
            }
        };
    }

    /*
     * Support adding children to bidirectional, one-to-many tables
     */
    private MailingList add = new MailingList();

    public MailingList getAdd() {
        return this.add;
    }

    public MailingList getAdded() {
        MailingList added = this.add;
        this.add = new MailingList();
        return added;
    }

    public Collection<String> getAllAvalaibleEMailAdresses() {
        return allAvalaibleEMailAdresses;
    }
}
