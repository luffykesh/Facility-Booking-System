package com.csci5308.g17.facility;

import com.csci5308.g17.user.IUserRepository;
import com.csci5308.g17.user.User;
import com.csci5308.g17.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityService implements IFacilityService {

    private static FacilityService instance;
    private final IFacilityRepository facilityRepo;
    private final IUserRepository userRepo;

    public FacilityService(IFacilityRepository facilityRepo, UserRepository userRepo) {
        this.facilityRepo = facilityRepo;
        this.userRepo = userRepo;
    }

    public static FacilityService getInstance() {
        if (instance == null) {
            instance = new FacilityService(FacilityRepository.getInstance(), UserRepository.getInstance());
        }
        return instance;
    }

    @Override
    public Facility getFacilityById(int id) {
        return this.facilityRepo.getFacilityById(id);
    }

    @Override
    public void save(FormFacility formFacility) {
        User u = userRepo.getUserByEmail(formFacility.getManagerEmail());

        if (formFacility.getActive() == null) {
            formFacility.setActive(false);
        }
        if (formFacility.getApprovalRequired() == null) {
            formFacility.setApprovalRequired(false);
        }
        Facility facility = new Facility();
        facility.setApprovalRequired(formFacility.getApprovalRequired());
        facility.setManagerId(u.getId());
        facility.setActive(formFacility.getActive());
        facility.setOccupancy(formFacility.getOccupancy());
        facility.setTimeSlot(formFacility.getTimeSlot());
        facility.setLocation(formFacility.getLocation());
        facility.setName(formFacility.getName());
        facility.setDescription(formFacility.getDescription());

        this.facilityRepo.save(facility);
    }

    @Override
    public List<Facility> findAll() {
        return this.facilityRepo.findAll();
    }

    @Override
    public void updateFacility(int id, FormFacility formFacility) {

        User u = userRepo.getUserByEmail(formFacility.getManagerEmail());
        Facility facility = new Facility();
        facility.setApprovalRequired(formFacility.getApprovalRequired());
        facility.setManagerId(u.getId());
        facility.setActive(formFacility.getActive());
        facility.setOccupancy(formFacility.getOccupancy());
        facility.setTimeSlot(formFacility.getTimeSlot());
        facility.setLocation(formFacility.getLocation());
        facility.setName(formFacility.getName());
        facility.setDescription(formFacility.getDescription());

        this.facilityRepo.updateFacility(id, facility);

    }
}
