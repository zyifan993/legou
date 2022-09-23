package com.service.auth.serviceauth.interceptor;

import com.service.auth.serviceauth.utils.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = null;
        try {
            token = AdminToken.adminToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestTemplate.header("Authorization", "Bearer " + token);
    }
}
