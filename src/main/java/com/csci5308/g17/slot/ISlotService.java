package com.csci5308.g17.slot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.csci5308.g17.timing.Timing;

public interface ISlotService {

    public List<Slot> createSlotsForTiming(Timing timing, Integer slotDurationInMinutes, Integer seatCount, LocalDate fromDate);

    public List<Slot> getSlotsForFacility(Integer facilityId, LocalDateTime fromInclusive, LocalDateTime toExclusive);

    public List<Slot> getSlotsForFacility(Integer facilityId, LocalDate date);

    public List<Slot> getSlotsOfTiming(Integer timingId);

    public void deleteSlot(Integer Id);

    public void deleteTimingSlots(Integer timingId);

    public void reserveSeat(Integer slotId) throws SlotFullException;

    public void releaseSeat(Integer slotId);

    public Slot getSlotById(Integer slotId);
}