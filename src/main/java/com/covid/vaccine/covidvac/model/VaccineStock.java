package com.covid.vaccine.covidvac.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="vaccine_stock")
public class VaccineStock implements Serializable {


    private static final long serialVersionUID = -7713589707233988849L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccineId")
    private  Vaccine vaccine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccineBranchId")
    private  VaccineBranch vaccineBranch;

    @Transient
    private String availableTiming;

    @Column(name = "vaccineQty")
    private Integer vaccineQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public VaccineBranch getVaccineBranch() {
        return vaccineBranch;
    }

    public void setVaccineBranch(VaccineBranch vaccineBranch) {
        this.vaccineBranch = vaccineBranch;
    }

    public Integer getVaccineQuantity() {
        return vaccineQuantity;
    }

    public void setVaccineQuantity(Integer vaccineQuantity) {
        this.vaccineQuantity = vaccineQuantity;
    }

    public String getAvailableTiming() {
        return availableTiming;
    }

    public void setAvailableTiming(String availableTiming) {
        this.availableTiming = availableTiming;
    }
}
