package com.tsolution.sso._3service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsolution.sso._1entities.Role;
import com.tsolution.sso._2repository.RoleRepository;
import com.tsolution.sso._3service.RoleService;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.Translator;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private Translator translator;

	@Override
	public ResponseEntity<Object> getOne(Long roleId) {
		return new ResponseEntity<>(this.roleRepository.findById(roleId), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> find(String clientId, String text, Pageable pageable) {
		return new ResponseEntity<>(this.roleRepository.find(clientId, text, pageable), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> create(Role role) {
		return new ResponseEntity<>(this.roleRepository.save(role), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> update(Long id, Role roleEntityInput) throws BusinessException {
		Optional<Role> ops = this.roleRepository.findById(id);
		if (!ops.isPresent()) {
			throw new BusinessException(this.translator.toLocale("role.is.not.exist"));
		}

		Role role = ops.get();
		role.setClientId(roleEntityInput.getClientId());
		role.setDescription(roleEntityInput.getDescription());
		role.setRoleName(roleEntityInput.getRoleName());
		if ((roleEntityInput.getMenus() != null) && (roleEntityInput.getPermissions() != null)) {
			role.setMenus(roleEntityInput.getMenus());
			role.setPermissions(roleEntityInput.getPermissions());
		}
		return new ResponseEntity<>(this.roleRepository.save(role), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> delete(Long id) throws BusinessException {
		Optional<Role> ops = this.roleRepository.findById(id);
		if (!ops.isPresent()) {
			throw new BusinessException(this.translator.toLocale("role.is.not.exist"));
		}
		this.roleRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<>(this.roleRepository.findAll(), HttpStatus.OK);
	}
}
