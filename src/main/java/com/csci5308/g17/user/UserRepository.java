package com.csci5308.g17.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository implements IUserRepository {

	private JdbcTemplate db;
	private NamedParameterJdbcTemplate npdb;


	private String QCOUNT_USERS = "SELECT count(*) FROM user";
	private String QUSER_BY_EMAIL = "SELECT * from user where email = ?";
	private String QUSER_BY_ID = "SELECT * from user where id = ?";
	public static String saveUser="INSERT INTO user( name, email, password,role, bannerId) VALUES (?,?,?,?,?)";
	public static String find_token="  SELECT * FROM user where token=?";
	public static String set_token="UPDATE user SET token =? WHERE Email=?";
	public static String update_password="UPDATE user SET password =? WHERE token=? ";

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
	public  User getUserByToken(Integer token){
		User user=db.queryForObject(find_token,new UserRowMapper(),new Object[]{token});
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
				this.db.update(this.saveUser, name, email,password, role, bid);
			}

		}
		return user;
	}

    @Override
	public Integer setTocken(String email, int token){
		int utoken=db.update(this.set_token,token,email);
		return utoken;
	}

	@Override
	public Integer updatePassword(int token, String password){

		Integer upass=db.update(this.update_password,password,token);
		return upass;
	}
}
