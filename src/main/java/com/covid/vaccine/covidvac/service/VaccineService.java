package com.covid.vaccine.covidvac.service;

import com.covid.vaccine.covidvac.model.Vaccine;
import com.covid.vaccine.covidvac.model.VaccineBranch;
import com.covid.vaccine.covidvac.repository.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VaccineService {

    @Autowired
    private  VaccineRepo vaccineRepo;

    public List<Vaccine> getallVaccine(){
        return vaccineRepo.findAll();
    }

    public Vaccine saveVaccine(Vaccine vaccine){
       vaccine.setModifiedDate(new Date());

       if(vaccine.getCreationDate() == null){
            vaccine.setCreationDate(new Date());
        }

        return vaccineRepo.save(vaccine);

    }
}
