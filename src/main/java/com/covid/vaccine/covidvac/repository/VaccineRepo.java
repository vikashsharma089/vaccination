package com.covid.vaccine.covidvac.repository;

import com.covid.vaccine.covidvac.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Integer> {

}
