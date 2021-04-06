package com.csci5308.g17.facility;


import com.csci5308.g17.config.DatabaseConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FacilityRepository implements IFacilityRepository {

    private static FacilityRepository instance;
    private final String QUERY_BY_ID = "SELECT * from facility where id = ?";
    private final String QUERY_FINDALL = "Select * from facility;";
    private final String QUERY_SAVE = "INSERT INTO facility (name, description, location, occupancy, manager_id, time_slot, active, approval_required) VALUES (?,?,?,?,?,?,?,?);";
    private final String QUERY_UPDATE = "update facility set name = ?,description = ?, location = ?, occupancy = ?, manager_id = ?, time_slot = ?, active = ?, approval_required =? where id = ?";
    private final String QUERY_DELETE = "delete from facility where id = ?";
    private final String QUERY_FINDMANAGER = "Select * from facility WHERE manager_id=?";
    private final JdbcTemplate db;

    public FacilityRepository(JdbcTemplate db) {
        this.db = db;
    }

    public static FacilityRepository getInstance() {
        if (instance == null) {
            instance = new FacilityRepository(DatabaseConfig.getJdbcTemplate());
        }
        return instance;
    }

    @Override
    public Facility getFacilityById(int id) {

        List<Facility> facilityList = this.db.query(QUERY_BY_ID, new FacilityRowMapper(), id);
        if (facilityList.isEmpty()) {
            return null;
        }
        return facilityList.get(0);
    }

    @Override
    public void save(Facility facility) {

        this.db.update(QUERY_SAVE, facility.getName(), facility.getDescription(), facility.getLocation(), facility.getOccupancy(), facility.getManagerId(), facility.getTimeSlot(), facility.getActive(), facility.getApprovalRequired());
    }

    @Override
    public List<Facility> findAll() {

        List<Facility> facilityList = this.db.query(QUERY_FINDALL, new FacilityRowMapper());
        return facilityList;
    }

    @Override
    public void updateFacility(int id, Facility facility) {
        this.db.update(QUERY_UPDATE, facility.getName(), facility.getDescription(), facility.getLocation(), facility.getOccupancy(), facility.getManagerId(), facility.getTimeSlot(), facility.getActive(), facility.getApprovalRequired(), id);
    }

    @Override
    public void deleteFacility(int id) {
        this.db.update(QUERY_DELETE,id);
    }

    @Override
    public List<Facility> findAllFacility(int id) {

        List<Facility> facilityList = this.db.query(QUERY_FINDMANAGER,new FacilityRowMapper(),id);
        return facilityList;
    }

}
