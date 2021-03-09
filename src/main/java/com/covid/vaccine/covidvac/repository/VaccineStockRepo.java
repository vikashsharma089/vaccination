package com.covid.vaccine.covidvac.repository;

import com.covid.vaccine.covidvac.model.VaccinationSchedule;
import com.covid.vaccine.covidvac.model.VaccineStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineStockRepo extends JpaRepository<VaccineStock,Integer> {

    @Query("select u from VaccineStock u where u.vaccineBranch.id = ?1 and u.vaccine.id = ?2 ")
    public List<VaccineStock> findByVaccineAndBranch(@Param("branchId") Integer branchId,@Param("vaccineId") Integer vaccineId);

    @Query("select u from VaccineStock u where u.vaccineBranch.id = ?1  ")
    public List<VaccineStock> getVaccinesByBranch(@Param("branchId") Integer branchId);
}
