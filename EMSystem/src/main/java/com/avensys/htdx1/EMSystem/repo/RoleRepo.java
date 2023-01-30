package com.avensys.htdx1.EMSystem.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avensys.htdx1.EMSystem.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);

}
