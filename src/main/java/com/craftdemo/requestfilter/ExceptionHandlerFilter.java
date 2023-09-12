package com.craftdemo.requestfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            ResponseDto<?> responseDto = ResponseDto.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                            .build())
                    .build();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
        }
    }
}
