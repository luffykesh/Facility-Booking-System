package com.csci5308.g17.timing;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimingTest {

    @Test
    public void getIdTest() {
        Timing timing = new Timing();
        final Integer ID = 100;

        Assertions.assertNull(timing.getId());

        timing.setId(ID);
        Assertions.assertEquals(ID, timing.getId());
    }

    @Test
    public void setIdTest() {
        Timing timing = new Timing();
        final Integer ID = 100;

        timing.setId(ID);
        Assertions.assertEquals(ID, timing.getId());
    }

    @Test
    public void getFacilityIdTest() {
        Timing timing = new Timing();
        final Integer FACILITY_ID = 100;

        Assertions.assertNull(timing.getFacilityId());

        timing.setFacilityId(FACILITY_ID);
        Assertions.assertEquals(FACILITY_ID, timing.getFacilityId());
    }

    @Test
    public void setFacilityIdTest() {
        Timing timing = new Timing();
        final Integer FACILITY_ID = 100;

        timing.setFacilityId(FACILITY_ID);
        Assertions.assertEquals(FACILITY_ID, timing.getFacilityId());
    }

    @Test
    public void getDayTest() {
        Timing timing = new Timing();
        final DayOfWeek DAY = DayOfWeek.MONDAY;

        Assertions.assertNull(timing.getDay());

        timing.setDay(DAY);
        Assertions.assertEquals(DAY, timing.getDay());
    }

    @Test
    public void setDayTest() {
        Timing timing = new Timing();
        final DayOfWeek DAY = DayOfWeek.MONDAY;

        timing.setDay(DAY);
        Assertions.assertEquals(DAY, timing.getDay());
    }

    @Test
    public void getStartTimeTest() {
        Timing timing = new Timing();
        final LocalTime TIME = LocalTime.now();

        Assertions.assertNull(timing.getStartTime());

        timing.setStartTime(TIME);
        Assertions.assertEquals(TIME, timing.getStartTime());
    }

    @Test
    public void setStartTimeTest() {
        Timing timing = new Timing();
        final LocalTime TIME = LocalTime.now();

        timing.setStartTime(TIME);
        Assertions.assertEquals(TIME, timing.getStartTime());
    }

    @Test
    public void getEndTime() {
        Timing timing = new Timing();
        final LocalTime TIME = LocalTime.now();

        Assertions.assertNull(timing.getEndTime());

        timing.setEndTime(TIME);
        Assertions.assertEquals(TIME, timing.getEndTime());
    }

    @Test
    public void setEndTime() {
        Timing timing = new Timing();
        final LocalTime TIME = LocalTime.now();

        timing.setEndTime(TIME);
        Assertions.assertEquals(TIME, timing.getEndTime());
    }

    @Test
    public void getIsBlockingTest() {
        Timing timing = new Timing();
        final Boolean STATUS = false;

        Assertions.assertNull(timing.getIsBlocking());

        timing.setIsBlocking(STATUS);
        Assertions.assertEquals(STATUS, timing.getIsBlocking());
    }

    @Test
    public void setIsBlockingTest() {
        Timing timing = new Timing();
        final Boolean STATUS = false;

        timing.setIsBlocking(STATUS);
        Assertions.assertEquals(STATUS, timing.getIsBlocking());
    }
}
