package com.examples.servlets;

import com.examples.exceptions.NoSuchUserException;
import com.examples.exceptions.PasswordIncorrectException;
import com.examples.model.entity.Token;
import com.examples.model.entity.User;
import com.examples.model.repositories.UserRepository;
import com.examples.model.repositories.UserRepositoryImpl;
import com.examples.utils.TokenGenerator;
import com.examples.utils.UsersInSessionUtils;
import com.mongodb.MongoClient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/login")
public class LoginServlet  extends HttpServlet {

    private UserRepositoryImpl userRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        System.out.println("In Login servlet do post");

        MongoClient client = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
        userRepository = new UserRepositoryImpl(client);
        User foundUser = null;

        try {
            if(!userRepository.isSuchUserExists(login)){
                throw new NoSuchUserException();
            } else {
                foundUser = userRepository.findUserByLogin(login);
                System.out.println(foundUser.toString());
            }

            if(!userRepository.isPasswordValid(foundUser, password)){
                throw new PasswordIncorrectException();
            } else {
                Token token = TokenGenerator.generateToken(foundUser);
                System.out.println(token);
                userRepository.updateWithToken(foundUser, token);

                UsersInSessionUtils.storeLoginedUser(req.getSession(), foundUser);
                System.out.println("updated with token");

                Cookie cookie = new Cookie("TOKEN", token.toString());
                cookie.setMaxAge(60*60);
                cookie.setSecure(true);
                resp.addCookie(cookie);
                resp.sendRedirect(req.getContextPath() + "/user-info");
            }
        }catch (Exception e){
           throw new RuntimeException(e);
        }

    }
}
