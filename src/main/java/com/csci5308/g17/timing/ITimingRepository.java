package com.csci5308.g17.timing;

import java.util.List;

public interface ITimingRepository {

    Timing insertTiming(Timing timing);

    Timing getTimingById(Integer id);

    List<Timing> getTimingsbyFacilityId(Integer facilityId);

    void deleteTimingById(Integer id);
}
