package com.craftdemo.requestfilter;

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

public class TracingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String traceId = ((HttpServletRequest) request).getHeader(REQUEST_HEADER_TRACE_ID);
        traceId = StringUtils.isEmpty(traceId) ? RequestContext.generateTraceId() : traceId;
        MDC.put(THREAD_CONTEXT_TRACE_ID, traceId);
        CustomHeaderRequestWrapper requestWrapper = new CustomHeaderRequestWrapper((HttpServletRequest) request);
        requestWrapper.addHeader(REQUEST_HEADER_TRACE_ID, traceId);
        chain.doFilter(requestWrapper, response);
        MDC.clear();
    }
}
