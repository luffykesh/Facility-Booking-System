package com.csci5308.g17.facility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class FacilityServiceTest {

    @Test
    void getFacilityByIdTest() {
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository);

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
        FacilityService facilityService = new FacilityService(facilityRepository);

        Facility facility = new Facility();

        facility.setName("name");
        facility.setDescription("Description");
        facility.setLocation("Location");
        facility.setActive(true);
        facility.setApprovalRequired(true);
        facility.setTimeSlot(30);
        facility.setOccupancy(20);

        facilityService.save(facility);
        Assertions.assertNotNull(facility);
    }

    @Test
    void findAllTest(){
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository);

        Facility fac = new Facility();
        List<Facility> facility = new ArrayList<Facility>();
        facility.add(fac);
        Mockito.when(facilityService.findAll()).thenReturn(facility);
        Mockito.when(facilityRepository.findAll()).thenReturn(facility);
        List<Facility> returnedFacility = facilityRepository.findAll();
        Assertions.assertTrue(returnedFacility.equals(facility));
    }

    @Test
    void updateFacilityTest(){
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository);

        Facility facility = new Facility();
        int id = 1;
        facility.setName("name");
        facility.setDescription("Description");
        facility.setLocation("Location");
        facility.setActive(true);
        facility.setApprovalRequired(true);
        facility.setTimeSlot(30);
        facility.setOccupancy(20);

        facilityService.updateFacility(id,facility);
        Assertions.assertNotNull(facility);
    }

    @Test
    void deleteFacilityTest() {
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository);

        final int ID =1;
        Mockito.doNothing().when(facilityRepository).deleteFacility(ID);
        facilityService.deleteFacility(ID);
        Mockito.verify(facilityRepository, Mockito.times(1)).deleteFacility(ID);
    }
    @Test
    void getFacilityByManagerIdTest() {
        FacilityRepository facilityRepository = Mockito.mock(FacilityRepository.class);
        FacilityService facilityService = new FacilityService(facilityRepository);
        List<Facility> facilityList = new ArrayList<Facility>();

        final int MANAGER_ID=10;

        Facility facility = new Facility();
        facility.setId(1);
        facility.setName("name");
        facility.setDescription("Description");
        facility.setLocation("Location");
        facility.setActive(true);
        facility.setApprovalRequired(true);
        facility.setTimeSlot(30);
        facility.setOccupancy(20);
        facility.setManagerId(MANAGER_ID);


        Mockito.when(facilityRepository.getFacilitiesByManagerId(MANAGER_ID)).thenReturn(facilityList);
        List<Facility> returnedFacility = facilityService.getManagerFacilities(MANAGER_ID);
        Assertions.assertNotNull(returnedFacility);
        Assertions.assertTrue(returnedFacility.equals(facilityList));
    }
}