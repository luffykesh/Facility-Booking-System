package com.csci5308.g17.facility;

import java.util.List;

public interface IFacilityService {
    Facility getFacilityById(int id);

    void save(Facility facility);

    List<Facility> findAll();

    void updateFacility(int id, Facility facility);

    public void deleteFacility(int id);

    List<Facility> getFacility(int id);
}
