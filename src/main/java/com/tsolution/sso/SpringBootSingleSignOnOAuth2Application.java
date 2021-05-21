package com.tsolution.sso;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.tsolution.sso.utils.StringUtils;

@SpringBootApplication
@EnableResourceServer
@ComponentScan("com.tsolution.sso.*")
public class SpringBootSingleSignOnOAuth2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSingleSignOnOAuth2Application.class, args);
	}

	private static final List<String> EXCLUDE_URL = Arrays.asList("/actuator");

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter() {
			@Override
			protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
				return SpringBootSingleSignOnOAuth2Application.EXCLUDE_URL.stream()
						.anyMatch(url -> !StringUtils.isNullOrEmpty(url)
								&& request.getServletPath().toLowerCase().startsWith(url.toLowerCase()));
			}
		};
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setIncludeHeaders(true);

		return loggingFilter;
	}

}
