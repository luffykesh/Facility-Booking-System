package com.csci5308.g17.facility;

import com.csci5308.g17.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class FacilityServiceTest {

    @Test
    void getFacilityById() {
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository,userRepository);

        final int ID=10;

        Facility facility = new Facility();

        facility.setId(ID);
        facility.setName("name");
        facility.setDescription("Description");
        facility.setLocation("Location");
        facility.setActive(true);
        facility.setApprovalRequired(true);
        facility.setTimeSlot(30);
        facility.setOccupancy(20);

        Mockito.when(facilityRepository.getFacilityById(ID)).thenReturn(facility);

        Facility returnedFacility = facilityService.getFacilityById(ID);
        Assertions.assertNotNull(returnedFacility);
        Assertions.assertTrue(returnedFacility.equals(facility));
    }

    @Test
    void saveTest() {
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository,userRepository);

        Facility facility = new Facility();

        facility.setName("name");
        facility.setDescription("Description");
        facility.setLocation("Location");
        facility.setActive(true);
        facility.setApprovalRequired(true);
        facility.setTimeSlot(30);
        facility.setOccupancy(20);

        facilityRepository.save(facility);
        Assertions.assertNotNull(facility);
    }

    @Test
    void findAllTest(){
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository,userRepository);

        Facility fac = new Facility();
        List<Facility> facility = new ArrayList<>();

        facility.add(fac);
        Mockito.when(facilityRepository.findAll()).thenReturn(facility);
        List<Facility> returnedFacility = facilityService.findAll();
        Assertions.assertTrue(returnedFacility.equals(facility));
    }

    @Test
    void updateFacility(){
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository,userRepository);

        Facility facility = new Facility();
        int id = 1;
        facility.setName("name");
        facility.setDescription("Description");
        facility.setLocation("Location");
        facility.setActive(true);
        facility.setApprovalRequired(true);
        facility.setTimeSlot(30);
        facility.setOccupancy(20);

        facilityRepository.updateFacility(id,facility);
        Assertions.assertNotNull(facility);
    }
}