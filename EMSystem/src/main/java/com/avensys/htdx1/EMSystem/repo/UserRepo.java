package com.avensys.htdx1.EMSystem.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.avensys.htdx1.EMSystem.entity.UserEntity;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
	public UserEntity findById(int id);

	public boolean existsByUsername(String username);
	public boolean existsByPassword(String password);

	public Optional<UserEntity> findByUsername(String username);

	@Query(value = "SELECT id FROM user WHERE username = :username", nativeQuery = true)
	List<Long> findAllIdByUsername(@Param("username") String username);
}
