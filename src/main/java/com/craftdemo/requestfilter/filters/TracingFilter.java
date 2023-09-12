package com.craftdemo.requestfilter.filters;

import com.craftdemo.requestfilter.util.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TracingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestHeaderTraceId = RequestContext.getHeaderTracer((HttpServletRequest) request);
        RequestContext.setTraceId(StringUtils.isEmpty(requestHeaderTraceId) ? RequestContext.generateNewTraceId() : requestHeaderTraceId);
        CustomHeaderRequestWrapper requestWrapper = new CustomHeaderRequestWrapper((HttpServletRequest) request);
        RequestContext.addHeaderTracer(requestWrapper);
        chain.doFilter(requestWrapper, response);
        MDC.clear();
    }
}
