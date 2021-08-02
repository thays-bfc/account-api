package com.bank.api.account.config.swagger;


import com.bank.api.account.model.Account;
import com.bank.api.account.model.AccountTransaction;
import com.bank.api.account.model.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfigurations {

    @Bean
    public Docket accountApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bank.api.account"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(Account.class)
                .ignoredParameterTypes(Customer.class)
                .ignoredParameterTypes(AccountTransaction.class)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Account API Documentation",
                "Bank - Account management",
                "1.0",
                "",
                new Contact("Backend Team", "", ""),
                "License of API", "", Collections.emptyList());
    }
}

