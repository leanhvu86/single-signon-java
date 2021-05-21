package com.tsolution.sso._2repository.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.tsolution.sso._1entities.LoginLog;
import com.tsolution.sso._1entities.enums.LoginStatus;
import com.tsolution.sso._2repository.LoginLogRepositoryCustom;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.DatetimeUtils;

@Repository
public class LoginLogRepositoryCustomImpl implements LoginLogRepositoryCustom {

	private static final String STATUS = "status";
	private static final String USERNAME2 = "username";
	@PersistenceContext
	private EntityManager em;

	@Override
	public void logLogin(String username, String ipAddress, LoginStatus loginStatus) throws BusinessException {
		StringBuilder strQuery = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		strQuery.append(" INSERT INTO login_log(username, log_date, month, ip_address, status) ");
		strQuery.append("		 VALUES (:username, SYSDATE(), MONTH(SYSDATE()), :ipAddress, :status) ");

		params.put(LoginLogRepositoryCustomImpl.USERNAME2, username);
		params.put(LoginLogRepositoryCustomImpl.STATUS, loginStatus.getValue());
		params.put("ipAddress", ipAddress);

		BaseRepository.executeCUD(this.em, strQuery.toString(), params);
	}

	@Override
	public Integer loginSuccessToday(String username) throws BusinessException {
		StringBuilder strQuery = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		strQuery.append(" SELECT COUNT(ll.id) + 1 ");
		strQuery.append(" FROM login_log ll ");
		strQuery.append(" WHERE ll.username LIKE :username ");
		strQuery.append("	AND DATE(SYSDATE()) <= ll.log_date ");
		strQuery.append("	AND ll.log_date <= SYSDATE() ");
		strQuery.append("	AND ll.status LIKE :status ");

		params.put(LoginLogRepositoryCustomImpl.USERNAME2, username);
		params.put(LoginLogRepositoryCustomImpl.STATUS, LoginStatus.SUCCESS.getValue());

		Number loginSuccessToday = BaseRepository.getScalarResult(this.em, strQuery.toString(), params, Number.class);

		return loginSuccessToday == null ? 0 : loginSuccessToday.intValue();
	}

	@Override
	public List<LoginLog> loginHistory(String username, LocalDateTime fromDate, LocalDateTime toDate,
			Boolean isGroupByDate) throws BusinessException {
		StringBuilder strQuery = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		if (Boolean.TRUE.equals(isGroupByDate)) {
			strQuery.append(" SELECT min(ll.id) id, ll.username, DATE(ll.log_date) log_date, ");
			strQuery.append("		 month, null ip_address, ll.status  ");
		} else {
			strQuery.append(" SELECT ll.* ");
		}
		strQuery.append(" FROM login_log ll ");
		strQuery.append(" WHERE ll.username LIKE :username ");
		strQuery.append("	AND DATE(:fromDate) <= ll.log_date ");
		strQuery.append("	AND ll.log_date < DATE(DATE_ADD(:toDate, INTERVAL 1 DAY)) ");
		strQuery.append("	AND ll.status LIKE :status ");
		if (Boolean.TRUE.equals(isGroupByDate)) {
			strQuery.append(" GROUP BY ll.username, DATE(ll.log_date), month, ll.status ");
		}
		strQuery.append(" ORDER BY ll.log_date DESC ");

		params.put(LoginLogRepositoryCustomImpl.USERNAME2, username);
		params.put("fromDate", DatetimeUtils.localDateTime2SqlDate(fromDate));
		params.put("toDate", DatetimeUtils.localDateTime2SqlDate(toDate));
		params.put(LoginLogRepositoryCustomImpl.STATUS, LoginStatus.SUCCESS.getValue());

		return BaseRepository.getResultListNativeQuery(this.em, strQuery.toString(), params, LoginLog.class);
	}

}
