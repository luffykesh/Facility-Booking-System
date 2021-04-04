package com.csci5308.g17.slot;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SlotTest {

    @Test
    public void getIdTest() {
        Slot slot = new Slot();
        final Integer ID = 100;

        Assertions.assertNull(slot.getId());

        slot.setId(ID);
        Assertions.assertEquals(ID, slot.getId());
    }

    @Test
    public void setIdTest() {
        Slot slot = new Slot();
        final Integer ID = 100;

        slot.setId(ID);
        Assertions.assertEquals(ID, slot.getId());
    }

    @Test
    public void getFacilityIdTest() {
        Slot slot = new Slot();
        final Integer FACILITY_ID = 100;

        Assertions.assertNull(slot.getFacilityId());

        slot.setFacilityId(FACILITY_ID);
        Assertions.assertEquals(FACILITY_ID, slot.getFacilityId());
    }

    @Test
    public void setFacilityIdTest() {
        Slot slot = new Slot();
        final Integer FACILITY_ID = 100;

        slot.setFacilityId(FACILITY_ID);
        Assertions.assertEquals(FACILITY_ID, slot.getFacilityId());
    }

    @Test
    public void getTotalSeatsTest() {
        Slot slot = new Slot();
        final Integer TOTAL_SEATS = 10;

        Assertions.assertNull(slot.getTotalSeats());

        slot.setTotalSeats(TOTAL_SEATS);
        Assertions.assertEquals(TOTAL_SEATS, slot.getTotalSeats());
    }

    @Test
    public void setTotalSeatsTest() {
        Slot slot = new Slot();
        final Integer TOTAL_SEATS = 100;

        slot.setTotalSeats(TOTAL_SEATS);
        Assertions.assertEquals(TOTAL_SEATS, slot.getTotalSeats());
    }

    @Test
    public void getAvailableSeatsTest() {
        Slot slot = new Slot();
        final Integer AVAILABLE_SEATS = 10;

        Assertions.assertNull(slot.getAvailableSeats());

        slot.setAvailableSeats(AVAILABLE_SEATS);
        Assertions.assertEquals(AVAILABLE_SEATS, slot.getAvailableSeats());
    }

    @Test
    public void setAvailableSeatsTest() {
        Slot slot = new Slot();
        final Integer AVAILABLE_SEATS = 100;

        slot.setAvailableSeats(AVAILABLE_SEATS);
        Assertions.assertEquals(AVAILABLE_SEATS, slot.getAvailableSeats());
    }

    @Test
    public void getStarTimeTest() {
        Slot slot = new Slot();
        final LocalDateTime TIMESTAMP = LocalDateTime.now();

        Assertions.assertNull(slot.getStartTime());

        slot.setStartTime(TIMESTAMP);
        Assertions.assertEquals(TIMESTAMP, slot.getStartTime());
    }

    @Test
    public void setStarTimeTest() {
        Slot slot = new Slot();
        final LocalDateTime TIMESTAMP = LocalDateTime.now();

        slot.setStartTime(TIMESTAMP);
        Assertions.assertEquals(TIMESTAMP, slot.getStartTime());
    }

    @Test
    public void getEndTimeTest() {
        Slot slot = new Slot();
        final LocalDateTime TIMESTAMP = LocalDateTime.now();

        Assertions.assertNull(slot.getEndTime());

        slot.setEndTime(TIMESTAMP);
        Assertions.assertEquals(TIMESTAMP, slot.getEndTime());
    }

    @Test
    public void setEndTimeTest() {
        Slot slot = new Slot();
        final LocalDateTime TIMESTAMP = LocalDateTime.now();

        slot.setEndTime(TIMESTAMP);
        Assertions.assertEquals(TIMESTAMP, slot.getEndTime());
    }
}
