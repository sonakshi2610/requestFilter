package com.craftdemo.requestfilter.httpClients;

import com.craftdemo.requestfilter.util.RequestContext;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Response;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.craftdemo.requestfilter.util.Constants.REQUEST_HEADER_TRACE_ID;

public class CustomAsyncHttpClient {

    private final AsyncHttpClient asyncHttpClient;

    public CustomAsyncHttpClient() {
        DefaultAsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder().build();
        asyncHttpClient = new DefaultAsyncHttpClient(config);
    }

    public CompletableFuture<Response> executeGetWithMDC(String url) {
        Map<String, String> mdcContext = RequestContext.getCopyOfThreadContext();
        return CompletableFuture.supplyAsync(() -> {
            if (mdcContext != null) {
                RequestContext.setMdcContext(mdcContext);
            }
            try {
                return asyncHttpClient
                        .prepareGet(url)
                        .addHeader(REQUEST_HEADER_TRACE_ID, RequestContext.getTraceId())
                        .execute()
                        .toCompletableFuture()
                        .get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                MDC.clear();
            }
        });
    }

    public void close() throws IOException {
        asyncHttpClient.close();
    }
}

