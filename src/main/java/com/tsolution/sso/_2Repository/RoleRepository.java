package com.tsolution.sso._2repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tsolution.sso._1entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query(value = "SELECT r.* " + RoleSql.FIND, countQuery = "SELECT COUNT(r.id) " + RoleSql.FIND, nativeQuery = true)
	Page<Role> find(String clientId, String text, Pageable pageable);
}
