package com.tsolution.sso.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@Order(1)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	// @Value("${security.Logistics.signing-key}")
	private String signingKey = "MaYzkSjmkzPC57L";

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	@Autowired
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/languages**", "/swagger**/**", "/webjars/**", "/v2/api-docs", "/api/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous().disable().requestMatcher(new BasicRequestMatcher()).authorizeRequests()
				.antMatchers("/oauth/**", "/languages**", "/swagger**/**", "/webjars/**", "/v2/api-docs", "/api/**")
				.permitAll().antMatchers("/**").authenticated().and()//
				.httpBasic().disable().cors().and().csrf().disable()//
				.headers().frameOptions().disable().and()//
				.formLogin().permitAll();
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(this.signingKey);
		return converter;
	}

	@Bean
	public TokenStore tokenStore(JdbcTemplate jdbcTemplate) {
		return new JdbcTokenStore(jdbcTemplate.getDataSource());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices(JdbcTemplate jdbcTemplate) {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(this.tokenStore(jdbcTemplate));
		defaultTokenServices.setSupportRefreshToken(true);
		// Token Timeout. With this is 1 day ^^
		defaultTokenServices.setAccessTokenValiditySeconds(24 * 60 * 60);
		defaultTokenServices.setRefreshTokenValiditySeconds(30 * 24 * 60 * 60);
		return defaultTokenServices;
	}

	protected static class BasicRequestMatcher implements RequestMatcher {
		@Override
		public boolean matches(HttpServletRequest request) {
			String auth = request.getHeader("Authorization");
			return ((auth != null) && auth.toUpperCase().startsWith("BEARER"));
		}
	}

}