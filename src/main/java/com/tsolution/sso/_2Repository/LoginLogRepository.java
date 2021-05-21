package com.tsolution.sso._2repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tsolution.sso._1entities.LoginLog;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, Long>,
		JpaSpecificationExecutor<LoginLog>, LoginLogRepositoryCustom {
}
