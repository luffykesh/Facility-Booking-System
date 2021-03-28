package com.csci5308.g17.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository implements IUserRepository {

	private JdbcTemplate db;


	private String QCOUNT_USERS = "SELECT count(*) FROM user";
	private String QUSER_BY_EMAIL = "SELECT * from user where email = ?";
	private String QUSER_BY_ID = "SELECT * from user where id = ?";
	private String QSAVE_USER="INSERT INTO user( name, email, password,role, bannerId) VALUES (?,?,?,?,?)";
	private String QUSER_BY_TOKEN="  SELECT * FROM user where token=?";
	private String QSET_TOKEN="UPDATE user SET token =? WHERE Email=?";
	private String QUPDATE_USER_PASSWORD="UPDATE user SET password =? WHERE id=? ";

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
		User u = db.queryForObject(QUSER_BY_EMAIL, new UserRowMapper(), new Object[]{email});
		return u;
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
	public  User getUserByToken(String token){
		User user=db.queryForObject(QUSER_BY_TOKEN,new UserRowMapper(),new Object[]{token});
		return user;
	}

	@Override
	public List<User> saveALL(List<User> user) {
		if (user.size() != 0) {
			for (int i = 0; i < user.size(); i++) {
				String name = user.get(i).getName();
				String email = user.get(i).getEmail();
				String password=user.get(i).getPassword();
				String role = user.get(i).getRole();
				String bid = user.get(i).getBannerId();
				this.db.update(this.QSAVE_USER, name, email,password, role, bid);
			}
		}
		return user;
	}

    @Override
	public Integer setToken(String email, String token){
		int userToken=db.update(this.QSET_TOKEN,token,email);
		return userToken;
	}

	@Override
	public int updatePassword(int id, String password){

		Integer userPassword=db.update(this.QUPDATE_USER_PASSWORD,password,id);
		return userPassword;
	}

	@Override
	public Integer getUserIdByToken(String token){
		 User user = db.queryForObject(QUSER_BY_TOKEN, new UserRowMapper(), new Object[]{token});
		 return user.getId();
	}
}
