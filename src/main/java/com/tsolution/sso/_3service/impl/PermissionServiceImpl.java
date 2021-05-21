package com.tsolution.sso._3service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsolution.sso._1entities.Permission;
import com.tsolution.sso._2repository.PermissionRepository;
import com.tsolution.sso._3service.PermissionService;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.Translator;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	private Translator translator;

	@Override
	public ResponseEntity<Object> getOne(Long permissionId) {
		return new ResponseEntity<>(this.permissionRepository.findById(permissionId), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> find(String clientId, String url, String description, Pageable pageable) {
		return new ResponseEntity<>(
				this.permissionRepository.findAllByConditionSearch(clientId, url, description, pageable),
				HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> create(Permission permissionEntityInput) {
		return new ResponseEntity<>(this.permissionRepository.save(permissionEntityInput), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> update(Long id, Permission permissionEntityInput) throws BusinessException {
		Optional<Permission> ops = this.permissionRepository.findById(id);
		if (!ops.isPresent()) {
			throw new BusinessException(this.translator.toLocale("permission.is.not.exist"));
		}
		Permission permission = ops.get();
		permission.setClientId(permissionEntityInput.getClientId());
		permission.setUrl(permissionEntityInput.getUrl());
		permission.setDescription(permissionEntityInput.getDescription());

		return new ResponseEntity<>(this.permissionRepository.save(permission), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> delete(Long id) throws BusinessException {
		Optional<Permission> ops = this.permissionRepository.findById(id);
		if (!ops.isPresent()) {
			throw new BusinessException(this.translator.toLocale("permission.is.not.exist"));
		}
		this.permissionRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<>(this.permissionRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> findByClientId(String clientId) {
		clientId = clientId == null ? "" : clientId;
		List<Permission> results = this.permissionRepository.findAllByClientId(clientId);
		return new ResponseEntity<>(results, HttpStatus.OK);
	}
}
