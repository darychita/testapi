package com.examples.servlets;

import com.examples.exceptions.SuchUserExistsException;
import com.examples.model.entity.User;
import com.examples.model.repositories.UserRepository;
import com.examples.model.repositories.UserRepositoryImpl;
import com.mongodb.MongoClient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("do post in register servlet");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        // VALIDATION
        if(firstName == null || firstName.equals("") || lastName == null || lastName.equals("")){

            req.setAttribute("error", "Missing your first name or last name!");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/");
            rd.forward(req, resp);
            System.out.println("FLname empty");
        } else if(login == null || login.equals("") || password == null || password.equals("")){

            req.setAttribute("error", "Missing your login or password!");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/");
            rd.forward(req, resp);
            System.out.println("Passwd empty");

        } else {
            try {
                User user = new User(firstName, lastName, login, password);
                System.out.println("User is created");
                MongoClient client = (MongoClient) req.getServletContext()
                        .getAttribute("MONGO_CLIENT");
                UserRepository repository = new UserRepositoryImpl(client);
                System.out.println("Before adding user");
                repository.addUser(user);
            } catch (SuchUserExistsException e) {
                req.setAttribute("error", "User with such login exists!");
                System.out.println("Such user exists!");
            } finally {
                RequestDispatcher rd = getServletContext().getRequestDispatcher(
                        "/");
                rd.forward(req, resp);
            }
        }




    }


}
