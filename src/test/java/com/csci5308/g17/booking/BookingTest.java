package com.csci5308.g17.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BookingTest {

    @Test
    public void getIdTest() {
        Booking booking = new Booking();
        final Integer ID = 10;
        Assertions.assertNull(booking.getId());
        booking.setId(ID);
        Assertions.assertEquals(booking.getId(),ID);
    }

    @Test
    public void setIdTest() {
        Booking booking = new Booking();
        final Integer ID = 10;

        booking.setId(ID);
        Assertions.assertEquals(booking.getId(),ID);
    }

    @Test
    public void getFacilityIdTest() {
        Booking booking = new Booking();
        final Integer FACILITY_ID = 10;
        Assertions.assertNull(booking.getFacilityId());
        booking.setFacilityId(FACILITY_ID);
        Assertions.assertEquals(booking.getFacilityId(),FACILITY_ID);
    }

    @Test
    public void setFacilityIdTest() {
        Booking booking = new Booking();
        final Integer FACILITY_ID = 10;

        booking.setFacilityId(FACILITY_ID);
        Assertions.assertEquals(booking.getFacilityId(),FACILITY_ID);
    }

    @Test
    public void getUserIdTest() {
        Booking booking = new Booking();
        final Integer USER_ID = 10;
        Assertions.assertNull(booking.getUserId());
        booking.setUserId(USER_ID);
        Assertions.assertEquals(booking.getUserId(),USER_ID);
    }

    @Test
    public void setUserIdTest() {
        Booking booking = new Booking();
        final Integer USER_ID = 10;

        booking.setUserId(USER_ID);
        Assertions.assertEquals(booking.getUserId(),USER_ID);
    }

    @Test
    public void getSlotIdTest() {
        Booking booking = new Booking();
        final Integer SLOT_ID = 10;
        Assertions.assertNull(booking.getSlotId());
        booking.setSlotId(SLOT_ID);
        Assertions.assertEquals(booking.getSlotId(),SLOT_ID);
    }

    @Test
    public void setSlotIdTest() {
        Booking booking = new Booking();
        final Integer SLOT_ID = 10;

        booking.setSlotId(SLOT_ID);
        Assertions.assertEquals(booking.getSlotId(),SLOT_ID);
    }

    @Test
    public void getStartTimeTest() {
        Booking booking = new Booking();
        final LocalDateTime TIMESTAMP = LocalDateTime.now();
        Assertions.assertNull(booking.getStartTime());
        booking.setStartTime(TIMESTAMP);
        Assertions.assertEquals(TIMESTAMP, booking.getStartTime());
    }

    @Test
    public void setStartTimeTest() {
        Booking booking = new Booking();
        final LocalDateTime TIMESTAMP = LocalDateTime.now();

        booking.setStartTime(TIMESTAMP);
        Assertions.assertEquals(TIMESTAMP, booking.getStartTime());
    }

    @Test
    public void getEndTimeTest() {
        Booking booking = new Booking();
        final LocalDateTime TIMESTAMP = LocalDateTime.now();
        Assertions.assertNull(booking.getEndTime());
        booking.setEndTime(TIMESTAMP);
        Assertions.assertEquals(TIMESTAMP, booking.getEndTime());
    }

    @Test
    public void setEndTimeTest() {
        Booking booking = new Booking();
        final LocalDateTime TIMESTAMP = LocalDateTime.now();

        booking.setEndTime(TIMESTAMP);
        Assertions.assertEquals(TIMESTAMP, booking.getEndTime());
    }

    @Test
    public void getStatusTest() {
        Booking booking = new Booking();

        final BookingStatus STATUS = BookingStatus.CONFIRMED;
        Assertions.assertNull(booking.getStatus());
        booking.setStatus(STATUS);
        Assertions.assertEquals(STATUS, booking.getStatus());
    }

    @Test
    public void setStatusTest() {
        Booking booking = new Booking();

        final BookingStatus STATUS = BookingStatus.CONFIRMED;
        booking.setStatus(STATUS);
        Assertions.assertEquals(STATUS, booking.getStatus());
    }
}
