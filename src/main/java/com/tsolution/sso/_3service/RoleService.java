package com.tsolution.sso._3service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.tsolution.sso._1entities.Role;
import com.tsolution.sso.exceptions.BusinessException;

public interface RoleService {
	ResponseEntity<Object> getOne(Long menuId);

	ResponseEntity<Object> find(String clientId, String text, Pageable pageable);

	ResponseEntity<Object> create(Role role) throws BusinessException;

	ResponseEntity<Object> update(Long id, Role roleEntityInput) throws BusinessException;

	ResponseEntity<Object> delete(Long id) throws BusinessException;

	ResponseEntity<Object> getAll();
}
