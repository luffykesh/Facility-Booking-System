package com.csci5308.g17.facility;

import java.util.List;

public interface IFacilityRepository {

    Facility getFacilityById(int id);

    void save(Facility facility);

    List<Facility> getAllFacilities();

    void updateFacility(int id, Facility facility);

    void deleteFacility(int id);

    List<Facility> getFacilitiesByManagerId(Integer managerId);
}
