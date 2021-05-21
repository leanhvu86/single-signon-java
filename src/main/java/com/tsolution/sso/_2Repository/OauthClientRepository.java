package com.tsolution.sso._2repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsolution.sso._1entities.OauthClientDetails;

@Repository
public interface OauthClientRepository
		extends JpaRepository<OauthClientDetails, String>, OauthClientRepositoryCustom {

}
