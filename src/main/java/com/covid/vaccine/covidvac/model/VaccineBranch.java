package com.covid.vaccine.covidvac.model;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Repeatable;
import java.util.Date;


@Entity
@Table(name ="vaccine_branch", uniqueConstraints={@UniqueConstraint(columnNames={"branch_name"})})
public class VaccineBranch implements Serializable {

    private static final long serialVersionUID = 5335659536862756631L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_address")
    private String branchAddress;

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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }
}

