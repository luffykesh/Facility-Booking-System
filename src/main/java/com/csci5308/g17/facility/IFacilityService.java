package com.csci5308.g17.facility;

import java.util.List;

public interface IFacilityService {
    Facility getFacilityById(int id);

    void save(FormFacility formFacility);

    List<Facility> findAll();
}
