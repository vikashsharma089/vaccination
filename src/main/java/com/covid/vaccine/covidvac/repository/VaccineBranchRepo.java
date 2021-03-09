package com.covid.vaccine.covidvac.repository;


import com.covid.vaccine.covidvac.model.VaccineBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineBranchRepo extends JpaRepository<VaccineBranch, Integer> {

    @Query("select u from VaccineBranch u where u.branchName = ?1")
    public List<VaccineBranch> findByBranchName(@Param("branchName") String branchName);

}
