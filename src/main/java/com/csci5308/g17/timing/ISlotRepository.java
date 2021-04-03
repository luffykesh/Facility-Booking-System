package com.csci5308.g17.timing;

import java.time.LocalDate;
import java.util.List;

public interface ISlotRepository {

    public void insertSlots(List<Slot> slots);

    public List<Slot> getSlotsForFacility(Integer facilityId, LocalDate date);

    public List<Slot> getSlotsOfTiming(Integer timingId);

    public void deleteSlot(Integer id);
}
