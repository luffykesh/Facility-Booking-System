package com.csci5308.g17.facility;


import java.util.List;

public interface IFacilityRepository {

    List<Facility> getFacilityById(int id);
    void Save(Facility facility);
}
