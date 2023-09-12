package com.craftdemo.requestfilter.security;

import com.craftdemo.requestfilter.response.ErrorDto;
import com.craftdemo.requestfilter.util.RequestContext;
import com.craftdemo.requestfilter.response.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.craftdemo.requestfilter.util.Constants.MESSAGE_FORBIDDEN;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException {
        ResponseDto<?> responseDto = ResponseDto.builder()
                .error(ErrorDto.builder()
                        .message(MESSAGE_FORBIDDEN)
                        .code(HttpStatus.FORBIDDEN.name())
                        .build())
                .correlationId(RequestContext.getTraceId())
                .build();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
    }
}
