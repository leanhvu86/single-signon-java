package com.tsolution.sso.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tsolution.sso._1entities.dto.UserDto;
import com.tsolution.sso._1entities.enums.LoginStatus;
import com.tsolution.sso._2repository.CountActionLogRepository;
import com.tsolution.sso._2repository.LoginLogRepository;
import com.tsolution.sso._2repository.UserRepository;
import com.tsolution.sso.exceptions.BusinessException;

@Component
public class AuthenticationEventListener {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginLogRepository loginLogRepository;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private CountActionLogRepository countActionLogRepository;

	@EventListener
	@Transactional
	public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) throws BusinessException {
		String username = (String) event.getAuthentication().getPrincipal();
		if (event.getException() != null) {
			this.userRepository.addMoreAttemptLoginFailed(username);
			this.loginLogRepository.logLogin(username, this.request.getRemoteAddr(), LoginStatus.FAILED);
		}
	}

	@EventListener
	@Transactional
	public void authenticationSuccess(AuthenticationSuccessEvent event) throws BusinessException {
		String remoteAddr = this.request.getRemoteAddr();
		if (!(event.getAuthentication().getPrincipal() instanceof UserDto)) {
			return;
		}
		UserDto userDto = (UserDto) event.getAuthentication().getPrincipal();
		if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
			if (!Boolean.TRUE.equals(userDto.getEnabled())) {
				return;
			}
			if (userDto.getAttemptLoginFailed() > 0) {
				this.userRepository.resetAttemptLoginFailed(userDto.getUsername());
			}
			this.loginLogRepository.logLogin(userDto.getUsername(), remoteAddr, LoginStatus.SUCCESS);
			this.countActionLogRepository.increaseTotalAction(userDto.getUsername());
		} else if (event.getSource() instanceof OAuth2Authentication) {
			this.countActionLogRepository.increaseTotalAction(userDto.getUsername());
		}
	}
}
