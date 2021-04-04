package com.csci5308.g17.facility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FacilityTest {

    Facility facility = new Facility();

    @Test
    void getIdTest() {
        int id = 1;
        facility.setId(id);
        Assertions.assertEquals(id, facility.getId());
    }


    @Test
    void setIdTest() {
        int id = 1;
        facility.setId(id);
        Assertions.assertEquals(id, facility.getId());
    }

    @Test
    void getOccupancyTest() {
        int occupancy = 10;
        facility.setOccupancy(occupancy);
        Assertions.assertEquals(occupancy, facility.getOccupancy());
    }

    @Test
    void setOccupancyTest() {
        int occupancy = 10;
        facility.setOccupancy(occupancy);
        Assertions.assertEquals(occupancy, facility.getOccupancy());
    }

    @Test
    void getNameTest() {
        String name = "XYZ";
        facility.setName(name);
        Assertions.assertEquals(name, facility.getName());
    }

    @Test
    void setNameTest() {
        String name = "XYZ";
        facility.setName(name);
        Assertions.assertEquals(name, facility.getName());
    }

    @Test
    void getDescriptionTest() {
        String description = "Standup Comedy";
        facility.setDescription(description);
        Assertions.assertEquals(description, facility.getDescription());
    }

    @Test
    void setDescriptionTest() {
        String description = "Drama";
        facility.setDescription(description);
        Assertions.assertEquals(description, facility.getDescription());
    }

    @Test
    void getLocationTest() {
        String location = "Ground";
        facility.setLocation(location);
        Assertions.assertEquals(location, facility.getLocation());
    }

    @Test
    void setLocationTest() {
        String location = "Theatre";
        facility.setLocation(location);
        Assertions.assertEquals(location, facility.getLocation());
    }

    @Test
    void getManagerIdTest() {
        int manager = 2;
        facility.setManagerId(manager);
        Assertions.assertEquals(manager, facility.getManagerId());
    }

    @Test
    void setManagerIdTest() {
        int manager = 1;
        facility.setManagerId(manager);
        Assertions.assertEquals(manager, facility.getManagerId());
    }

    @Test
    void getTimeSlotTest() {
        Integer timeSlotDuration = 60;
        facility.setTimeSlot(timeSlotDuration);
        Assertions.assertEquals(timeSlotDuration, facility.getTimeSlot());
    }

    @Test
    void setTimeSlotTest() {
        Integer timeSlotDuration = 60;
        facility.setTimeSlot(timeSlotDuration);
        Assertions.assertEquals(timeSlotDuration, facility.getTimeSlot());
    }

    @Test
    void getActiveTest() {
        Boolean active = true;
        facility.setActive(active);
        Assertions.assertTrue(facility.getActive());

    }

    @Test
    void setActiveTest() {
        Boolean active = true;
        facility.setActive(active);
        Assertions.assertTrue(facility.getActive());
    }

    @Test
    void getApprovalRequiredTest() {
        Boolean Approval_required = true;
        facility.setApprovalRequired(Approval_required);
        Assertions.assertTrue(facility.getApprovalRequired());
    }

    @Test
    void setApprovalRequiredTest() {
        Boolean approval_required = true;
        facility.setApprovalRequired(approval_required);
        Assertions.assertTrue(facility.getApprovalRequired());
    }
}