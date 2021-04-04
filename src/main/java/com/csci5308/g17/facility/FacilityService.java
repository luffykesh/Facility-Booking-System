package com.csci5308.g17.facility;

import com.csci5308.g17.user.IUserRepository;
import com.csci5308.g17.user.User;
import com.csci5308.g17.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityService implements IFacilityService {

    private static FacilityService instance;
    private final IFacilityRepository facilityRepo;
    private final IUserRepository userRepo;

    public FacilityService(IFacilityRepository facilityRepo, IUserRepository userRepo) {
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
    public FormFacility getFacilityById(int id) {
        Facility facility = this.facilityRepo.getFacilityById(id);
        FormFacility formFacility = new FormFacility();

        User u = userRepo.getUserById(facility.getManagerId());
        formFacility.setApprovalRequired(facility.getApprovalRequired());
        formFacility.setManagerEmail(u.getEmail());
        formFacility.setActive(facility.getActive());
        formFacility.setOccupancy(facility.getOccupancy());
        formFacility.setTimeSlot(facility.getTimeSlot());
        formFacility.setLocation(facility.getLocation());
        formFacility.setName(facility.getName());
        formFacility.setDescription(facility.getDescription());
        formFacility.setId(facility.getId());

        return formFacility;
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
    public List<FormFacility> findAll() {

        List<Facility> facilityList= this.facilityRepo.findAll();
        List<FormFacility> formFacilityList = new ArrayList<FormFacility>();

        for(Facility f1 : facilityList) {
            FormFacility formFacility = new FormFacility();
            User u = userRepo.getUserById(f1.getManagerId());
            formFacility.setApprovalRequired(f1.getApprovalRequired());
            formFacility.setManagerEmail(u.getEmail());
            formFacility.setActive(f1.getActive());
            formFacility.setOccupancy(f1.getOccupancy());
            formFacility.setTimeSlot(f1.getTimeSlot());
            formFacility.setLocation(f1.getLocation());
            formFacility.setName(f1.getName());
            formFacility.setDescription(f1.getDescription());
            formFacility.setId(f1.getId());
            formFacilityList.add(formFacility);
        }

        return formFacilityList;
    }

    @Override
    public void updateFacility(int id, FormFacility formFacility) {

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

        this.facilityRepo.updateFacility(id, facility);

    }

    @Override
    public void deleteFacility(int id) {
        this.facilityRepo.deleteFacility(id);
    }
}
