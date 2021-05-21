package com.tsolution.sso._2repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.tsolution.sso._1entities.Menu;
import com.tsolution.sso._2repository.MenuRepositoryCustom;

public class MenuRepositoryCustomImpl implements MenuRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Menu> findMenuEntityCustom(Menu menu, Pageable pageable) {
		List<Menu> result = this.getMenuEntities(menu, pageable);
		Long total = this.getTotalMenuEntity(menu);
		return new PageImpl<>(result, pageable, total);
	}

	private Long getTotalMenuEntity(Menu menu) {
		Query countQuery = this.em.createQuery(this.createHql("COUNT(*)"));
		this.setParameterQuery(menu, countQuery);
		return (Long) countQuery.getSingleResult();
	}

	private List<Menu> getMenuEntities(Menu menu, Pageable pageable) {
		TypedQuery<Menu> query = this.em.createQuery(this.createHql("m"), Menu.class);
		this.setParameterQuery(menu, query);
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return query.getResultList();
	}

	private String createHql(String select) {
		return " SELECT " + select + " FROM Menu m " + " WHERE " + " UPPER(m.clientId) LIKE :clientId "
				+ " AND ( UPPER(m.appType) LIKE :appType  " + " OR UPPER(m.code) LIKE :code "
				+ " OR UPPER(m.url) LIKE :url ) ";
	}

	private void setParameterQuery(Menu menu, Query query) {
		query.setParameter("clientId", "%" + menu.getClientId().toUpperCase() + "%");
		query.setParameter("appType", "%" + menu.getAppType().toUpperCase() + "%");
		query.setParameter("code", "%" + menu.getCode().toUpperCase() + "%");
		query.setParameter("url", "%" + menu.getUrl().toUpperCase() + "%");
	}

}
