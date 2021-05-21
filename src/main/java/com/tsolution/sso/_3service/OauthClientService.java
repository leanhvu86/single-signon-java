package com.tsolution.sso._3service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.tsolution.sso._1entities.OauthClientDetails;
import com.tsolution.sso.exceptions.BusinessException;

public interface OauthClientService {

	ResponseEntity<Object> getAllClientId(String filterShowOnClient);

	ResponseEntity<Object> getOne(String clientId);

	ResponseEntity<Object> getAll();

	ResponseEntity<Object> create(OauthClientDetails oauthClientDetails) throws BusinessException;

	ResponseEntity<Object> update(String clientId, OauthClientDetails oauthClientEntityInput) throws BusinessException;

	ResponseEntity<Object> delete(String clientId) throws BusinessException;

	ResponseEntity<Object> find(OauthClientDetails oauthClientDetails, Pageable pageable);
}
