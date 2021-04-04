package com.csci5308.g17.facility;

import java.util.List;

public interface IFacilityService {
    FormFacility getFacilityById(int id);

    void save(FormFacility formFacility);

    List<FormFacility> findAll();

    void updateFacility(int id, FormFacility formFacility);

    public void deleteFacility(int id);
}
