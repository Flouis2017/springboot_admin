package com.flouis.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${swagger2.enable}")
	private boolean enable;

	@Bean
	public Docket createRestApi() {
		/**
		 * 这是为了我们在用 swagger 测试接口的时候添加头部信息
		 */
		List<Parameter> pars = Lists.newArrayList();
		ParameterBuilder tokenPar = new ParameterBuilder();
		ParameterBuilder refreshTokenPar = new ParameterBuilder();
		tokenPar.name("authorization").description("swagger测试用(模拟authorization传入)非必填header")
				.modelRef(new ModelRef("string")).parameterType("header").required(false);
		refreshTokenPar.name("refresh_token").description("swagger测试用(模拟刷新token传入)非必填header")
				.modelRef(new ModelRef("string")).parameterType("header").required(false);
		/**
		 * 多个的时候 就直接添加到 pars 就可以了
		 */
		pars.add(tokenPar.build());
		pars.add(refreshTokenPar.build());
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.flouis.controller"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars)
				.enable(enable);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Spring Boot Admin")
				.description("springboot project in action")
				.termsOfServiceUrl("")
				.version("1.0")
				.build();
	}

}
