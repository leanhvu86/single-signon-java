package com.tsolution.sso._3service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.tsolution.sso._1entities.Permission;
import com.tsolution.sso.exceptions.BusinessException;

public interface PermissionService {

	ResponseEntity<Object> getOne(Long menuId);

	ResponseEntity<Object> find(String clientId, String url, String code, Pageable pageable) throws BusinessException;

	ResponseEntity<Object> create(Permission permission) throws BusinessException;

	ResponseEntity<Object> update(Long id, Permission permissionEntityInput) throws BusinessException;

	ResponseEntity<Object> delete(Long id) throws BusinessException;

	ResponseEntity<Object> getAll();

	ResponseEntity<Object> findByClientId(String clientId);
}
