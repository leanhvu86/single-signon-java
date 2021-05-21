package com.tsolution.sso._2repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.tsolution.sso._1entities.OauthClientDetails;
import com.tsolution.sso._2repository.OauthClientRepositoryCustom;
import com.tsolution.sso.utils.StringUtils;

public class OauthClientRepositoryCustomImpl implements OauthClientRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<String> getAllClientId(String filterShowOnClient) {
		String hql = "SELECT c.clientId FROM OauthClientDetails AS c ";
		if (!StringUtils.isNullOrEmpty(filterShowOnClient)) {
			hql += "WHERE LOWER(c.filterShowOnClient) LIKE LOWER(CONCAT('%','" + filterShowOnClient + "','%'))";
		}
		TypedQuery<String> query = this.em.createQuery(hql, String.class);
		return query.getResultList();
	}

	@Override
	public Page<OauthClientDetails> findOauthClients(OauthClientDetails oauthClientDetails, Pageable pageable) {
		List<OauthClientDetails> resultList = this.getOauthClientEntities(oauthClientDetails, pageable);
		Long total = this.getTotalOauthClientEntities(oauthClientDetails);
		return new PageImpl<>(resultList, pageable, total);
	}

	private Long getTotalOauthClientEntities(OauthClientDetails oauthClientDto) {
		Query countQuery = this.em.createQuery(this.createHql("SELECT COUNT(*) "));
		this.setParameter(oauthClientDto, countQuery);
		return (Long) countQuery.getSingleResult();
	}

	private List<OauthClientDetails> getOauthClientEntities(OauthClientDetails oauthClientDetails, Pageable pageable) {
		TypedQuery<OauthClientDetails> query = this.em.createQuery(this.createHql(" SELECT c "),
				OauthClientDetails.class);
		this.setParameter(oauthClientDetails, query);
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return query.getResultList();
	}

	private String createHql(String select) {
		return select + " FROM OauthClientDetails c " + " WHERE UPPER(c.clientId) LIKE :clientId "
				+ " OR UPPER(c.scope) LIKE :scope OR UPPER(c.authorities) LIKE :authorities ";
	}

	private void setParameter(OauthClientDetails oauthClientDetails, Query query) {
		query.setParameter("clientId", "%" + oauthClientDetails.getClientId().toUpperCase() + "%");
		query.setParameter("scope", "%" + oauthClientDetails.getScope().toUpperCase() + "%");
		query.setParameter("authorities", "%" + oauthClientDetails.getAuthorities().toUpperCase() + "%");
	}
}
