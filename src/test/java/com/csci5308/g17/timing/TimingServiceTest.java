package com.csci5308.g17.timing;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import com.csci5308.g17.facility.Facility;
import com.csci5308.g17.facility.FacilityService;
import com.csci5308.g17.slot.SlotService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TimingServiceTest {

    @Test
    public void isOverlappingTest() {
        TimingRepository timingRepo = Mockito.mock(TimingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        TimingService timingService = new TimingService(timingRepo, slotService, facilityService);

        Timing TIMING1 = new Timing();
        Timing timing2 = new Timing();

        TIMING1.setDay(DayOfWeek.MONDAY);
        TIMING1.setStartTime(LocalTime.of(10,0));
        TIMING1.setEndTime(LocalTime.of(20,0));

        // true assertions
        timing2.setDay(DayOfWeek.MONDAY);
        timing2.setStartTime(LocalTime.of(10,0));
        timing2.setEndTime(LocalTime.of(20,0));
        Assertions.assertTrue(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertTrue(timingService.isOverlapping(timing2, TIMING1));

        timing2.setDay(DayOfWeek.MONDAY);
        timing2.setStartTime(LocalTime.of(9,0));
        timing2.setEndTime(LocalTime.of(10,20));
        Assertions.assertTrue(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertTrue(timingService.isOverlapping(timing2, TIMING1));

        timing2.setDay(DayOfWeek.MONDAY);
        timing2.setStartTime(LocalTime.of(10,0));
        timing2.setEndTime(LocalTime.of(12,0));
        Assertions.assertTrue(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertTrue(timingService.isOverlapping(timing2, TIMING1));

        timing2.setDay(DayOfWeek.MONDAY);
        timing2.setStartTime(LocalTime.of(15,0));
        timing2.setEndTime(LocalTime.of(17,0));
        Assertions.assertTrue(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertTrue(timingService.isOverlapping(timing2, TIMING1));

        timing2.setDay(DayOfWeek.MONDAY);
        timing2.setStartTime(LocalTime.of(18,0));
        timing2.setEndTime(LocalTime.of(20,0));
        Assertions.assertTrue(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertTrue(timingService.isOverlapping(timing2, TIMING1));

        timing2.setDay(DayOfWeek.MONDAY);
        timing2.setStartTime(LocalTime.of(18,0));
        timing2.setEndTime(LocalTime.of(21,0));
        Assertions.assertTrue(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertTrue(timingService.isOverlapping(timing2, TIMING1));

        // false assertions
        timing2.setDay(DayOfWeek.MONDAY);
        timing2.setStartTime(LocalTime.of(9,0));
        timing2.setEndTime(LocalTime.of(10,0));
        Assertions.assertFalse(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertFalse(timingService.isOverlapping(timing2, TIMING1));

        timing2.setDay(DayOfWeek.MONDAY);
        timing2.setStartTime(LocalTime.of(20,0));
        timing2.setEndTime(LocalTime.of(21,0));
        Assertions.assertFalse(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertFalse(timingService.isOverlapping(timing2, TIMING1));

        timing2.setDay(DayOfWeek.MONDAY);
        timing2.setStartTime(LocalTime.of(22,0));
        timing2.setEndTime(LocalTime.of(23,0));
        Assertions.assertFalse(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertFalse(timingService.isOverlapping(timing2, TIMING1));

        timing2.setDay(DayOfWeek.TUESDAY);
        timing2.setStartTime(TIMING1.getStartTime());
        timing2.setEndTime(TIMING1.getEndTime());
        Assertions.assertFalse(timingService.isOverlapping(TIMING1, timing2));
        Assertions.assertFalse(timingService.isOverlapping(timing2, TIMING1));
    }

    @Test
    public void getTimingTest() {
        TimingRepository timingRepo = Mockito.mock(TimingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        TimingService timingService = new TimingService(timingRepo, slotService, facilityService);

        Integer TIMING_ID = 100;

        Timing timing = new Timing();
        timing.setDay(DayOfWeek.MONDAY);
        timing.setStartTime(LocalTime.of(10,0));
        timing.setEndTime(LocalTime.of(20,0));
        timing.setFacilityId(TIMING_ID);
        timing.setIsBlocking(false);

        Mockito.when(timingRepo.getTimingById(TIMING_ID)).thenReturn(timing);
        Assertions.assertEquals(timingService.getTiming(TIMING_ID), timing);
    }

    @Test
    public void getFacilityTimingsTest() {
        TimingRepository timingRepo = Mockito.mock(TimingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        TimingService timingService = new TimingService(timingRepo, slotService, facilityService);

        Integer FACILITY_ID = 100;

        Timing timing = new Timing();
        timing.setDay(DayOfWeek.MONDAY);
        timing.setStartTime(LocalTime.of(10,0));
        timing.setEndTime(LocalTime.of(20,0));
        timing.setFacilityId(FACILITY_ID);
        timing.setIsBlocking(false);

        List<Timing> timings = Collections.singletonList(timing);

        Mockito.when(timingRepo.getTimingsbyFacilityId(FACILITY_ID)).thenReturn(timings);
        Assertions.assertTrue(timingService.getFacilityTimings(FACILITY_ID) == timings);
    }

    @Test
    public void deleteTimingTest() {
        TimingRepository timingRepo = Mockito.mock(TimingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        TimingService timingService = new TimingService(timingRepo, slotService, facilityService);
        Integer TIMING_ID = 100;

        Timing timing = new Timing();
        timing.setId(TIMING_ID);
        timing.setDay(DayOfWeek.MONDAY);
        timing.setStartTime(LocalTime.of(10,0));
        timing.setEndTime(LocalTime.of(20,0));
        timing.setFacilityId(100);
        timing.setIsBlocking(false);

        Mockito.doNothing().when(timingRepo).deleteTimingById(TIMING_ID);
        timingService.deleteTiming(TIMING_ID);
        Mockito.verify(timingRepo, Mockito.times(1)).deleteTimingById(TIMING_ID);
    }

    @Test
    public void addTimingTest() {
        TimingRepository timingRepo = Mockito.mock(TimingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        TimingService timingService = new TimingService(timingRepo, slotService, facilityService);

        Integer FACILITY_ID = 100;
        Facility facility = new Facility();
        facility.setId(FACILITY_ID);
        facility.setApprovalRequired(false);
        facility.setDescription("description");
        facility.setLocation("location");
        facility.setManagerId(123);
        facility.setName("name");
        facility.setOccupancy(100);
        facility.setTimeSlot(60);

        Timing existingTiming = new Timing();
        existingTiming.setDay(DayOfWeek.MONDAY);
        existingTiming.setStartTime(LocalTime.of(10,0));
        existingTiming.setEndTime(LocalTime.of(20,0));
        existingTiming.setFacilityId(facility.getId());
        existingTiming.setIsBlocking(false);

        List<Timing> existingTimings = Collections.singletonList(existingTiming);

        Timing newTiming = new Timing();
        newTiming.setDay(DayOfWeek.MONDAY);
        newTiming.setFacilityId(facility.getId());
        newTiming.setStartTime(LocalTime.of(9,0));
        newTiming.setEndTime(LocalTime.of(10,0));
        newTiming.setFacilityId(facility.getId());
        newTiming.setIsBlocking(false);

        Mockito.when(facilityService.getFacilityById(FACILITY_ID)).thenReturn(facility);
        Mockito.when(
            slotService.createSlotsForTiming(
                Mockito.any(Timing.class), Mockito.eq(facility.getTimeSlot()), Mockito.eq(facility.getOccupancy()), Mockito.any(LocalDate.class)))
        .thenReturn(null);
        Mockito.when(timingRepo.getTimingsbyFacilityId(facility.getId())).thenReturn(existingTimings);
        Mockito.when(timingRepo.insertTiming(newTiming)).thenReturn(newTiming);

        try{
            Assertions.assertTrue(timingService.addTiming(newTiming) == newTiming);
        }
        catch(TimingConflictException e) {
            Assertions.fail();
        }
    }

    @Test
    public void addTimingTest_TimingConflictException_Test() {
        TimingRepository timingRepo = Mockito.mock(TimingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        TimingService timingService = new TimingService(timingRepo, slotService, facilityService);

        Integer FACILITY_ID = 100;
        Facility facility = new Facility();
        facility.setId(FACILITY_ID);
        facility.setApprovalRequired(false);
        facility.setDescription("description");
        facility.setLocation("location");
        facility.setManagerId(123);
        facility.setName("name");
        facility.setOccupancy(100);
        facility.setTimeSlot(60);

        Timing existingTiming = new Timing();
        existingTiming.setDay(DayOfWeek.MONDAY);
        existingTiming.setStartTime(LocalTime.of(10,0));
        existingTiming.setEndTime(LocalTime.of(20,0));
        existingTiming.setFacilityId(facility.getId());
        existingTiming.setIsBlocking(false);

        List<Timing> existingTimings = Collections.singletonList(existingTiming);

        Timing newTiming = new Timing();
        newTiming.setDay(DayOfWeek.MONDAY);
        newTiming.setFacilityId(facility.getId());
        newTiming.setStartTime(LocalTime.of(11,0));
        newTiming.setEndTime(LocalTime.of(12,0));
        newTiming.setFacilityId(facility.getId());
        newTiming.setIsBlocking(false);

        Mockito.when(facilityService.getFacilityById(FACILITY_ID)).thenReturn(facility);
        Mockito.when(
            slotService.createSlotsForTiming(
                Mockito.any(Timing.class), Mockito.eq(facility.getTimeSlot()), Mockito.eq(facility.getOccupancy()), Mockito.any(LocalDate.class)))
        .thenReturn(null);
        Mockito.when(timingRepo.getTimingsbyFacilityId(facility.getId())).thenReturn(existingTimings);
        Mockito.when(timingRepo.insertTiming(newTiming)).thenReturn(newTiming);

        Assertions.assertThrows(TimingConflictException.class, () -> {
            timingService.addTiming(newTiming);
        });
    }

}
