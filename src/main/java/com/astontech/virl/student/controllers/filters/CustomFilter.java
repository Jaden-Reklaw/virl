package com.astontech.virl.student.controllers.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//These header will be created each api call
@Component
public class CustomFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader("CustomFilterH1", "Value for first custom header");
        response.addHeader("CustomFilterH2", "Value for Second custom header");

        filterChain.doFilter(request, response);
    }
}
