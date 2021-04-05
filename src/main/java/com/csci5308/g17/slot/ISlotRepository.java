package com.csci5308.g17.slot;

import java.time.LocalDateTime;
import java.util.List;

public interface ISlotRepository {

    public void insertSlots(List<Slot> slots);

    public List<Slot> getSlotsForFacility(Integer facilityId, LocalDateTime fromInclusive, LocalDateTime toExclusive);

    public List<Slot> getSlotsOfTiming(Integer timingId);

    public void deleteSlot(Integer id);

    public void deleteTimingSlots(Integer timingId);

    public void reserveSeat(Integer slotId);

    public void releaseSeat(Integer slotId);

    public Slot getSlotById(Integer slotId);
}
