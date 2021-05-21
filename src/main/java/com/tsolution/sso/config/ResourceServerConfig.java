package com.tsolution.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import com.tsolution.sso.config.WebSecurityConfigurer.BasicRequestMatcher;

@Order(3)
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private ResourceServerTokenServices tokenServices;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(this.tokenServices);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous().disable().requestMatcher(new BasicRequestMatcher()).authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/oauth/token", "/languages", "/swagger**/**", "/webjars/**",
						"/v2/api-docs", "/api/**")
				.permitAll().antMatchers("/**").authenticated().and().httpBasic().disable();
	}

}
