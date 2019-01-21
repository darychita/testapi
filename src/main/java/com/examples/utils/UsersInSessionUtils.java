package com.examples.utils;

import com.examples.model.entity.User;

import javax.servlet.http.HttpSession;

public class UsersInSessionUtils {

    public static void storeLoginedUser(HttpSession session, User user){
        session.setAttribute("LOGINED_USER", user);
    }

    public static User getLoginedUser(HttpSession session) {
        return  (User) session.getAttribute("LOGINED_USER");
    }
}
