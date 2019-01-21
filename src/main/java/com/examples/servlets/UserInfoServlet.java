package com.examples.servlets;

import com.examples.model.entity.User;
import com.examples.model.repositories.UserRepositoryImpl;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

@WebServlet("/user-info")
public class UserInfoServlet extends HttpServlet {

    private UserRepositoryImpl userRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getHeader("LOGIN");

        MongoClient client = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
        userRepository = new UserRepositoryImpl(client);

        User user = userRepository.findUserByLogin(login);

//        String text = "firstName="+user.getFirstName()+";lastName="+user.getLastName();
//        PrintWriter pw = resp.getWriter();
//        pw.write(text);
//        pw.flush();
//        pw.close();

    }
}
