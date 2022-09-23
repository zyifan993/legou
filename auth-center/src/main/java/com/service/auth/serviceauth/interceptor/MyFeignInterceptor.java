package com.service.auth.serviceauth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
public class MyFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null){
            //获取请求对象
            HttpServletRequest request = requestAttributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null){
                //2.获取请求对象中的所有的头信息(请求传递过来的)
                while ((headerNames.hasMoreElements())){
                    //头的名称
                    String name = headerNames.nextElement();
                    //值
                    String value = request.getHeader(name);
                    System.out.println("name:" + name + "::::::::value:" + value);
                    //将头信息传递给fegin (restTemplate)
                    template.header(name,value);
                }
            }
        }
    }
}
