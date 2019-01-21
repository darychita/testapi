package com.examples.filters;

import com.examples.model.entity.Token;
import com.examples.model.entity.User;
import com.examples.utils.UserRequestWrapper;
import com.examples.utils.UsersInSessionUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/data-info")
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = UsersInSessionUtils.getLoginedUser(request.getSession());
        HttpServletRequest userRequest = null;

        if(user != null){
            Token userToken = user.getToken();
            Token sessionToken = null;

            for(Cookie temp : request.getCookies()){
                if(temp.getName().equals("TOKEN")){
                    sessionToken = new Token(temp.getValue());
                    break;
                }
            }

            userRequest = new UserRequestWrapper(user.getLogin(), request);

            if(userToken.equals(sessionToken)){
                response.setHeader("LOGIN", user.getLogin());
                response.sendRedirect("/data-info");
            } else {
                response.sendRedirect("/");
            }


        }

        filterChain.doFilter(userRequest, response);

    }

    @Override
    public void destroy() {

    }
}
