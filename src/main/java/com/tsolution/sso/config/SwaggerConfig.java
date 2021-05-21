package com.tsolution.sso.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${app.client.id}")
	private String clientId;
	@Value("${app.client.secret}")
	private String clientSecret;

	@Value("${host.auth.url}")
	private String authUrl;

	@Bean
	public Docket api() {
//        List<ResponseMessage> list = new java.util.ArrayList<>()
//        list.add(new ResponseMessageBuilder().code(500).message("500 message")
//                .responseModel(new ModelRef("Result")).build())
//        list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
//                .responseModel(new ModelRef("Result")).build())
//        list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
//                .responseModel(new ModelRef("Result")).build())

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(this.getApiInfo())
				.securitySchemes(Collections.singletonList(this.securitySchema()))
				.securityContexts(Collections.singletonList(this.securityContext())).pathMapping("/");

//                .useDefaultResponseMessages(false)

//        .globalResponseMessage(RequestMethod.GET, list)
//                .globalResponseMessage(RequestMethod.POST, list)

	}

	private OAuth securitySchema() {

//        List<AuthorizationScope> authorizationScopeList = newArrayList()
//        authorizationScopeList.add(new AuthorizationScope("read", "read all"))
//        authorizationScopeList.add(new AuthorizationScope("write", "access all"))

		List<GrantType> grantTypes = Lists.newArrayList();
		GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(this.authUrl + "/oauth/token");

		grantTypes.add(creGrant);

		return new OAuth("oauth2schema", Lists.newArrayList(), grantTypes);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(this.defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		return Collections.singletonList(new SecurityReference("oauth2schema", new AuthorizationScope[0]));
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Spring Boot REST API Swagger 2").description("Demo Spring Boot Swagger")
				.version("1.0.0").license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
				.contact(new Contact("Chan thoi", "abccba", "abccba@gmail,com")).build();
	}

}
