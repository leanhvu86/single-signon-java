package com.tsolution.sso._2repository.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.tsolution.sso._1entities.CountActionLog;
import com.tsolution.sso._2repository.CountActionLogRepositoryCustom;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.DatetimeUtils;

@Repository
public class CountActionLogRepositoryCustomImpl implements CountActionLogRepositoryCustom {

	private static final String USERNAME2 = "username";
	@PersistenceContext
	private EntityManager em;

	@Override
	public void increaseTotalAction(String username) throws BusinessException {
		StringBuilder strQuery = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		strQuery.append(" INSERT INTO count_action_log(username, log_date, month, total_action) ");
		strQuery.append(" SELECT :username, DATE(SYSDATE()), MONTH(SYSDATE()), 1 ");
		strQuery.append(" ON DUPLICATE KEY UPDATE total_action = total_action + 1");

		params.put(CountActionLogRepositoryCustomImpl.USERNAME2, username);

		BaseRepository.executeCUD(this.em, strQuery.toString(), params);
	}

	@Override
	public List<CountActionLog> getTotalAction(List<String> username, LocalDateTime fromDate,
			LocalDateTime toDate) throws BusinessException {
		StringBuilder strQuery = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

//		strQuery.append(" SELECT NVL(SUM(cal.total_action), 0) ")
		strQuery.append(" SELECT cal.* ");
		strQuery.append(" FROM count_action_log cal ");
		strQuery.append(" WHERE cal.username IN (:username) ");
		strQuery.append("	AND DATE(:fromDate) <= cal.log_date ");
		strQuery.append("	AND cal.log_date < DATE(DATE_ADD(:toDate, INTERVAL 1 DAY)) ");

		params.put(CountActionLogRepositoryCustomImpl.USERNAME2, username.isEmpty() ? "N/A" : username);
		params.put("fromDate", DatetimeUtils.localDateTime2SqlDate(fromDate));
		params.put("toDate", DatetimeUtils.localDateTime2SqlDate(toDate));

		return BaseRepository.getResultListNativeQuery(this.em, strQuery.toString(), params,
				CountActionLog.class);
	}

}
