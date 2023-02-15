package com.mylearnings.user.service.config;

import com.mylearnings.user.service.config.interceptors.RestTemplateInterceptor;
import com.mylearnings.user.service.controllers.UserController;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Log4j2
public class MyConfig {
    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Bean
    @LoadBalanced
     public RestTemplate restTemplate(){
        logger.info(" in resTemplate method");
        RestTemplate restTemplate = new RestTemplate();
//        List<ClientHttpRequestInterceptor> interceptorList=new ArrayList<>();
//        interceptorList.add(new RestTemplateInterceptor(manager(clientRegistrationRepository,oAuth2AuthorizedClientRepository)));
//        restTemplate.setInterceptors(interceptorList);
        return restTemplate;
    }

    @Bean
    public OAuth2AuthorizedClientManager manager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository){

        OAuth2AuthorizedClientProvider provider= OAuth2AuthorizedClientProviderBuilder
                                                    .builder()
                                                    .clientCredentials()
                                                    .build();
        DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager=new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository,oAuth2AuthorizedClientRepository
        );

        defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
        return defaultOAuth2AuthorizedClientManager;
    }
}
