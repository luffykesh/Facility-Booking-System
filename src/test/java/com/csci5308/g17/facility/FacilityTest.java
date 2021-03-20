package com.csci5308.g17.facility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacilityTest {

    Facility facility = new Facility();
    @Test
    void getIdTest() {
        int id = 1;
        facility.setId(id);
        Assertions.assertEquals(id,facility.getId());
    }

    @Test
    void setIdTest() {
        int id = 1;
        facility.setId(id);
        Assertions.assertEquals(id,facility.getId());
    }

    @Test
    void getOccupancyTest() {
        int occupancy = 10;
        facility.setOccupancy(occupancy);
        Assertions.assertEquals(occupancy,facility.getOccupancy());
    }

    @Test
    void setOccupancyTest() {
        int occupancy = 10;
        facility.setOccupancy(occupancy);
        Assertions.assertEquals(occupancy,facility.getOccupancy());
    }

    @Test
    void getNameTest() {
        String name = "XYZ";
        facility.setName(name);
        Assertions.assertEquals(name,facility.getName());
    }

    @Test
    void setNameTest() {
        String name = "XYZ";
        facility.setName(name);
        Assertions.assertEquals(name,facility.getName());
    }

    @Test
    void getDescriptionTest() {
        String description = "Standup Comedy";
        facility.setDescription(description);
        Assertions.assertEquals(description,facility.getDescription());
    }

    @Test
    void setDescriptionTest() {
        String description = "Drama";
        facility.setDescription(description);
        Assertions.assertEquals(description,facility.getDescription());
    }

    @Test
    void getLocationTest() {
        String location = "Ground";
        facility.setLocation(location);
        Assertions.assertEquals(location,facility.getLocation());
    }

    @Test
    void setLocationTest() {
        String location = "Theatre";
        facility.setLocation(location);
        Assertions.assertEquals(location,facility.getLocation());
    }

    @Test
    void getManagerTest() {
        String manager = "vv@dal.ca";
        facility.setManager(manager);
        Assertions.assertEquals(manager,facility.getManager());
    }

    @Test
    void setManagerTest() {
        String manager = "XYZ@dal.ca";
        facility.setManager(manager);
        Assertions.assertEquals(manager,facility.getManager());
    }

    @Test
    void getTime_slotTest() {
        String time_slot = "09:00:00";
        facility.setTime_slot(time_slot);
        Assertions.assertEquals(time_slot,facility.getTime_slot());
    }

    @Test
    void setTime_slotTest() {
        String time_slot = "09:00:00";
        facility.setTime_slot(time_slot);
        Assertions.assertEquals(time_slot,facility.getTime_slot());
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
    void getApproval_requiredTest() {
        Boolean Approval_required = true;
        facility.setApproval_required(Approval_required);
        Assertions.assertTrue(facility.getApproval_required());
    }

    @Test
    void setApproval_requiredTest() {
        Boolean approval_required = true;
        facility.setApproval_required(approval_required);
        Assertions.assertTrue(facility.getApproval_required());
    }
}