package com.tsolution.sso._2repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tsolution.sso._1entities.OauthClientDetails;

public interface OauthClientRepositoryCustom {

	List<String> getAllClientId(String filterShowOnClient);

	Page<OauthClientDetails> findOauthClients(OauthClientDetails oauthClientDetails, Pageable pageable);
}
