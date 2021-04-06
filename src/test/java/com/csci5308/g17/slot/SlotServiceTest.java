package com.csci5308.g17.slot;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import com.csci5308.g17.timing.Timing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SlotServiceTest {
    @Test
    public void deleteSlotTest() {
        SlotRepository slotRepo = Mockito.mock(SlotRepository.class);
        SlotService slotService = new SlotService(slotRepo);

        final Integer SLOT_ID = 100;

        Mockito.doNothing().when(slotRepo).deleteSlot(SLOT_ID);
        slotService.deleteSlot(SLOT_ID);
        Mockito.verify(slotRepo, Mockito.times(1)).deleteSlot(SLOT_ID);
    }

    @Test
    public void deleteTimingSlotsTest() {
        SlotRepository slotRepo = Mockito.mock(SlotRepository.class);
        SlotService slotService = new SlotService(slotRepo);

        final Integer TIMING_ID = 100;

        Mockito.doNothing().when(slotRepo).deleteTimingSlots(TIMING_ID);
        slotService.deleteTimingSlots(TIMING_ID);
        Mockito.verify(slotRepo, Mockito.times(1)).deleteTimingSlots(TIMING_ID);
    }

    @Test
    public void getSlotsForFacilityTest() {
        SlotRepository slotRepo = Mockito.mock(SlotRepository.class);
        SlotService slotService = new SlotService(slotRepo);

        final Integer FACILITY_ID = 10;
        final LocalDate DATE = LocalDate.now();

        Slot slot = new Slot();
        slot.setId(100);
        slot.setFacilityId(100);
        slot.setTimingId(100);
        slot.setAvailableSeats(20);
        slot.setTotalSeats(20);
        slot.setStartTime(LocalDateTime.now());
        slot.setEndTime(slot.getStartTime().plusHours(1));

        List<Slot> SLOTS = Collections.singletonList(slot);

        Mockito.when(slotRepo.getSlotsForFacility(Mockito.eq(FACILITY_ID), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class))).thenReturn(SLOTS);
        Assertions.assertEquals(slotService.getSlotsForFacility(FACILITY_ID, DATE), SLOTS);
    }

    @Test
    public void getSlotsOfTimingTest() {
        SlotRepository slotRepo = Mockito.mock(SlotRepository.class);
        SlotService slotService = new SlotService(slotRepo);

        final Integer TIMING_ID = 10;

        Slot slot = new Slot();
        slot.setId(100);
        slot.setFacilityId(100);
        slot.setTimingId(TIMING_ID);
        slot.setAvailableSeats(20);
        slot.setTotalSeats(20);
        slot.setStartTime(LocalDateTime.now());
        slot.setEndTime(slot.getStartTime().plusHours(1));

        List<Slot> SLOTS = Collections.singletonList(slot);

        Mockito.when(slotRepo.getSlotsOfTiming(TIMING_ID)).thenReturn(SLOTS);
        Assertions.assertEquals(slotService.getSlotsOfTiming(TIMING_ID), SLOTS);
    }

    @Test
    public void createSlotsForTimingTest() {
        SlotRepository slotRepo = Mockito.mock(SlotRepository.class);
        SlotService slotService = new SlotService(slotRepo);

        final Integer SLOT_DURATION = 60;
        final Integer SEAT_COUNT = 20;
        final LocalDate FROM_DATE = LocalDate.of(2021, 4, 4); //sunday

        Timing timing = new Timing();
        timing.setId(123);
        timing.setDay(DayOfWeek.MONDAY);
        timing.setStartTime(LocalTime.of(10,0));
        timing.setEndTime(LocalTime.of(15,0));
        timing.setFacilityId(100);
        timing.setIsBlocking(false);

        int timeDiff = (int)MINUTES.between(timing.getStartTime(), timing.getEndTime());
        int expectedSlotCount = (timeDiff/SLOT_DURATION) * SlotService.ADVANCE_SLOT_CREATE_WEEKS;

        List<Slot> slots = slotService.createSlotsForTiming(timing, SLOT_DURATION, SEAT_COUNT, FROM_DATE);
        Assertions.assertEquals(expectedSlotCount, slots.size());
    }

    @Test
    public void generateDaySlotsTest() {
        SlotRepository slotRepo = Mockito.mock(SlotRepository.class);
        SlotService slotService = new SlotService(slotRepo);

        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11,0);
        Integer slotDuration = 30;
        List<DaySlot> daySlots = slotService.generateDaySlots(startTime, endTime, slotDuration);

        Assertions.assertEquals(daySlots.size(), 2);
    }

    @Test
    public void generateDaySlots_InvalidTimeRange_Test() {
        SlotRepository slotRepo = Mockito.mock(SlotRepository.class);
        SlotService slotService = new SlotService(slotRepo);

        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(10,30);
        Integer slotDuration = 60;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            slotService.generateDaySlots(startTime, endTime, slotDuration);
        });
    }
}
