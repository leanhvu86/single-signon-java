package com.tsolution.sso._3service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.tsolution.sso._1entities.User;
import com.tsolution.sso.exceptions.BusinessException;

public interface UserService {
	ResponseEntity<Object> changePassword(String oldPassword, String newPassword, String newConfirmPassword)
			throws BusinessException;

	ResponseEntity<Object> active(String username) throws BusinessException;

	ResponseEntity<Object> deactive(String username) throws BusinessException;

	ResponseEntity<Object> resetPassword(String username) throws BusinessException;

	ResponseEntity<Object> find(String firstName, String lastName, String username, Pageable pageable)
			throws BusinessException;

	ResponseEntity<Object> create(User userEntityInput) throws BusinessException;

	ResponseEntity<Object> update(Long id, User userEntityInput) throws BusinessException;

	ResponseEntity<Object> delete(Long id) throws BusinessException;

	ResponseEntity<Object> getOne(Long id) throws BusinessException;

	ResponseEntity<Object> getUserRole(String username) throws BusinessException;

	ResponseEntity<Object> getStatusByUsername(String username) throws BusinessException;

	ResponseEntity<Object> loginHistory(String username, LocalDateTime fromDate, LocalDateTime toDate,
			Boolean isGroupByDate) throws BusinessException;

	ResponseEntity<Object> getTotalAction(List<String> username, LocalDateTime fromDate, LocalDateTime toDate)
			throws BusinessException;
}
