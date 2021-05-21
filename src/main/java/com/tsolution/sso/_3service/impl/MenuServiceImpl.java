package com.tsolution.sso._3service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsolution.sso._1entities.Menu;
import com.tsolution.sso._2repository.MenuRepository;
import com.tsolution.sso._3service.MenuService;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.Translator;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private Translator translator;

	@Override
	public ResponseEntity<Object> getOne(Long menuId) {
		return new ResponseEntity<>(this.menuRepository.findById(menuId), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> find(Menu menu, Pageable pageable) {
		return new ResponseEntity<>(this.menuRepository.findMenuEntityCustom(menu, pageable), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<>(this.menuRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> findByClientId(String clientId) {
		clientId = clientId == null ? "" : clientId;
		List<Menu> results = this.menuRepository.findAllByClientId(clientId);
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> create(Menu menu) throws BusinessException {
		return new ResponseEntity<>(this.menuRepository.save(menu), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> update(Long id, Menu menuEntityInput) throws BusinessException {
		Optional<Menu> ops = this.menuRepository.findById(id);
		if (!ops.isPresent()) {
			throw new BusinessException(this.translator.toLocale("menu.is.not.exist"));
		}
		Menu menu = ops.get();
		menu.setUrl(menuEntityInput.getUrl());
		menu.setCode(menuEntityInput.getCode());
		menu.setAppType(menuEntityInput.getAppType());
		menu.setClientId(menuEntityInput.getClientId());
		menu.setParentMenu(menuEntityInput.getParentMenu());
		return new ResponseEntity<>(this.menuRepository.save(menu), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> delete(Long id) throws BusinessException {
		Optional<Menu> ops = this.menuRepository.findById(id);
		if (!ops.isPresent()) {
			throw new BusinessException(this.translator.toLocale("menu.not.exist"));
		}
		this.menuRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
