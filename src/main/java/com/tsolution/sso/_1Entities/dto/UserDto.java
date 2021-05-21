package com.tsolution.sso._1entities.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.tsolution.sso._1entities.Role;

public class UserDto extends User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3576481018432954113L;
	private String firstName;
	private String lastName;
	private List<Role> roles;
	private Boolean enabled;
	private Integer loginSuccessToday;
	private Boolean mustChangePassword;
	private Integer attemptLoginFailed;

	public UserDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getLoginSuccessToday() {
		return this.loginSuccessToday;
	}

	public void setLoginSuccessToday(Integer loginSuccessToday) {
		this.loginSuccessToday = loginSuccessToday;
	}

	public Boolean getMustChangePassword() {
		return this.mustChangePassword;
	}

	public void setMustChangePassword(Boolean mustChangePassword) {
		this.mustChangePassword = mustChangePassword;
	}

	public Integer getAttemptLoginFailed() {
		return this.attemptLoginFailed;
	}

	public void setAttemptLoginFailed(Integer attemptLoginFailed) {
		this.attemptLoginFailed = attemptLoginFailed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + Objects.hash(this.attemptLoginFailed, this.enabled, this.firstName, this.lastName,
				this.loginSuccessToday, this.mustChangePassword, this.roles);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof UserDto)) {
			return false;
		}
		UserDto other = (UserDto) obj;
		return Objects.equals(this.attemptLoginFailed, other.attemptLoginFailed)
				&& Objects.equals(this.enabled, other.enabled) && Objects.equals(this.firstName, other.firstName)
				&& Objects.equals(this.lastName, other.lastName)
				&& Objects.equals(this.loginSuccessToday, other.loginSuccessToday)
				&& Objects.equals(this.mustChangePassword, other.mustChangePassword)
				&& Objects.equals(this.roles, other.roles);
	}

}
