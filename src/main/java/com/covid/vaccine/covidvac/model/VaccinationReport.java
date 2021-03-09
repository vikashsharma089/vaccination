package com.covid.vaccine.covidvac.model;

public class VaccinationReport {

    String branchName;
    long appliedVacciation;

    VaccinationReport(){

    }
    VaccinationReport(String branchName, long appliedVacciation){
        this.branchName = branchName;
        this.appliedVacciation= appliedVacciation;
    }


    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public long getAppliedVacciation() {
        return appliedVacciation;
    }

    public void setAppliedVacciation(long appliedVacciation) {
        this.appliedVacciation = appliedVacciation;
    }
}
