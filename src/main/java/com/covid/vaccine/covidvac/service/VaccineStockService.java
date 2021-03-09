package com.covid.vaccine.covidvac.service;

import com.covid.vaccine.covidvac.model.VaccineStock;
import com.covid.vaccine.covidvac.repository.VaccineStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccineStockService {

    @Autowired
    private  VaccineStockRepo vaccineStockRepo;

    public  VaccineStock save(VaccineStock obj){
        return  vaccineStockRepo.save(obj);
    }

    public  List<VaccineStock> getVaccineStockByBranch(Integer branchId){
        return vaccineStockRepo.getVaccinesByBranch(branchId);
    }

    public  List<VaccineStock> findAll(){
        return vaccineStockRepo.findAll();
    }
    public Optional<VaccineStock> findByVaccineAndBranch(Integer branchId, Integer vaccineId){
        List<VaccineStock> vaccineStockList =  vaccineStockRepo.findByVaccineAndBranch(branchId, vaccineId);
        if (vaccineStockList.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(vaccineStockList.get(0));
        }
    }



}
