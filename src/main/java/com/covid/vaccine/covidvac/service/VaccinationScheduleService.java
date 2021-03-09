package com.covid.vaccine.covidvac.service;

import com.covid.vaccine.covidvac.model.Patient;
import com.covid.vaccine.covidvac.model.VaccinationReport;
import com.covid.vaccine.covidvac.model.VaccinationSchedule;
import com.covid.vaccine.covidvac.repository.PatientRepo;
import com.covid.vaccine.covidvac.repository.VaccinationScheDuleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class VaccinationScheduleService {

    @Value("${vaccination.open.hour}")
    private Integer vacciNationOpenHour;

    @Value("${vaccination.close.hour}")
    private Integer vacciNationCloseHour;

    @Autowired
    private VaccinationScheDuleRepo vaccinationScheDuleRepo;

    @Autowired
    private PatientRepo patientRepo;


    public VaccinationSchedule save(VaccinationSchedule vaccinationSchedule ){
        return vaccinationScheDuleRepo.save(vaccinationSchedule);
    }

    public boolean ifAlreadyScheduled(Integer branchId, Integer vaccineId, String emailId){
        List<Patient> allPatientDetails  = patientRepo.findByEmailId(emailId);
        if(allPatientDetails.isEmpty()){
            return  true;
        }else{
           List<VaccinationSchedule> alreadyScheduled =  vaccinationScheDuleRepo.scheduledByBranchVaccineAndPatient(branchId,vaccineId,allPatientDetails.get(0).getId());
           if(alreadyScheduled.isEmpty()){
               return true;
           }else{
               return false;
           }
        }

    }


    public List<VaccinationSchedule> findAllScheduledBranch(){
        return vaccinationScheDuleRepo.findAll();
    }

    public List<VaccinationSchedule> findAllScheduledByVaccinationStatus(boolean vacinationstatus){
        return vaccinationScheDuleRepo.findAllVaccinated(vacinationstatus);
    }


    public List<VaccinationSchedule> getScheduledVaccinationPerBranch(Integer branchId){
        return vaccinationScheDuleRepo.findByBranchName(branchId);
    }



    public  List<VaccinationSchedule>  getAllScheduledVaccinationByVaccinationStatus(Integer branchId, boolean vaccinationStatus){

        Date startAndEndTiming[] = getVaccinationStartEndTiming();
        List<VaccinationSchedule> availibility = vaccinationScheDuleRepo.getAllScheduledVaccinationByVaccinationStatus(branchId,startAndEndTiming[0].getTime() ,startAndEndTiming[1].getTime(), vaccinationStatus);
        return  availibility;
    }


    public  List<VaccinationSchedule>  loadAllScheduledVaccinationForDay(Integer branchId){
        Date startAndEndTiming[] = getVaccinationStartEndTiming();
        List<VaccinationSchedule> availibility = vaccinationScheDuleRepo.chackAvailabilityForVaccination(branchId,startAndEndTiming[0].getTime() ,startAndEndTiming[1].getTime());
       return  availibility;
    }


    public List<Object[]> getAppliedVaccinationToday(){
        Date startAndEndTiming[] = getVaccinationStartEndTiming();
       return  vaccinationScheDuleRepo. getVaccinationReportDateWise(startAndEndTiming[0].getTime() ,startAndEndTiming[1].getTime());

    }

    public List<Object[]> getAppliedVaccinationToday(boolean vacinationStatus){
        Date startAndEndTiming[] = getVaccinationStartEndTiming();
        return  vaccinationScheDuleRepo. getVaccinationReportDateWiseAndVccinatonStatus(startAndEndTiming[0].getTime() ,startAndEndTiming[1].getTime(),vacinationStatus);

    }



    public Date[] getVaccinationStartEndTiming(){
        Date arr[] = new Date[2];
        Calendar vaccinationStartTime = new GregorianCalendar();
        vaccinationStartTime.set(Calendar.HOUR_OF_DAY, vacciNationOpenHour);
        vaccinationStartTime.set(Calendar.MINUTE, 0);
        vaccinationStartTime.set(Calendar.SECOND, 0);
        vaccinationStartTime.set(Calendar.MILLISECOND, 0);

        arr[0]= vaccinationStartTime.getTime();

        Calendar vaccinationSEndTime = new GregorianCalendar();
        vaccinationSEndTime.set(Calendar.HOUR_OF_DAY, vacciNationCloseHour);
        vaccinationSEndTime.set(Calendar.MINUTE, 0);
        vaccinationSEndTime.set(Calendar.SECOND, 0);
        vaccinationSEndTime.set(Calendar.MILLISECOND, 0);

        arr[1]= vaccinationSEndTime.getTime();
        return arr;
    }







}
