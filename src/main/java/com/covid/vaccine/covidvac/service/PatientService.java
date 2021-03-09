package com.covid.vaccine.covidvac.service;

import com.covid.vaccine.covidvac.model.Patient;
import com.covid.vaccine.covidvac.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepo patientRepo;

    public List<Patient> getAllPatient(){
        return patientRepo.findAll();
    }

    public  Patient savePatientRecord(Patient patient){
        return patientRepo.save(patient);
    }

    public Optional<Patient> findById(Integer id){
        return   patientRepo.findById(id);
    }


}
