package com.tsolution.sso._2repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tsolution.sso._1entities.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	@Query(value = "select p from Permission p where "
			+ "( lower(p.clientId) like lower(CONCAT(CONCAT('%',:clientId),'%'))) "
			+ "and (lower(p.url) like lower(CONCAT(CONCAT('%',:url),'%')) "
			+ "or lower(p.description) like lower(CONCAT(CONCAT('%',:description),'%')))")
	Page<Permission> findAllByConditionSearch(String clientId, String url, String description, Pageable pageable);

	List<Permission> findAllByClientId(String clientId);
}
