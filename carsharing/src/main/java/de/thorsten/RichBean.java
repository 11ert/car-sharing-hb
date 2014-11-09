package de.thorsten;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean
@SessionScoped
public class RichBean implements Serializable {

    private static final long serialVersionUID = -2403138958014741653L;
    private String name;
    private List<Date> listOfDates;
    private List<Date> listOfNewDates;

    @Inject
    private FacesContext facesContext;

    public RichBean() {
        name = "John";
    }

    @PostConstruct
    public void init() {
        System.out.println("post construct: init()");
        listOfDates = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        listOfDates.add(cal.getTime());
        cal.add(Calendar.DATE, 1);
        listOfDates.add(cal.getTime());
        cal.add(Calendar.DATE, 2);
        listOfDates.add(cal.getTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the listOfDates
     */
    public List<Date> getListOfDates() {
        System.out.println("getListOfDates");
        if (listOfDates != null) {
            System.out.println("Size = " + listOfDates.size());
        }

        return listOfDates;
    }

    /**
     * @return the listOfNewDates
     */
    public List<Date> getListOfNewDates() {
        System.out.println("getListOfNewDates");
        if (listOfNewDates != null) {
            System.out.println("Size = " + listOfNewDates.size());
        }
        return listOfNewDates;
    }

    /**
     * @param listOfNewDates the listOfNewDates to set
     */
    public void setListOfNewDates(List<Date> listOfNewDates) {
        System.out.println("setListOfNewDates");
        this.listOfNewDates = listOfNewDates;
    }

    public void printListOfDates() {
        try {
            System.out.println("printDates");
            if (listOfNewDates != null) {
                System.out.println("Size = " + listOfNewDates.size());
            } else {
                System.out.println("listOfNewDates = null");
            }
            Calendar cal = Calendar.getInstance();
            for (Iterator<Date> it = listOfNewDates.iterator(); it.hasNext();) {
                cal.setTime((Date) it.next());
                cal.add(Calendar.DATE, 1);
                System.out.println("Neues Datum = " + cal.getTime().toString());
            }
            FacesMessage successMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Erfolg!", "Erfolgreich!");
            facesContext.addMessage(null, successMsg);

        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Fehler!", "Fehler !" + "+");
            facesContext.addMessage(null, errorMsg);

        }
    }

}
