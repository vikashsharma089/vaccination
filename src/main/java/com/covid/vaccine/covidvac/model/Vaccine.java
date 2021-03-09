package com.covid.vaccine.covidvac.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name ="vaccine",uniqueConstraints={@UniqueConstraint(columnNames={"vaccine_name"})})
public class Vaccine implements Serializable {


    private static final long serialVersionUID = 8786007416281778738L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vaccine_name")
    private String vaccineName;

    @Column(name = "created_on")
    private Date creationDate;

    @Column(name = "modified_on")
    private Date modifiedDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }
}
