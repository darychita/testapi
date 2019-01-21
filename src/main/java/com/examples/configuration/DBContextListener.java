package com.examples.configuration;

import com.mongodb.MongoClient;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DBContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();


        MongoClient client = new MongoClient(
                ctx.getInitParameter("MONGODB_HOST"),
                Integer.parseInt(ctx.getInitParameter("MONGODB_PORT")));
        sce.getServletContext().setAttribute("MONGO_CLIENT", client);
        System.out.println("Mongo opened successfully");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MongoClient client = (MongoClient) sce.getServletContext()
                .getAttribute("MONGO_CLIENT");
        client.close();
        System.out.println("Mongo closed successfully");

    }
}
