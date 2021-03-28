package com.csci5308.g17.user;

import java.util.List;

import com.csci5308.g17.config.DatabaseConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements IUserRepository {

    @Autowired
    private JdbcTemplate db;
    private static UserRepository instance;

    private String QCOUNT_USERS = "SELECT count(*) FROM user";
    private String QUSER_BY_EMAIL = "SELECT * from user where email = ?";
    private String QUSER_BY_ID = "SELECT * from user where id = ?";
    private String QSAVE_USER = "INSERT INTO user(name, email, password, role, bannerId) VALUES (?,?,?,?,?)";
    private String QUSER_BY_TOKEN = "SELECT * FROM user where token= ?";
    private String QSET_TOKEN = "UPDATE user SET token = ? WHERE Email= ?";
    private String QUPDATE_USER_PASSWORD = "UPDATE user SET password = ? WHERE id= ?";

    public UserRepository(JdbcTemplate db) {
        this.db = db;
    }

    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository(DatabaseConfig.getJdbcTemplate());
        }
        return instance;
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

    @Override
    public User getUserByToken(String token) {
        User user = db.queryForObject(QUSER_BY_TOKEN, new UserRowMapper(), new Object[]{token});
        return user;
    }

    @Override
    public void save(User user) {
        db.update(
            QSAVE_USER, user.getName(), user.getEmail(),
            user.getPassword(), user.getRole(), user.getBannerId());
    }

    @Override
    public void setUserToken(String email, String token) {
        db.update(this.QSET_TOKEN,token,email);
    }

    @Override
    public void updatePassword(Integer id, String password) {
        db.update(this.QUPDATE_USER_PASSWORD, password, id);
    }
}
