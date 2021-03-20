package com.csci5308.g17.user;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements IUserRepository {

    private JdbcTemplate db;

    private String QCOUNT_USERS = "SELECT count(*) FROM user";
    private String QUSER_BY_EMAIL = "SELECT * from user where email = ?";
    private String QUSER_BY_ID = "SELECT * from user where id = ?";

    public UserRepository(JdbcTemplate db) {
        this.db = db;
    }

    @Override
    public Integer count() {
        Integer count = db.queryForObject(QCOUNT_USERS, Integer.class);
        return count;
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> userList = db.query(QUSER_BY_EMAIL, new UserRowMapper(), new Object[] {email});
        if(userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public User getUserById(Integer Id) {
        User user =
            db.queryForObject(
                QUSER_BY_ID,
                new UserRowMapper(),
                new Object[]{Id});
        return user;
    }
}
