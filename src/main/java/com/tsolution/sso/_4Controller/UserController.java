package com.tsolution.sso._4controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.tsolution.sso._1entities.User;
import com.tsolution.sso._3service.UserService;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.Constants;
import com.tsolution.sso.utils.Translator;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private Translator translator;

	@GetMapping("/me")
	public Principal user(Principal principal) {
		return principal;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOne(@PathVariable("id") Long id) throws BusinessException {
		return this.userService.getOne(id);
	}

	@GetMapping("/{username}/status")
	public ResponseEntity<Object> getStatusByUsername(@PathVariable("username") String username)
			throws BusinessException {
		return this.userService.getStatusByUsername(username);
	}

	@GetMapping("/{username}/role")
	public ResponseEntity<Object> getUserRole(@PathVariable("username") String username) throws BusinessException {
		return this.userService.getUserRole(username);
	}

	@GetMapping("/find")
	public ResponseEntity<Object> getAll(@RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "pageSize", required = true) Integer pageSize) throws BusinessException {
		text = text == null ? "" : text;
		return this.userService.find(text, text, text, PageRequest.of(pageNumber - 1, pageSize));
	}

	@PostMapping("/change-password")
	public ResponseEntity<Object> changePassword(
			@RequestParam(value = "oldPassword", required = true) String oldPassword,
			@RequestParam(value = "newPassword", required = true) String newPassword,
			@RequestParam(value = "newConfirmPassword", required = true) String newConfirmPassword)
			throws BusinessException {
		return this.userService.changePassword(oldPassword, newPassword, newConfirmPassword);
	}

	@PostMapping("/active")
	public ResponseEntity<Object> active(@RequestBody(required = true) String username) throws BusinessException {
		return this.userService.active(username);
	}

	@PostMapping("/deactive")
	public ResponseEntity<Object> deactive(@RequestBody(required = true) String username) throws BusinessException {
		return this.userService.deactive(username);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Object> resetPassword(@RequestBody(required = true) String username)
			throws BusinessException {
		return this.userService.resetPassword(username);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Optional<User> userEntityInput) throws BusinessException {
		if (!userEntityInput.isPresent()) {
			throw new BusinessException(this.translator.toLocale(BusinessException.COMMON_INPUT_INFO_INVALID));
		}
		return this.userService.create(userEntityInput.get());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody Optional<User> userEntityInput)
			throws BusinessException {
		if (!userEntityInput.isPresent()) {
			throw new BusinessException(this.translator.toLocale(BusinessException.COMMON_INPUT_INFO_INVALID));
		}
		return this.userService.update(id, userEntityInput.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws BusinessException {
		return this.userService.delete(id);
	}

	@GetMapping("/login-history")
	public ResponseEntity<Object> loginHistory(@RequestParam(required = true) String username,
			@RequestParam(required = true) @DateTimeFormat(pattern = Constants.DATE_PATTERN) LocalDateTime fromDate,
			@RequestParam(required = true) @DateTimeFormat(pattern = Constants.DATE_PATTERN) LocalDateTime toDate,
			@RequestParam(required = false) Boolean isGroupByDate) throws BusinessException {
		return this.userService.loginHistory(username, fromDate, toDate, isGroupByDate);
	}

	@GetMapping("/total-action")
	public ResponseEntity<Object> loginHistory(@RequestParam(required = true) List<String> username,
			@RequestParam(required = true) @DateTimeFormat(pattern = Constants.DATE_PATTERN) LocalDateTime fromDate,
			@RequestParam(required = true) @DateTimeFormat(pattern = Constants.DATE_PATTERN) LocalDateTime toDate)
			throws BusinessException {
		return this.userService.getTotalAction(username, fromDate, toDate);
	}
}
