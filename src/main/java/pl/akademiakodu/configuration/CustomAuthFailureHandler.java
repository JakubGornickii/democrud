package pl.akademiakodu.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CustomAuthFailureHandler
        implements AuthenticationFailureHandler {



    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {
       if (exception.getMessage().equals("User is disabled"))
       {
           response.sendRedirect(request.getContextPath() + "/disabled");
       }
       else
       {
           response.sendRedirect(request.getContextPath() + "/login?error=true");
       }

}}