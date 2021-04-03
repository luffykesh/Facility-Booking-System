package com.csci5308.g17.slot;

import java.util.List;

import com.csci5308.g17.timing.Timing;

public interface ISlotService {

    public List<Slot> createSlots(Timing timing, Integer slotDuration);

    public List<Slot> getSlotsByFacility(Integer facilityId);

    public List<Slot> getSlotsOfTiming(Integer timingId);

    public void deleteSlot(Integer Id);
}
