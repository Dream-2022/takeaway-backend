package com.example.takeawaybackend.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 配置跨域
 */
@WebFilter("/**")
@Order(-1)
@Component
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("跨域问题拦截器");
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // 不使用*，自动适配跨域域名，避免携带Cookie时失效
        String origin = request.getHeader("Origin");
        if(StringUtils.isNotBlank(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        //response.setHeader("Access-Control-Allow-Origin","http://localhost:5173");

        System.out.println(response);

        // 自适应所有自定义头
        String headers = request.getHeader("Access-Control-Request-Headers");
        if(StringUtils.isNotBlank(headers)) {
            response.setHeader("Access-Control-Allow-Headers", headers);
            response.setHeader("Access-Control-Expose-Headers", "*,long_token,token"); // 修改这行
        }
        else response.setHeader("Access-Control-Allow-Headers","Content-Type, token,long_token");
        //response.setHeader("Access-Control-Allow-Headers","Content-Type, token,long_token");
        response.setHeader("Access-Control-Expose-Headers", "*,long_token,token");

        // 允许跨域的请求方法类型
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,OPTIONS,PUT");
        // 预检命令（OPTIONS）缓存时间，单位：秒


        response.setHeader("Access-Control-Max-Age", "3600");
        // 明确许可客户端发送Cookie，不允许删除字段即可
        response.setHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}

////@WebFilter("/api/*")
//public class CrosFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse resp=(HttpServletResponse)servletResponse;
//        HttpServletRequest req=(HttpServletRequest)servletRequest;
//
//        //设置cors：允许跨域访问
//
////        预检请求方式是：OPTIONS
//
////        1\允许哪些域可以访问我
//        resp.setHeader("Access-Control-Allow-Origin","Origin");
////        resp.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
////        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
//
////        2、允许哪些请求方式
//        resp.setHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT,OPTIONS");
//
////        3、请求头中包含
//        resp.setHeader("Access-Control-Allow-Headers","token,long_token");
////        resp.setHeader("Access-Control-Allow-Headers","long_token");
//
////        4、跨域是否允许携带cookie
//        resp.setHeader("Access-Control-Allow-Credentials","true");
//
////        5、预检请求的时间间隔
//        resp.setHeader("Access-Control-Max-Age","3600");
//
////        6、 可以向客户端浏览器暴露哪些请求头
//        resp.setHeader("Access-Control-Expose-Headers", "token,long_token");
//
////        将请求交给目标资源
//        filterChain.doFilter(req, resp);
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}

