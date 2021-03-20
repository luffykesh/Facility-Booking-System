package com.csci5308.g17.facility;

import java.util.List;

public interface IFacilityService {
    List<Facility> getFacilityById(int id);
    void Save(Facility facility);
}
