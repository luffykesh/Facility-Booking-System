package com.csci5308.g17.timing;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TimingService implements ITimingService{

    private ITimingRepository timingRepository;
    private static TimingService instance;

    public TimingService(ITimingRepository timingRepository) {
        this.timingRepository = timingRepository;
    }

    public static TimingService getInstance() {
        if(instance == null) {
            instance = new TimingService(TimingRepository.getInstance());
        }
        return instance;
    }

    @Override
    public Timing addTiming(Timing timing) throws TimingConflictException{

        if(timing.getEndTime().compareTo(timing.getStartTime())<=0) {
            throw new IllegalArgumentException("End time must be greater than Start time");
        }

        List<Timing> existingTimings = timingRepository.getTimingsbyFacilityId(timing.getFacilityId());
        // check for conflict with existing timings of facility
        for(Timing t : existingTimings) {
            if(isOverlapping(t, timing)) {
                throw new TimingConflictException(t, timing);
            }
        }
        if(timing.getIsBlocking() == null){
            timing.setIsBlocking(false);
        }
        return timingRepository.insertTiming(timing);
    }

    @Override
    public void deleteTiming(Integer timingId) {
        timingRepository.deleteTimingById(timingId);
    }

    @Override
    public List<Timing> getFacilityTimings(Integer facilityId) {
        return timingRepository.getTimingsbyFacilityId(facilityId);
    }

    @Override
    public Timing getTiming(Integer timingId) {
        return timingRepository.getTimingById(timingId);
    }

    public boolean isOverlapping(Timing first, Timing second) {
        if(first.getDay().compareTo(second.getDay()) != 0) {
            // timings are not overlapping if they represent
            // different days of the week
            return false;
        }

        if(first.getStartTime().isBefore(second.getStartTime()) &&
            first.getEndTime().compareTo(second.getStartTime()) <= 0) {
            // first timing is before second
            return false;
        }

        if(first.getStartTime().compareTo(second.getEndTime()) >= 0 &&
            first.getEndTime().isAfter(second.getEndTime())) {
            // first timing is after second
            return false;
        }

        return true;
    }

}
