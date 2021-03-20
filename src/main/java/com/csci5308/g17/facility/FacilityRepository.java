package com.csci5308.g17.facility;


import com.csci5308.g17.user.IUserRepository;
import com.csci5308.g17.user.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FacilityRepository implements IFacilityRepository{

    private JdbcTemplate db;
    private NamedParameterJdbcTemplate npdb;
    private IUserRepository userRepo;

    public FacilityRepository(JdbcTemplate db, NamedParameterJdbcTemplate npdb, IUserRepository userRepo) {
        this.db = db;
        this.npdb = npdb;
        this.userRepo = userRepo;
    }



    private String QUERY_BY_NAME = "SELECT * from facility where id = :id";

    @Override
    public List<Facility> getFacilityById(int id) {
        Map namedParameters = new HashMap();
        namedParameters.put("id",id);
        List<Facility> facility = this.npdb.query(QUERY_BY_NAME, namedParameters, new BeanPropertyRowMapper(Facility.class));
        return facility;
    }
    //private String QUERY_SAVE = "INSERT INTO facility (name, description, location, occupancy, manager, time_slot, active, approval_required) VALUES (':name', ':description',':location',:occupancy,':manager',':time_slot', :active, :approval_required);";
    private String QUERY_SAVE = "INSERT INTO facility (name, description, location, occupancy, manager, time_slot, active, approval_required) VALUES (?,?,?,?,?,?,?,?);";
    @Override
    public void Save(Facility facility){

        User u = userRepo.getUserByEmail(facility.manager);
        System.out.println(u.getEmail());
        this.db.update(QUERY_SAVE, facility.name,facility.description,facility.location,facility.occupancy,facility.manager,facility.time_slot,facility.active,facility.approval_required);

    }

}
