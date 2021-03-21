package com.csci5308.g17.facility;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityService implements IFacilityService {

    private IFacilityRepository facilityRepo;

    public FacilityService(IFacilityRepository facilityRepo) {
        this.facilityRepo = facilityRepo;
    }

    @Override
    public Facility getFacilityById(int id) {
        return this.facilityRepo.getFacilityById(id);
    }

    @Override
    public void Save(FormFacility formFacility) {
        this.facilityRepo.Save(formFacility);
    }

    @Override
    public List<Facility> findAll() {
        return this.facilityRepo.findAll();
    }


}
