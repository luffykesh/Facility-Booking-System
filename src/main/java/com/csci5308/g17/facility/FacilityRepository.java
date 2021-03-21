package com.csci5308.g17.facility;


import com.csci5308.g17.user.IUserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FacilityRepository implements IFacilityRepository {

    private final JdbcTemplate db;
    private final IUserRepository userRepo;
    private final String QUERY_BY_ID = "SELECT * from facility where id = ?";
    private final String QUERY_FINDALL = "Select * from facility;";
    private final String QUERY_SAVE = "INSERT INTO facility (name, description, location, occupancy, manager_id, time_slot, active, approval_required) VALUES (?,?,?,?,?,?,?,?);";
    private final String QUERY_UPDATE = "update facility set name = ?,description = ?, location = ?, occupancy = ?, manager_id = ?, time_slot = ?, active = ?, approval_required =? where id = ?";

    public FacilityRepository(JdbcTemplate db, IUserRepository userRepo) {
        this.db = db;
        this.userRepo = userRepo;
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

        this.db.update(QUERY_SAVE, facility.name, facility.description, facility.location, facility.occupancy, facility.managerId, facility.timeSlot, facility.active, facility.approvalRequired);
    }

    @Override
    public List<Facility> findAll() {

        List<Facility> facilityList = this.db.query(QUERY_FINDALL, new FacilityRowMapper());
        return facilityList;
    }

    @Override
    public void updateFacility(int id, Facility facility){
        this.db.update(QUERY_UPDATE,facility.name, facility.description, facility.location, facility.occupancy, facility.managerId, facility.timeSlot, facility.active, facility.approvalRequired,id);
    }

}
