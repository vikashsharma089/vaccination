package com.covid.vaccine.covidvac.service;

import com.covid.vaccine.covidvac.model.VaccineBranch;
import com.covid.vaccine.covidvac.repository.VaccineBranchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VaccineBranchService {

    @Autowired
    VaccineBranchRepo  vaccineBranchRepo;

    public List<VaccineBranch> getAllBranch(){
        return vaccineBranchRepo.findAll();
    }

    public VaccineBranch save(VaccineBranch vaccineBranch){

        if(vaccineBranch.getModifiedDate() == null){
            vaccineBranch.setModifiedDate(new Date());
        }
        if(vaccineBranch.getCreationDate() == null ){
            vaccineBranch.setCreationDate(new Date());
        }

        return vaccineBranchRepo.save(vaccineBranch);
    }



}
