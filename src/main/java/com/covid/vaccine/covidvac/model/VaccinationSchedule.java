package com.covid.vaccine.covidvac.model;


import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name ="vaccine_schedule")
public class VaccinationSchedule implements Serializable {

    private static final long serialVersionUID = 2556157282083349560L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccineId")
    private  Vaccine vaccine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccineBranchId")
    private  VaccineBranch vaccineBranch;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private Patient patient;


    private  boolean isVaccinated;

    @Column(name = "scheduledDate")
    private Date scheDuledDate;


    @Column(name = "vacciNationDateTime")
    private Long vacciNationTime;


    @Transient
    private String vaccineTime;



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



    public boolean isVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        isVaccinated = vaccinated;
    }

    public Date getScheDuledDate() {
        return scheDuledDate;
    }

    public void setScheDuledDate(Date scheDuledDate) {
        this.scheDuledDate = scheDuledDate;
    }

    public Long getVacciNationTime() {
        return vacciNationTime;
    }

    public void setVacciNationTime(Date vacciNationTime) {
        this.vacciNationTime = vacciNationTime.getTime();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getVaccineTime() {
       return  new SimpleDateFormat("dd-M-yyyy hh:mm").format(new Date(vacciNationTime));
    }

    public void setVaccineTime(String vaccineTime) {
        this.vaccineTime = vaccineTime;
    }
}
