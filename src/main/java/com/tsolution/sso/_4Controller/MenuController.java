package com.tsolution.sso._4controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tsolution.sso._1entities.Menu;
import com.tsolution.sso._3service.MenuService;
import com.tsolution.sso.exceptions.BusinessException;

@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOne(@PathVariable("id") Long menuId) {
		return this.menuService.getOne(menuId);
	}

	@GetMapping("/find")
	public ResponseEntity<Object> find(@RequestParam(value = "clientId", required = false) String clientId,
			@RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "pageNumber") Integer pageNumber, @RequestParam(value = "pageSize") Integer pageSize)
			throws BusinessException {
		text = text == null ? "" : text;
		clientId = clientId == null ? "" : clientId;

		Menu menu = new Menu();
		menu.setClientId(clientId);
		menu.setUrl(text);
		menu.setCode(text);
		menu.setAppType(text);
		return this.menuService.find(menu, PageRequest.of(pageNumber - 1, pageSize));
	}

	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll() {
		return this.menuService.getAll();
	}

	@GetMapping("/client-id")
	public ResponseEntity<Object> findByClientId(@RequestParam(value = "client-id") String clientId) {
		return this.menuService.findByClientId(clientId);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Optional<Menu> oMenuEntity) throws BusinessException {
		if (oMenuEntity.isPresent()) {
			return this.menuService.create(oMenuEntity.get());
		}
		throw new BusinessException(BusinessException.COMMON_INPUT_INFO_INVALID);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody Optional<Menu> oMenuEntity)
			throws BusinessException {
		if (oMenuEntity.isPresent()) {
			return this.menuService.update(id, oMenuEntity.get());
		}
		throw new BusinessException(BusinessException.COMMON_INPUT_INFO_INVALID);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws BusinessException {
		return this.menuService.delete(id);
	}

}
