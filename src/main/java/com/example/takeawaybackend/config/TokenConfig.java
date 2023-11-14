package com.example.takeawaybackend.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/api/*")
public class TokenConfig implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, IOException {
        HttpServletResponse resp=(HttpServletResponse)servletResponse;
        HttpServletRequest req=(HttpServletRequest)servletRequest;

        //设置cors：允许跨域访问

//        预检请求方式是：OPTIONS

//        1\允许哪些域可以访问我
        resp.setHeader("Access-Control-Allow-Origin","Origin");
//        resp.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
//        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));

//        2、允许哪些请求方式
        resp.setHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT,OPTIONS");

//        3、请求头中包含
        resp.setHeader("Access-Control-Allow-Headers","token,long_token");
//        resp.setHeader("Access-Control-Allow-Headers","long_token");

//        4、跨域是否允许携带cookie
        resp.setHeader("Access-Control-Allow-Credentials","true");

//        5、预检请求的时间间隔
        resp.setHeader("Access-Control-Max-Age","3600");

//        6、 可以向客户端浏览器暴露哪些请求头
        resp.setHeader("Access-Control-Expose-Headers", "token,long_token");

//        将请求交给目标资源
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}