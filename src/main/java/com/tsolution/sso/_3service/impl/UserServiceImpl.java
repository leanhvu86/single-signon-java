package com.tsolution.sso._3service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsolution.sso._1entities.Role;
import com.tsolution.sso._1entities.User;
import com.tsolution.sso._2repository.CountActionLogRepository;
import com.tsolution.sso._2repository.LoginLogRepository;
import com.tsolution.sso._2repository.UserRepository;
import com.tsolution.sso._3service.UserService;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.StringUtils;
import com.tsolution.sso.utils.Translator;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final String USER_USERNAME_IS_NOT_EXISTS = "user.username.is.not.exists";

	private static final String COMMON_ACCESS_DENIED = "common.access.denied";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private LoginLogRepository loginLogRepository;

	@Autowired
	private CountActionLogRepository countActionLogRepository;

	@Autowired
	private Translator translator;

	@Value("${sso.default.password}")
	private String defaultPassword;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> changePassword(String oldPassword, String newPassword, String newConfirmPassword)
			throws BusinessException {
		if (StringUtils.isNullOrEmpty(oldPassword) || StringUtils.isNullOrEmpty(newPassword)
				|| StringUtils.isNullOrEmpty(newConfirmPassword)) {
			throw new BusinessException(this.translator.toLocale(BusinessException.COMMON_INPUT_INFO_INVALID));
		}
		if (!newPassword.equalsIgnoreCase(newConfirmPassword)) {
			throw new BusinessException(this.translator.toLocale("user.input.new.password.invalid"));
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			throw new BusinessException(this.translator.toLocale(UserServiceImpl.COMMON_ACCESS_DENIED));
		}
		Optional<User> oUser = this.userRepository.findOneByUsername(authentication.getName());
		if (!oUser.isPresent()) {
			throw new BusinessException(this.translator
					.toLocaleByFormatString(UserServiceImpl.USER_USERNAME_IS_NOT_EXISTS, authentication.getName()));
		}
		User user = oUser.get();
		if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new BusinessException(this.translator.toLocale("user.input.old.password.invalid"));
		}
		if (this.passwordEncoder.matches(newPassword, user.getPassword())) {
			throw new BusinessException(this.translator.toLocale("user.input.old.password.not.same.new.password"));
		}
		user.setPassword(this.passwordEncoder.encode(newPassword));
		user.setMustChangePassword(false);
		user = this.userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> active(String username) throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			throw new BusinessException(this.translator.toLocale(UserServiceImpl.COMMON_ACCESS_DENIED));
		}
		Optional<User> oUser = this.userRepository.findOneByUsername(username);
		if (!oUser.isPresent()) {
			throw new BusinessException(
					this.translator.toLocaleByFormatString(UserServiceImpl.USER_USERNAME_IS_NOT_EXISTS, username));
		}
		User user = oUser.get();
		user.setAttemptLoginFailed(0);
		user.setEnabled(true);
		user = this.userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> deactive(String username) throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			throw new BusinessException(this.translator.toLocale(UserServiceImpl.COMMON_ACCESS_DENIED));
		}
		Optional<User> oUser = this.userRepository.findOneByUsername(username);
		if (!oUser.isPresent()) {
			throw new BusinessException(
					this.translator.toLocaleByFormatString(UserServiceImpl.USER_USERNAME_IS_NOT_EXISTS, username));
		}
		User user = oUser.get();
		user.setAttemptLoginFailed(0);
		user.setEnabled(false);
		user = this.userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> resetPassword(String username) throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			throw new BusinessException(this.translator.toLocale(UserServiceImpl.COMMON_ACCESS_DENIED));
		}
		Optional<User> oUser = this.userRepository.findOneByUsername(username);
		if (!oUser.isPresent()) {
			throw new BusinessException(
					this.translator.toLocaleByFormatString(UserServiceImpl.USER_USERNAME_IS_NOT_EXISTS, username));
		}
		User user = oUser.get();
		user.setPassword(this.passwordEncoder.encode(this.defaultPassword));
		user.setMustChangePassword(true);
		user = this.userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getOne(Long id) throws BusinessException {
		Optional<User> oUser = this.userRepository.findById(id);
		if (!oUser.isPresent()) {
			throw new BusinessException(this.translator.toLocale("user.username.is.exists"));
		}
		return new ResponseEntity<>(oUser.get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getStatusByUsername(String username) throws BusinessException {
		Optional<User> oUser = this.userRepository.findOneByUsername(username);
		if (!oUser.isPresent()) {
			throw new BusinessException(
					this.translator.toLocaleByFormatString(UserServiceImpl.USER_USERNAME_IS_NOT_EXISTS, username));
		}
		return new ResponseEntity<>(oUser.get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getUserRole(String username) throws BusinessException {
		Optional<User> oUser = this.userRepository.findOneByUsername(username);
		if (!oUser.isPresent()) {
			throw new BusinessException(
					this.translator.toLocaleByFormatString(UserServiceImpl.USER_USERNAME_IS_NOT_EXISTS, username));
		}
		return new ResponseEntity<>(oUser.get().getRoles(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> find(String firstName, String lastName, String username, Pageable pageable) {
		return new ResponseEntity<>(this.userRepository
				.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingOrUsernameIgnoreCaseContaining(
						firstName, lastName, username, pageable),
				HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> create(User userEntityInput) throws BusinessException {
		Optional<User> oUser = this.userRepository.findOneByUsername(userEntityInput.getUsername());
		if (oUser.isPresent()) {
			throw new BusinessException(
					this.translator.toLocaleByFormatString("user.username.is.exists", userEntityInput.getUsername()));
		}
		User user = new User();
		user.setUsername(userEntityInput.getUsername());
		user.setFirstName(userEntityInput.getFirstName());
		user.setLastName(userEntityInput.getLastName());
		if (StringUtils.isNullOrEmpty(userEntityInput.getPassword())) {
			user.setPassword(this.passwordEncoder.encode(this.defaultPassword));
		} else {
			user.setPassword(this.passwordEncoder.encode(userEntityInput.getPassword()));
		}
		user.setEnabled(userEntityInput.getEnabled());
		if (user.getEnabled() == null) {
			user.setEnabled(false);
		}
		user.setMustChangePassword(true);
		user.setAttemptLoginFailed(0);
		user = this.userRepository.save(user);

		if ((userEntityInput.getRoles() != null) && !userEntityInput.getRoles().isEmpty()) {
			List<Role> roles = new ArrayList<>();
			userEntityInput.getRoles().forEach(roles::add);
			user.setRoles(roles);
		}
		return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> update(Long id, User userEntityInput) throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			throw new BusinessException(this.translator.toLocale(UserServiceImpl.COMMON_ACCESS_DENIED));
		}
		Optional<User> oUser = this.userRepository.findOneByUsername(userEntityInput.getUsername());
		if (!oUser.isPresent()) {
			throw new BusinessException(this.translator.toLocaleByFormatString(
					UserServiceImpl.USER_USERNAME_IS_NOT_EXISTS, userEntityInput.getUsername()));
		}
		if (id > 0) {
			oUser = this.userRepository.findById(id);
			if (!oUser.isPresent()) {
				throw new BusinessException(this.translator.toLocaleByFormatString(
						UserServiceImpl.USER_USERNAME_IS_NOT_EXISTS, userEntityInput.getUsername()));
			}
		}
		User user = oUser.get();
		user.setFirstName(userEntityInput.getFirstName());
		user.setLastName(userEntityInput.getLastName());
		List<Role> roles = new ArrayList<>();
		if ((userEntityInput.getRoles() != null)) {
			userEntityInput.getRoles().forEach(roles::add);
		}
		user.setRoles(roles);
		return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> delete(Long id) throws BusinessException {
		this.userRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> loginHistory(String username, LocalDateTime fromDate, LocalDateTime toDate,
			Boolean isGroupByDate) throws BusinessException {
		return new ResponseEntity<>(this.loginLogRepository.loginHistory(username, fromDate, toDate, isGroupByDate),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getTotalAction(List<String> username, LocalDateTime fromDate, LocalDateTime toDate)
			throws BusinessException {
		return new ResponseEntity<>(this.countActionLogRepository.getTotalAction(username, fromDate, toDate),
				HttpStatus.OK);
	}

}
