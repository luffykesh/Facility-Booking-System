package com.csci5308.g17.slot;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SlotService implements ISlotRepository{

    private ISlotRepository slotRepo;
    private static SlotService instance;

    public SlotService(ISlotRepository slotRepo) {
        this.slotRepo = slotRepo;
    }

    public SlotService getInstance() {
        if (instance == null) {
            instance = new SlotService(SlotRepository.getInstance());
        }
        return instance;
    }

    @Override
    public void deleteSlot(Integer id) {
        slotRepo.deleteSlot(id);
    }

    @Override
    public void deleteTimingSlots(Integer timingId) {
        slotRepo.deleteTimingSlots(timingId);
    }

    @Override
    public List<Slot> getSlotsForFacility(Integer facilityId, LocalDate date) {
        return slotRepo.getSlotsForFacility(facilityId, date);
    }

    @Override
    public List<Slot> getSlotsOfTiming(Integer timingId) {
        return slotRepo.getSlotsOfTiming(timingId);
    }

    @Override
    public void insertSlots(List<Slot> slots) {
        slotRepo.insertSlots(slots);
    }
}
