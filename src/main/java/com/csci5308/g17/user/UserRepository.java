package com.csci5308.g17.user;

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
}
