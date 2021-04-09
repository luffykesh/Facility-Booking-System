package com.csci5308.g17.user;

import java.util.List;

import com.csci5308.g17.config.DatabaseConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements IUserRepository {

    private JdbcTemplate db;
    private static UserRepository instance;

    private String QUSER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private String QUSER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private String QSAVE_USER = "INSERT INTO user(name, email, role, bannerId) VALUES (?,?,?,?)";
    private String QUSER_BY_TOKEN = "SELECT * FROM user WHERE token= ?";
    private String QSET_TOKEN = "UPDATE user SET token = ? WHERE Email= ?";
    private String QUPDATE_USER_PASSWORD = "UPDATE user SET password = ? WHERE id= ?";
    private String QCLEAR_USER_TOKEN = "UPDATE user SET token=null WHERE id = ?";
    private String QSET_VERIFY_FLAG="UPDATE user SET verified=? WHERE id = ?";
    private String QUERY_FINDALL="SELECT * FROM user";
    private String QUERY_DELETE = "DELETE FROM user WHERE id = ?";

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
    public void setVerifiedFlag(Integer userId, Boolean flag){
        db.update(this.QSET_VERIFY_FLAG, flag, userId);
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
             user.getRole(), user.getBannerId());
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = this.db.query(QUERY_FINDALL, new UserRowMapper());
        return userList;
    }

    @Override
    public void setUserToken(String email, String token) {
        db.update(this.QSET_TOKEN,token,email);
    }

    @Override
    public void updatePassword(Integer id, String password) {
        db.update(this.QUPDATE_USER_PASSWORD, password, id);
    }

    @Override
    public void clearUserToken(Integer userId) {
        db.update(QCLEAR_USER_TOKEN, userId);
    }

    @Override
    public void deleteUser(Integer id) {
        this.db.update(QUERY_DELETE,id);
    }
}
