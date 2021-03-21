package com.csci5308.g17.facility;

import com.csci5308.g17.user.User;
import com.csci5308.g17.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityService implements IFacilityService {

    private final IFacilityRepository facilityRepo;
    private final UserRepository userRepo;

    public FacilityService(IFacilityRepository facilityRepo, UserRepository userRepo) {
        this.facilityRepo = facilityRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Facility getFacilityById(int id) {
        return this.facilityRepo.getFacilityById(id);
    }

    @Override
    public void save(FormFacility formFacility) {
        System.out.println(formFacility.managerEmail);
        User u = userRepo.getUserByEmail(formFacility.managerEmail);
        System.out.println(u.getId());

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
    public void updateFacility(int id, FormFacility formFacility){

        User u = userRepo.getUserByEmail(formFacility.managerEmail);
        Facility facility = new Facility();
        facility.setApprovalRequired(formFacility.getApprovalRequired());
        facility.setManagerId(u.getId());
        facility.setActive(formFacility.getActive());
        facility.setOccupancy(formFacility.getOccupancy());
        facility.setTimeSlot(formFacility.getTimeSlot());
        facility.setLocation(formFacility.getLocation());
        facility.setName(formFacility.getName());
        facility.setDescription(formFacility.getDescription());

        this.facilityRepo.updateFacility(id,facility);

    }
}
