package com.covid.vaccine.covidvac.repository;

import com.covid.vaccine.covidvac.model.Patient;
import com.covid.vaccine.covidvac.model.VaccinationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

    @Query("select u from Patient u where u.emailId = ?1 ")
    public List<Patient> findByEmailId(@Param("emailId") String emailId);
}
