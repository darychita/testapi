package com.examples.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;


public class UserRequestWrapper extends HttpServletRequestWrapper {

    private String login;
    private HttpServletRequest request;

    public UserRequestWrapper(String login, HttpServletRequest request) {
        super(request);
        this.login = login;
        this.request = request;
    }

    @Override
    public Principal getUserPrincipal() {
        if(this.login == null ){
            return request.getUserPrincipal();
        }
        return () -> this.login;
    }
}
