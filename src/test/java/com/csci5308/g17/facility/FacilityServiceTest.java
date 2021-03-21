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

        Facility dbUser = new Facility();

        dbUser.setId(ID);
        dbUser.setName("name");
        dbUser.setDescription("Description");
        dbUser.setLocation("Location");
        dbUser.setActive(true);
        dbUser.setApprovalRequired(true);
        dbUser.setTimeSlot("09:00:00");
        dbUser.setOccupancy(20);

        Mockito.when(facilityRepository.getFacilityById(ID)).thenReturn(dbUser);

        Facility returnedFacility = facilityService.getFacilityById(ID);
        Assertions.assertNotNull(returnedFacility);
        Assertions.assertTrue(returnedFacility.equals(dbUser));
    }

    @Test
    void saveTest() {
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository,userRepository);

        Facility dbUser = new Facility();

        dbUser.setName("name");
        dbUser.setDescription("Description");
        dbUser.setLocation("Location");
        dbUser.setActive(true);
        dbUser.setApprovalRequired(true);
        dbUser.setTimeSlot("09:00:00");
        dbUser.setOccupancy(20);

        facilityRepository.save(dbUser);
        Assertions.assertNotNull(dbUser);
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

        Facility dbUser = new Facility();
        int id = 1;
        dbUser.setName("name");
        dbUser.setDescription("Description");
        dbUser.setLocation("Location");
        dbUser.setActive(true);
        dbUser.setApprovalRequired(true);
        dbUser.setTimeSlot("09:00:00");
        dbUser.setOccupancy(20);

        facilityRepository.updateFacility(id,dbUser);
        Assertions.assertNotNull(dbUser);
    }
}