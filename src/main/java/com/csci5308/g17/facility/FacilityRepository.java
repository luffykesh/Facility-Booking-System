package com.csci5308.g17.facility;


import com.csci5308.g17.user.IUserRepository;
import com.csci5308.g17.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FacilityRepository implements IFacilityRepository {

    private JdbcTemplate db;
    private IUserRepository userRepo;

    public FacilityRepository(JdbcTemplate db, IUserRepository userRepo) {
        this.db = db;
        this.userRepo = userRepo;
    }


    private String QUERY_BY_ID = "SELECT * from facility where id = ?";
    private String QUERY_FINDALL = "Select * from facility;";
    private String QUERY_SAVE = "INSERT INTO facility (name, description, location, occupancy, manager_id, time_slot, active, approval_required) VALUES (?,?,?,?,?,?,?,?);";

    @Override
    public Facility getFacilityById(int id) {

        Facility facility = new Facility();
        facility = (Facility) this.db.queryForObject(QUERY_BY_ID, new Object[]{id}, new FacilityRowMapper());
        return facility;
    }
    //private String QUERY_SAVE = "INSERT INTO facility (name, description, location, occupancy, manager_email, time_slot, active, approval_required) VALUES (':name', ':description',':location',:occupancy,':manager_email',':time_slot', :active, :approval_required);";

    @Override
    public void Save(FormFacility formFacility) {

        System.out.println(formFacility.manager_email);
        User u = userRepo.getUserByEmail(formFacility.manager_email);
        System.out.println(u.getId());
        this.db.update(QUERY_SAVE, formFacility.name, formFacility.description, formFacility.location, formFacility.occupancy, u.getId(), formFacility.time_slot, formFacility.active, formFacility.approval_required);

    }

    @Override
    public List<Facility> findAll() {

        List<Facility> facilityList = this.db.query(QUERY_FINDALL, new FacilityRowMapper());
        return facilityList;
    }

}
