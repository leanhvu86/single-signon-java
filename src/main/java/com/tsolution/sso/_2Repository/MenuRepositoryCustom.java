package com.tsolution.sso._2repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tsolution.sso._1entities.Menu;

public interface MenuRepositoryCustom {

	Page<Menu> findMenuEntityCustom(Menu menu, Pageable pageable);

}
