package com.csci5308.g17.repository;

import java.util.Optional;


// package org.springframework.data.repository;

public interface CrudRepository<T, ID> {

	<S> S save(S entity);

	<S> Iterable<S> saveAll(Iterable<S> entities);

	Optional<T> findById(ID id);

	boolean existsById(ID id);

	Iterable<T> findAll();

	Iterable<T> findAllById(Iterable<ID> ids);

	long count();

	void deleteById(ID id);

	void delete(T entity);

	void deleteAll(Iterable<?> entities);

	void deleteAll();
}
