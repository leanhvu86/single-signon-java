package com.tsolution.sso._2repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsolution.sso._1entities.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom {

	Menu findOneByCode(String code);

	List<Menu> findAllByClientId(String clientId);

}
