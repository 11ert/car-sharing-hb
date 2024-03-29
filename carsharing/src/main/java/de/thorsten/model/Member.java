package de.thorsten.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table
public class Member implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    private Long id;

    @NotNull 
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Darf keine Zahlen enthalten")
    private String name;

    //@NotNull
    //@NotEmpty
    @Email
    private String email;

    //@NotNull
    //@Size(min = 10, max = 12)
    //@Digits(fraction = 0, integer = 12)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Min(value = 0) @Max(value = 10)
    @Column(name = "carsize")
    private int carsize = 0;

    @Size(min = 0, max = 25)
    private String firstname;
    
    @Size(min = 0, max = 25)
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the carsize
     */
    public int getCarsize() {
        return carsize;
    }

    /**
     * @param carsize the carsize to set
     */
    public void setCarsize(int carsize) {
        this.carsize = carsize;
        
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Member)) {
            return false;
        }
        Member other = (Member) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
 
}
