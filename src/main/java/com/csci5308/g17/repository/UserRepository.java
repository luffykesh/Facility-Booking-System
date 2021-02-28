package com.csci5308.g17.repository;

import java.util.Optional;

import com.csci5308.g17.model.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class UserRepository implements CrudRepository<User, Integer> {
	private JdbcTemplate db;

	public static String COUNT_QUERY = "SELECT count(*) FROM user";

	public UserRepository(JdbcTemplate db) {
		this.db = db;
	}

	@Override
	public long count() {
		Long count = this.db.queryForObject(this.COUNT_QUERY, Long.class);
		return count;
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<?> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<User> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
