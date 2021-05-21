package com.tsolution.sso._3service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tsolution.sso._1entities.User;
import com.tsolution.sso._1entities.dto.UserDto;
import com.tsolution.sso._2repository.UserRepository;

@Service("userDetailsService")
public class LoginServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> oUser = this.userRepository.findOneByUsername(username);
		if (!oUser.isPresent()) {
			throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
		}
		User user = oUser.get();
		if (Boolean.FALSE.equals(user.getEnabled())) {
			throw new UsernameNotFoundException("Locked");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("**/**"));
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			role.getPermissions()
					.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getUrl())));
		});
		Integer loginSuccessToday = 0;
		UserDto userDto = new UserDto(user.getUsername(), user.getPassword(),
				authorities);
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setRoles(user.getRoles());
		userDto.setEnabled(user.getEnabled());
		userDto.setLoginSuccessToday(loginSuccessToday);
		userDto.setMustChangePassword(user.getMustChangePassword());
		userDto.setAttemptLoginFailed(user.getAttemptLoginFailed());
		return userDto;
	}
}
