package com.tsolution.sso._4controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tsolution.sso._1entities.Permission;
import com.tsolution.sso._3service.PermissionService;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.Translator;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private Translator translator;

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOne(@PathVariable("id") Long id) {
		return this.permissionService.getOne(id);
	}

	@GetMapping("/find")
	public ResponseEntity<Object> findAll(@RequestParam(value = "clientId", required = false) String clientId,
			@RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "pageNumber") Integer pageNumber, @RequestParam(value = "pageSize") Integer pageSize)
			throws BusinessException {
		clientId = clientId == null ? "" : clientId;
		text = text == null ? "" : text;
		return this.permissionService.find(clientId, text, text, PageRequest.of(pageNumber - 1, pageSize));
	}

	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll() {
		return this.permissionService.getAll();
	}

	@GetMapping("/client-id")
	public ResponseEntity<Object> findByClientId(@RequestParam(value = "client-id") String clientId) {
		return this.permissionService.findByClientId(clientId);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Optional<Permission> oPermissionEntity)
			throws BusinessException {
		if (oPermissionEntity.isPresent()) {
			return this.permissionService.create(oPermissionEntity.get());
		}
		throw new BusinessException(this.translator.toLocale(BusinessException.COMMON_INPUT_INFO_INVALID));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") Long id,
			@RequestBody Optional<Permission> oPermissionEntity) throws BusinessException {
		if (oPermissionEntity.isPresent()) {
			return this.permissionService.update(id, oPermissionEntity.get());
		}
		throw new BusinessException(this.translator.toLocale(BusinessException.COMMON_INPUT_INFO_INVALID));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws BusinessException {
		return this.permissionService.delete(id);
	}

}
