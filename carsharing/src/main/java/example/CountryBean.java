/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 *
 * @author Thorsten
 */
@SessionScoped
@Named("country")
public class CountryBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Map<String, String> countries;

    private String localeCode = "en"; //default value 

    static {
        countries = new LinkedHashMap<String, String>();
        countries.put("United Kingdom", "en"); //label, value
        countries.put("French", "fr");
        countries.put("German", "de");
        countries.put("China", "zh_CN");
    }

    public void countryLocaleCodeChanged(ValueChangeEvent e) {
        //assign new value to localeCode
        localeCode = e.getNewValue().toString();

    }

    public Map<String, String> getCountryInMap() {
        return this.countries;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

}
