package com.craftdemo.requestfilter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;


@Slf4j
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("Request for URL came : {}", ((CustomHeaderRequestWrapper) request).getRequestURL());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        MDC.clear();
    }
}

