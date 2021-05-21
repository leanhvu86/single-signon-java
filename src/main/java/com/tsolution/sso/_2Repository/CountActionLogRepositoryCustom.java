package com.tsolution.sso._2repository;

import java.time.LocalDateTime;
import java.util.List;

import com.tsolution.sso._1entities.CountActionLog;
import com.tsolution.sso.exceptions.BusinessException;

public interface CountActionLogRepositoryCustom {
	void increaseTotalAction(String username) throws BusinessException;

	List<CountActionLog> getTotalAction(List<String> username, LocalDateTime fromDate, LocalDateTime toDate)
			throws BusinessException;
}
