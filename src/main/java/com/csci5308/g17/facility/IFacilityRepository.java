package com.csci5308.g17.facility;

import java.util.List;

public interface IFacilityRepository {

    Facility getFacilityById(int id);

    void Save(FormFacility formFacility);

    List<Facility> findAll();
}
