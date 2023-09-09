package com.craftdemo.requestfilter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

import static com.craftdemo.requestfilter.Constants.REQUEST_HEADER_TRACE_ID;
import static com.craftdemo.requestfilter.Constants.THREAD_CONTEXT_TRACE_ID;
import static com.craftdemo.requestfilter.Constants.UNDER_SCORE;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestContext {
    public static String getTraceId() {
        Object obj = MDC.get(THREAD_CONTEXT_TRACE_ID);
        if (obj == null) {
            MDC.put(THREAD_CONTEXT_TRACE_ID, generateTraceId());
        }
        return MDC.get(THREAD_CONTEXT_TRACE_ID);
    }

    public static String getHeaderTracer(HttpServletRequest request) {
        return request.getHeader(REQUEST_HEADER_TRACE_ID);
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace(UNDER_SCORE, StringUtils.EMPTY);
    }

    public static Map<String, String> getThreadContextMap() {
        return MDC.getCopyOfContextMap();
    }
}
