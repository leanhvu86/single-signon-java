package com.tsolution.sso._1entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tsolution.sso._1entities.enums.LoginStatus;
import com.tsolution.sso.utils.Constants;

@Entity
@Table(name = "login_log")
public class LoginLog implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5184959351009938018L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "log_date", nullable = false)
	@JsonFormat(pattern = Constants.DATE_PATTERN, shape = JsonFormat.Shape.STRING)
	private LocalDateTime logDate;

	@Column(name = "month", nullable = false)
	private Integer month;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private LoginStatus status;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getLogDate() {
		return this.logDate;
	}

	public void setLogDate(LocalDateTime logDate) {
		this.logDate = logDate;
	}

	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public LoginStatus getStatus() {
		return this.status;
	}

	public void setStatus(LoginStatus status) {
		this.status = status;
	}

}
