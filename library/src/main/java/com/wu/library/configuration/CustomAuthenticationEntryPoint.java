//testconflict with develop
//testconflict with v2
package com.wu.library.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//add new from develop
// add from v1
@Configuration
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        System.out.println(httpServletRequest.getRequestURI());

        httpServletRequest.getSession().setAttribute("REDIRECT_URI",httpServletRequest.getRequestURI());
        httpServletResponse.sendRedirect("/login");

    }
}
