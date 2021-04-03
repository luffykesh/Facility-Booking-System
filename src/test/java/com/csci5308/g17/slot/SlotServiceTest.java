package com.csci5308.g17.slot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

        Mockito.when(slotRepo.getSlotsForFacility(FACILITY_ID, DATE)).thenReturn(SLOTS);
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
    public void insertSlotsTest() {
        SlotRepository slotRepo = Mockito.mock(SlotRepository.class);
        SlotService slotService = new SlotService(slotRepo);

        Slot slot = new Slot();
        slot.setId(100);
        slot.setFacilityId(100);
        slot.setTimingId(200);
        slot.setAvailableSeats(20);
        slot.setTotalSeats(20);
        slot.setStartTime(LocalDateTime.now());
        slot.setEndTime(slot.getStartTime().plusHours(1));

        List<Slot> SLOTS = Collections.singletonList(slot);

        Mockito.doNothing().when(slotRepo).insertSlots(SLOTS);
        slotService.insertSlots(SLOTS);
        Mockito.verify(slotRepo, Mockito.times(1)).insertSlots(SLOTS);
    }
}
