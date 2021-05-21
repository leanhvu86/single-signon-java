package com.tsolution.sso._2repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tsolution.sso._1entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findOneByUsername(String username);

	@Query(value = "SELECT SYSDATE()", nativeQuery = true)
	LocalDateTime getSysdate();

	Page<User> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingOrUsernameIgnoreCaseContaining(
			String firstName, String lastName, String username, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE app_user u SET u.attempt_login_failed = u.attempt_login_failed + 1, u.enabled = CASE WHEN u.attempt_login_failed >= 5 OR u.enabled = 0 THEN 0 ELSE 1 END WHERE u.username = :username ", nativeQuery = true)
	void addMoreAttemptLoginFailed(String username);

	@Modifying
	@Query(value = "UPDATE app_user u SET u.attempt_login_failed = 0 WHERE u.username = :username ", nativeQuery = true)
	void resetAttemptLoginFailed(String username);
}
