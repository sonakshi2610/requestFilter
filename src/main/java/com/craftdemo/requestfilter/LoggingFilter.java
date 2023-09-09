package com.craftdemo.requestfilter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.craftdemo.requestfilter.Constants.REQUEST_HEADER_TRACE_ID;
import static com.craftdemo.requestfilter.Constants.THREAD_CONTEXT_TRACE_ID;


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

