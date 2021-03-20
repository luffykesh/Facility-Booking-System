package com.csci5308.g17.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository implements IUserRepository {

	private JdbcTemplate db;

	private String QCOUNT_USERS = "SELECT count(*) FROM user";
	private String QUSER_BY_EMAIL = "SELECT * from user where email = ?";
	private String QUSER_BY_ID = "SELECT * from user where id = ?";
	public static String saveUser="INSERT INTO user(id, name, email, password, role, bannerId) VALUES (,?,?,?,?,?,?)";

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
	public void saveALL(List<User> user){
		if(user.size()!=0) {
			for (int i = 0; i < user.size(); i++) {
				int id = (int) user.get(i).getId();
				String name = user.get(i).getName();
				String email = user.get(i).getEmail();
				String password = user.get(i).getPassword();
				String role=user.get(i).getRole();
				String bid=user.get(i).getBannerId();
				this.db.update(this.saveUser, id, name, email, password, role,bid);
			}
			System.out.println("done");
		}
		else {
			System.out.println("not done");
		}
	}
}
