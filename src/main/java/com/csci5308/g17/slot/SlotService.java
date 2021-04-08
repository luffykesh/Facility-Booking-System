package com.csci5308.g17.slot;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.csci5308.g17.timing.Timing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SlotService implements ISlotService{

    private ISlotRepository slotRepo;
    private static SlotService instance;
    public static final int ADVANCE_SLOT_CREATE_WEEKS = 12;
    Logger logger = LoggerFactory.getLogger(SlotService.class);

    public SlotService(ISlotRepository slotRepo) {
        this.slotRepo = slotRepo;
    }

    public static SlotService getInstance() {
        if (instance == null) {
            instance = new SlotService(SlotRepository.getInstance());
        }
        return instance;
    }

    @Override
    public List<Slot> createSlotsForTiming(Timing timing, Integer slotDurationInMinutes, Integer seatCount, LocalDate fromDate) {
        logger.info(String.format("Generating slots for: %s, duration=%d, seatCount=%d, from: %s", timing, slotDurationInMinutes, seatCount, fromDate));
        List<DaySlot> daySlots = generateDaySlots(timing.getStartTime(), timing.getEndTime(), slotDurationInMinutes);
        LocalDate currentDate = fromDate;
        Integer dayDiff = fromDate.getDayOfWeek().getValue() - timing.getDay().getValue();

        if(dayDiff <= 0) {
            // eg. if timing is of wednesday and fromDate represents a day after wednesday,
            // slots will be created in the same week
            currentDate = fromDate.plusDays(Math.abs(dayDiff));
        }
        else {
            // eg. if timing is of wednesday and fromDate represents a day before wednesday,
            // slots will be created in the next week
            currentDate = fromDate.plusDays(7-dayDiff);
        }

        List<Slot> slots = new ArrayList<Slot>();
        for(int i=0; i<ADVANCE_SLOT_CREATE_WEEKS; ++i) {
            // create slots in advance for `CREATE_SLOT_WEEKS` number of weeks
            for(DaySlot daySlot : daySlots) {
                Slot s = new Slot();
                s.setStartTime(daySlot.getStartTime().atDate(currentDate));
                s.setEndTime(daySlot.getEndTime().atDate(currentDate));
                s.setFacilityId(timing.getFacilityId());
                s.setTimingId(timing.getId());
                s.setTotalSeats(seatCount);
                s.setAvailableSeats(seatCount);
                slots.add(s);
            }
            currentDate = currentDate.plusDays(7);
        }
        slotRepo.insertSlots(slots);
        return slots;
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
    public List<Slot> getSlotsForFacility(Integer facilityId, LocalDateTime fromInclusive, LocalDateTime toExclusive) {
        return slotRepo.getSlotsForFacility(facilityId, fromInclusive, toExclusive);
    }

    @Override
    public List<Slot> getSlotsForFacility(Integer facilityId, LocalDate date) {
        LocalDateTime fromInclusive = date.atTime(LocalTime.MIN);
        LocalDateTime toExclusive = fromInclusive.plusDays(1);
        return slotRepo.getSlotsForFacility(facilityId, fromInclusive, toExclusive);
    }

    @Override
    public List<Slot> getSlotsOfTiming(Integer timingId) {
        return slotRepo.getSlotsOfTiming(timingId);
    }

    public List<DaySlot> generateDaySlots(LocalTime startTime, LocalTime endTime, Integer slotDurationInMinutes) {
        List<DaySlot> slots = new ArrayList<DaySlot>();

        if(MINUTES.between(startTime, endTime) < slotDurationInMinutes) {
            throw new IllegalArgumentException("Start time and end time must be at least one slot duration apart");
        }

        logger.info(String.format("Generating day slot from %s - %s, duration=%dm", startTime, endTime, slotDurationInMinutes));

        LocalTime currentTime = startTime;
        while(currentTime.isBefore(endTime)) {
            DaySlot slot = new DaySlot();
            LocalTime slotStartTime = currentTime;
            LocalTime slotEndTime = slotStartTime.plusMinutes(slotDurationInMinutes);
            slot.setStartTime(slotStartTime);
            slot.setEndTime(slotEndTime);
            slots.add(slot);

            currentTime = currentTime.plusMinutes(slotDurationInMinutes);
        }
        return slots;
    }

    @Override
    public void releaseSeat(Integer slotId) {
        slotRepo.releaseSeat(slotId);
    }

    @Override
    public void reserveSeat(Integer slotId) throws SlotFullException {
        Slot slot = getSlotById(slotId);
        if(slot.getAvailableSeats() == 0) {
            throw new SlotFullException();
        }
        slotRepo.reserveSeat(slotId);
    }

    @Override
    public Slot getSlotById(Integer slotId) {
        return slotRepo.getSlotById(slotId);
    }
}
