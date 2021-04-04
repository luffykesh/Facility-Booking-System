package com.csci5308.g17.timing;

import java.util.List;

public interface ITimingService {

    Timing getTiming(Integer timingId);

    List<Timing> getFacilityTimings(Integer facilityId);

    void deleteTiming(Integer timingId);

    public Timing addTiming(Timing timing) throws TimingConflictException;
}
