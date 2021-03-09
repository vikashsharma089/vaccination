package com.covid.vaccine.covidvac.repository;

import com.covid.vaccine.covidvac.model.VaccinationReport;
import com.covid.vaccine.covidvac.model.VaccinationSchedule;
import com.covid.vaccine.covidvac.model.VaccineBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VaccinationScheDuleRepo extends JpaRepository<VaccinationSchedule, Integer> {


    @Query("select u from VaccinationSchedule u where u.vaccineBranch.id = ?1 ORDER BY u.vacciNationTime DESC")
    public List<VaccinationSchedule> findByBranchName(@Param("branchId") Integer branchId);

    @Query("select u from VaccinationSchedule u where u.isVaccinated = ?1 ")
    public List<VaccinationSchedule> findAllVaccinated(@Param("isVaccinated") boolean isVaccinated);


    @Query("select u from VaccinationSchedule u where u.vaccineBranch.id = ?1 and u.vacciNationTime BETWEEN ?2 and ?3 ORDER BY u.vacciNationTime DESC ")
    public List<VaccinationSchedule> chackAvailabilityForVaccination(@Param("branchId") Integer branchId, @Param("vaccinationTime") Long vaccinationStartTime, @Param("vaccinationEndTime") Long vaccinationEndTime);

    @Query("select u from VaccinationSchedule u where u.vaccineBranch.id = ?1 and u.vacciNationTime BETWEEN ?2 and ?3 AND u.isVaccinated = ?4 ORDER BY u.vacciNationTime ASC ")
    public List<VaccinationSchedule> getAllScheduledVaccinationByVaccinationStatus(@Param("branchId") Integer branchId, @Param("vaccinationTime") Long vaccinationStartTime, @Param("vaccinationEndTime") Long vaccinationEndTime, @Param("vaccinationStatus") boolean vaccinationStatus);

    @Query("select u from VaccinationSchedule u where u.vaccineBranch.id = ?1 and u.vaccine.id = ?2 and u.patient.id = ?3  ")
    public List<VaccinationSchedule> scheduledByBranchVaccineAndPatient(@Param("branchId") Integer branchId, @Param("vaccineId") Integer vaccineId, @Param("patientId") Integer patientId);




    @Query("SELECT u.vaccineBranch.branchName as branchName, COUNT(u.id) as total FROM VaccinationSchedule AS u WHERE   u.vacciNationTime BETWEEN ?1 and ?2  GROUP BY u.vaccineBranch.id ")
    List<Object[]> getVaccinationReportDateWise(  @Param("vaccinationTime") Long vaccinationStartTime, @Param("vaccinationEndTime") Long vaccinationEndTime);

    @Query("SELECT u.vaccineBranch.branchName as branchName, COUNT(u.id) as total FROM VaccinationSchedule AS u WHERE   u.vacciNationTime BETWEEN ?1 and ?2 AND u.isVaccinated = ?3   GROUP BY u.vaccineBranch.id ")
    List<Object[]> getVaccinationReportDateWiseAndVccinatonStatus(  @Param("vaccinationTime") Long vaccinationStartTime, @Param("vaccinationEndTime") Long vaccinationEndTime, @Param("isVaccinated") boolean isVaccinated);

    /*@Query("select new com.covid.vaccine.covidvac.model.VaccinationReport("
            + "u.vaccineBranch.branchName as branchName, "
            + "count(u.id) as countProduct ) "
            + "from VaccinationSchedule As u "
           // + "where  u.vacciNationTime BETWEEN ?1 and ?2 "
            + "group by u.vaccineBranch.id")
    public List<VaccinationReport> getVaccinationReportDateWise(  @Param("vaccinationTime") Long vaccinationStartTime, @Param("vaccinationEndTime") Long vaccinationEndTime);*/

   /* @Query("select new com.covid.vaccine.covidvac.model.VaccinationReport("
            + "u.vaccineBranch.branchName as branchName, "
            + "count(u.id) as countProduct ) "
            + "from VaccinationSchedule u "
            //+ "where  u.vacciNationTime BETWEEN ?1 and ?2   "
            + "group by u.vaccineBranch.id")
    public List<VaccinationReport> getVaccinationReportDateWiseAndVccinatonStatus(  @Param("vaccinationTime") Long vaccinationStartTime, @Param("vaccinationEndTime") Long vaccinationEndTime, @Param("isVaccinated") boolean isVaccinated);
*/


}
