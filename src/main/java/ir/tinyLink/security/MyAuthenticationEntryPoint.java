package ir.tinyLink.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Component
public class MyAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    @Autowired
    private ResponseWriterUtil responseWriterUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException {
        responseWriterUtil.sendUnauthorizedResponse(request, response);
    }

}
