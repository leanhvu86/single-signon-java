package com.tsolution.sso._2repository;

public class RoleSql {
	private RoleSql() {
	}

	public static final String FIND = " FROM app_role r WHERE (:clientId IS NULL OR LOWER(r.client_id) like LOWER(CONCAT('%',:clientId,'%')) ) "
			+ " AND (:text IS NULL OR LOWER(r.description) like LOWER(CONCAT('%',:text,'%')) OR LOWER(r.role_name) like LOWER(CONCAT('%',:text,'%')) ) ";
}
