package com.tsolution.sso._2repository;

import java.time.LocalDateTime;
import java.util.List;

import com.tsolution.sso._1entities.LoginLog;
import com.tsolution.sso._1entities.enums.LoginStatus;
import com.tsolution.sso.exceptions.BusinessException;

public interface LoginLogRepositoryCustom {
	void logLogin(String username, String ipAddress, LoginStatus loginStatus) throws BusinessException;

	Integer loginSuccessToday(String username) throws BusinessException;

	List<LoginLog> loginHistory(String username, LocalDateTime fromDate, LocalDateTime toDate,
			Boolean isGroupByDate) throws BusinessException;
}
