package com.csci5308.g17.facility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class FacilityServiceTest {

    @Test
    void getFacilityById() {
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository);
        final int ID=10;

        Facility dbUser = new Facility();

        dbUser.setId(ID);
        dbUser.setName("name");
        dbUser.setDescription("Description");
        dbUser.setLocation("Location");
        dbUser.setActive(true);
        dbUser.setApproval_required(true);
        dbUser.setTime_slot("09:00:00");
        dbUser.setOccupancy(20);

        Mockito.when(facilityRepository.getFacilityById(ID)).thenReturn(dbUser);

        Facility returnedFacility = facilityService.getFacilityById(ID);
        Assertions.assertNotNull(returnedFacility);
        Assertions.assertTrue(returnedFacility.equals(dbUser));


    }

    @Test
    void saveTest() {
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository);

        FormFacility dbUser = new FormFacility();

        dbUser.setName("name");
        dbUser.setDescription("Description");
        dbUser.setLocation("Location");
        dbUser.setActive(true);
        dbUser.setApproval_required(true);
        dbUser.setTime_slot("09:00:00");
        dbUser.setOccupancy(20);

        facilityRepository.Save(dbUser);
        Assertions.assertNotNull(dbUser);
    }

    @Test
    void findAllTest(){
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository);
        Facility fac = new Facility();
        List<Facility> facility = new ArrayList<>();

        facility.add(fac);
        Mockito.when(facilityRepository.findAll()).thenReturn(facility);
        List<Facility> returnedFacility = facilityService.findAll();
        Assertions.assertTrue(returnedFacility.equals(facility));




    }
}