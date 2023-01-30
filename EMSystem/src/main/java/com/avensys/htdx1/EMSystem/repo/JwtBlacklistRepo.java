package com.avensys.htdx1.EMSystem.repo;

import org.springframework.data.repository.CrudRepository;

import com.avensys.htdx1.EMSystem.entity.JwtBlacklist;

public interface JwtBlacklistRepo extends CrudRepository<JwtBlacklist, Long>{
	
	public boolean existsByJwt(String jwt);

}
