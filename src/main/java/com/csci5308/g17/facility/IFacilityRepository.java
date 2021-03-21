package com.csci5308.g17.facility;

import java.util.List;

public interface IFacilityRepository {

    Facility getFacilityById(int id);

    void save(Facility facility);

    List<Facility> findAll();
}
