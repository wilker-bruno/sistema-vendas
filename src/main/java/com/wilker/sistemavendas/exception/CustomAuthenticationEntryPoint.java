package com.wilker.sistemavendas.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        ErrorDetail detail = new ErrorDetail(HttpStatus.FORBIDDEN.name(), "Usuário/Token inválido", HttpStatus.FORBIDDEN.value());
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        res.getWriter().write(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(detail));
    }
}
