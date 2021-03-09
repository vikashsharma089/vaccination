package com.covid.vaccine.covidvac.controller;

import com.covid.vaccine.covidvac.model.*;
import com.covid.vaccine.covidvac.repository.VaccineBranchRepo;
import com.covid.vaccine.covidvac.repository.VaccineRepo;
import com.covid.vaccine.covidvac.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.DateUtils;


import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class VaccinationController {

    @Autowired
    private VaccineBranchService vaccineBranchService;

    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private VaccinationScheduleService vaccinationScheduleService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private VaccineStockService vaccineStockService;

    @Value("${vaccination.open.hour}")
    private Integer vacciNationOpenHour;

    @Value("${vaccination.close.hour}")
    private Integer vacciNationCloseHour;

    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;






    @GetMapping("/getAllBranch")
    public ResponseEntity<List<VaccineBranch>> getAllEmployees() {
        List<VaccineBranch> list = vaccineBranchService.getAllBranch();
        return new ResponseEntity<List<VaccineBranch>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path= "/addBranch", consumes = "application/json", produces = "application/json")
    public ResponseEntity<VaccineBranch> getAllVaccine(@RequestBody VaccineBranch vaccineBranch) {
        VaccineBranch obj  = new VaccineBranch();

        try{
            obj = vaccineBranchService.save(vaccineBranch);

        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<VaccineBranch>(obj, new HttpHeaders(), HttpStatus.CONFLICT);
        }
            return new ResponseEntity<VaccineBranch>(obj, new HttpHeaders(), HttpStatus.OK);
   }


    @GetMapping("/getAllVaccine")
    public ResponseEntity<List<Vaccine>> getAllVaccine() {
        List<Vaccine> list = vaccineService.getallVaccine();
        return new ResponseEntity<List<Vaccine>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping(path= "/addVaccine", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Vaccine> getAllVaccine(@RequestBody Vaccine vaccine) {
        Vaccine obj  = new Vaccine();

        try{
            obj = vaccineService.saveVaccine(vaccine);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<Vaccine>(obj, new HttpHeaders(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Vaccine>(obj, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/getAllVaccineStock")
    public ResponseEntity<List<VaccineStock>> getVaccineStock() {
        List<VaccineStock> list = vaccineStockService.findAll();
        Date startAndEndTiming[] = vaccinationScheduleService.getVaccinationStartEndTiming();
        SimpleDateFormat timeformate = new SimpleDateFormat(" hh:mm aa");
        for(VaccineStock stock : list){
            String timing= "";
            LocalDateTime endOFBranchTiming = LocalDateTime.ofInstant(startAndEndTiming[1].toInstant(),ZoneId.systemDefault());
            if(LocalDateTime.now().isAfter(endOFBranchTiming)){
                timing = "Not Available";

            }else{
                List<VaccinationSchedule>  allScheduledList =  vaccinationScheduleService.loadAllScheduledVaccinationForDay(stock.getVaccineBranch().getId());
                if(allScheduledList.isEmpty()){
                    timing   = ""+timeformate.format(startAndEndTiming[0])+"  to "+timeformate.format(startAndEndTiming[1]);
                }else{
                    VaccinationSchedule obj = allScheduledList.get(0);
                    LocalDateTime lastscheduleTime = LocalDateTime.ofInstant(new Date(obj.getVacciNationTime()).toInstant(),ZoneId.systemDefault());
                    if(lastscheduleTime.isBefore(LocalDateTime.now())){
                        timing = ""+timeformate.format(new Date())+"  to "+timeformate.format(startAndEndTiming[1]);
                    }else{
                        timing = ""+timeformate.format(new Date(obj.getVacciNationTime()))+"  to "+timeformate.format(startAndEndTiming[1]);
                    }

                }
            }
            stock.setAvailableTiming(timing);


        }

        return new ResponseEntity<List<VaccineStock>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/getVaccineStock/{id}")
    public ResponseEntity<List<VaccineStock>> getVaccineStock(@PathVariable("id") Integer branchId) {
        List<VaccineStock> list = vaccineStockService.getVaccineStockByBranch(branchId);
        return new ResponseEntity<List<VaccineStock>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path= "/addVaccineStock", consumes = "application/json", produces = "application/json")
    public ResponseEntity<VaccineStock> getAllVaccine(@RequestBody VaccineStock vaccineStock) {

        try{
            Optional<VaccineStock> vaccineStocks = vaccineStockService.findByVaccineAndBranch(vaccineStock.getVaccineBranch().getId(), vaccineStock.getVaccine().getId());
            if(!vaccineStocks.isEmpty()){
                Integer vacqty = vaccineStocks.get().getVaccineQuantity();
                vacqty = vacqty+ (vaccineStock.getVaccineQuantity() == null ? 0: vaccineStock.getVaccineQuantity());
                //vacqty = (vaccineStock.getVaccineQuantity() == null ? 0: vaccineStock.getVaccineQuantity());
                vaccineStock.setVaccineQuantity(vacqty);
                vaccineStock.setId(vaccineStocks.get().getId());
            }
            vaccineStock = vaccineStockService.save(vaccineStock);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<VaccineStock>(vaccineStock, new HttpHeaders(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<VaccineStock>(vaccineStock, new HttpHeaders(), HttpStatus.OK);
    }






    @GetMapping("/getAllScheduledBranch")
    public ResponseEntity<List<VaccinationSchedule>> getAllScheduledBranch() {
        List<VaccinationSchedule> list = vaccinationScheduleService.findAllScheduledByVaccinationStatus(false);
        return new ResponseEntity<List<VaccinationSchedule>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping(path= "/doVaccination", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> doVacciation(@RequestBody VaccinationSchedule vaccineSchedule) {
       try {
           List<VaccinationSchedule> allScheduleByBranch =  vaccinationScheduleService.getAllScheduledVaccinationByVaccinationStatus(vaccineSchedule.getVaccineBranch().getId(),false);
           VaccinationSchedule obj = allScheduleByBranch.get(0);
           obj.setVaccinated(true);
           vaccinationScheduleService.save(obj);
           return new ResponseEntity<Object>(new Rsponse("Vaccinated Success fully"), new HttpHeaders(), HttpStatus.OK);

       }catch (Exception e){
           return new ResponseEntity<Object>(new Rsponse(e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }


    @PostMapping(path= "/scheduleVaccination", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> getAllVaccine(@RequestBody VaccinationSchedule vaccineSchedule) {

        try{

        Integer vaccineId  = vaccineSchedule.getVaccine().getId();
        Integer branchId  = vaccineSchedule.getVaccineBranch().getId();

        if(!ifVaccineAvailable(branchId,vaccineId)){
            return new ResponseEntity<Object>(new Rsponse("Vaccine Does not Available"), new HttpHeaders(), HttpStatus.OK);
        }

       if(!vaccinationScheduleService.ifAlreadyScheduled(vaccineSchedule.getVaccineBranch().getId(),vaccineSchedule.getVaccine().getId(), vaccineSchedule.getPatient().getEmailId())){
           return new ResponseEntity<Object>(new Rsponse("Already Scheduled for this patient"), new HttpHeaders(), HttpStatus.OK);
       }

       LocalDateTime scheduledTime = LocalDateTime.now();
       Date startAndEndTiming[] = vaccinationScheduleService.getVaccinationStartEndTiming();

       LocalDateTime endOFBranchTiming = LocalDateTime.ofInstant(startAndEndTiming[1].toInstant(),ZoneId.systemDefault());
       if(LocalDateTime.now().isAfter(endOFBranchTiming)){
           return new ResponseEntity<Object>(new Rsponse("Time slot not available for today"), new HttpHeaders(), HttpStatus.OK);
        }


        List<VaccinationSchedule>  allSceduledVaccination  =    vaccinationScheduleService.loadAllScheduledVaccinationForDay(vaccineSchedule.getVaccineBranch().getId() );
        vaccineSchedule.setScheDuledDate(new Date());
        if(allSceduledVaccination.isEmpty()){
            LocalDateTime currentDateTime = LocalDateTime.now();
            currentDateTime =currentDateTime.plusMinutes(15);
            scheduledTime = currentDateTime;
            LocalDateTime startofVaccinationTiming = LocalDateTime.ofInstant(startAndEndTiming[0].toInstant(),ZoneId.systemDefault());
            if(scheduledTime.isBefore(startofVaccinationTiming)){
                scheduledTime =startofVaccinationTiming;
                currentDateTime = scheduledTime;
            }

            Date vaccinationTime  = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
            vaccineSchedule.setVacciNationTime(vaccinationTime);
            Patient  patient_obj = patientService.savePatientRecord(vaccineSchedule.getPatient());
            vaccineSchedule.setPatient(patient_obj);
            vaccineSchedule = vaccinationScheduleService.save(vaccineSchedule);
        }else{
            VaccinationSchedule obj  = allSceduledVaccination.get(0);
            LocalDateTime latestScheduleTime = LocalDateTime.ofInstant(new Date(obj.getVacciNationTime()).toInstant(),     ZoneId.systemDefault());
            latestScheduleTime =latestScheduleTime.plusMinutes(15);
            if(latestScheduleTime.isBefore(LocalDateTime.now())){
                latestScheduleTime= LocalDateTime.now();
                latestScheduleTime = latestScheduleTime.plusMinutes(15);
            }
             if(latestScheduleTime.isAfter(endOFBranchTiming)){
                 return new ResponseEntity<Object>(new Rsponse("No Time slot available for this branch"), new HttpHeaders(), HttpStatus.OK);
             }

            LocalDateTime startofVaccinationTiming = LocalDateTime.ofInstant(startAndEndTiming[0].toInstant(),ZoneId.systemDefault());
            if(latestScheduleTime.isBefore(startofVaccinationTiming)){
                latestScheduleTime =startofVaccinationTiming;
            }
            vaccineSchedule.setScheDuledDate(new Date());
            Date vaccinationTime  = Date.from(latestScheduleTime.atZone(ZoneId.systemDefault()).toInstant());
            vaccineSchedule.setVacciNationTime(vaccinationTime);

            Patient  patient_obj = patientService.savePatientRecord(vaccineSchedule.getPatient());
            vaccineSchedule.setPatient(patient_obj);
            vaccineSchedule = vaccinationScheduleService.save(vaccineSchedule);
        }

        Optional<VaccineStock>  stock = vaccineStockService.findByVaccineAndBranch(branchId, vaccineId);
        VaccineStock maintainSto   = stock.get();
        Integer vacQty = maintainSto.getVaccineQuantity();
        vacQty = vacQty-1;
        maintainSto.setVaccineQuantity(vacQty);
        vaccineStockService.save(maintainSto);

        String messageText = "Deary "+vaccineSchedule.getPatient().getPatientName()+" your vaccination has been sheduled for "+vaccineSchedule.getVaccineBranch().getBranchName()+" at "+vaccineSchedule.getVaccineTime();
        String emailTo = vaccineSchedule.getPatient().getEmailId();
            new Thread(() -> {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from);
                message.setTo(emailTo);
                message.setSubject("Scheduled Vaccination");
                message.setText(messageText);
                mailSender.send(message);
            }).start();

        return new ResponseEntity<Object>(new Rsponse("Vaccination Has been Scheduled successfully! Please check email"), new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(new Rsponse(e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    private boolean ifVaccineAvailable(Integer branchId, Integer vaccineId){
        Optional<VaccineStock> stock =   vaccineStockService.findByVaccineAndBranch(branchId, vaccineId);
        if(stock.isEmpty()){
            return false;
        }else{
            VaccineStock vaccine =  stock.get();
            if(vaccine.getVaccineQuantity()<=0){
                return false;
            }else {
                return true;
            }
        }
    }



    @GetMapping("/getVaccinationReport")
    public ResponseEntity<Object> getVaccinationReport() {
        Map<String,VaccinationReport>  reports = new HashMap<>();
        List<Object[]>  obj =  vaccinationScheduleService.getAppliedVaccinationToday();
        String abc =(String) obj.get(0)[0];
        for(Object arrayObj[] : obj){
          String key  = (String)  arrayObj[0];
          Long value = (Long)  arrayObj[1];
            VaccinationReport report = new VaccinationReport();
            report.setTotalVaccination(value);
            reports.put(key,report) ;
        }

        List<Object[]>  obj2 =  vaccinationScheduleService.getAppliedVaccinationToday(true);
        for(Object arrayObj[] : obj2) {
            String key = (String) arrayObj[0];
            Long value = (Long) arrayObj[1];
            VaccinationReport report = reports.get(key);
            report.setConfirmedVaccination(value);
            report.setRemainingVaccination(report.getTotalVaccination()-report.getConfirmedVaccination());
            reports.put(key,report);
        }
        return new ResponseEntity<Object>(reports, new HttpHeaders(), HttpStatus.OK);
    }


}
