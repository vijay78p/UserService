package com.mylearnings.user.service.config.interceptors;

import com.mylearnings.user.service.controllers.UserController;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Autowired
    private OAuth2AuthorizedClientManager manager;
    private Logger logger= LoggerFactory.getLogger(FeignClientInterceptor.class);
    @Override
    public void apply(RequestTemplate requestTemplate) {
         String token= manager.authorize(OAuth2AuthorizeRequest
                            .withClientRegistrationId("my-internal-client")
                            .principal("internal")
                            .build()).getAccessToken().getTokenValue();
        logger.info("token: ",token);
        requestTemplate.header("Authorization","Bearer"+token);
    }
}
