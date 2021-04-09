package com.csci5308.g17.facility;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityService implements IFacilityService {

    private static FacilityService instance;
    private final IFacilityRepository facilityRepo;

    public FacilityService(IFacilityRepository facilityRepo) {
        this.facilityRepo = facilityRepo;
    }

    public static FacilityService getInstance() {
        if (instance == null) {
            instance = new FacilityService(FacilityRepository.getInstance());
        }
        return instance;
    }

    @Override
    public Facility getFacilityById(int id) {
        Facility facility = this.facilityRepo.getFacilityById(id);
        return facility;
    }

    @Override
    public void save(Facility facility) {
        this.facilityRepo.save(facility);
    }

    @Override
    public List<Facility> getAllFacilities() {
        List<Facility> facilityList = this.facilityRepo.getAllFacilities();
        return facilityList;
    }

    @Override
    public void updateFacility(int id, Facility facility) {
        this.facilityRepo.updateFacility(id, facility);
    }

    @Override
    public void deleteFacility(int id) {
        this.facilityRepo.deleteFacility(id);
    }

    @Override
    public List<Facility>getManagerFacilities(Integer managerUserId) {
        return this.facilityRepo.getFacilitiesByManagerId(managerUserId);
    }
}
