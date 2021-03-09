package com.covid.vaccine.covidvac.controller;

import java.util.Objects;

public class VaccinationReport {

    Long totalVaccination;
    Long confirmedVaccination;
    Long remainingVaccination;

    public Long getTotalVaccination() {
        return totalVaccination;
    }

    public void setTotalVaccination(Long totalVaccination) {
        this.totalVaccination = totalVaccination;
    }

    public Long getConfirmedVaccination() {
        return confirmedVaccination;
    }

    public void setConfirmedVaccination(Long confirmedVaccination) {
        this.confirmedVaccination = confirmedVaccination;
    }

    public Long getRemainingVaccination() {
        return remainingVaccination;
    }

    public void setRemainingVaccination(Long remainingVaccination) {
        this.remainingVaccination = remainingVaccination;
    }
}
