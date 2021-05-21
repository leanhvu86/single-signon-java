package com.tsolution.sso._1entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tsolution.sso.utils.Constants;

@Entity
@Table(name = "count_action_log")
public class CountActionLog implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5526829962290736629L;

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

	@Column(name = "total_action")
	private Long totalAction;

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

	public Long getTotalAction() {
		return this.totalAction;
	}

	public void setTotalAction(Long totalAction) {
		this.totalAction = totalAction;
	}

}
