package com.examples.model.repositories;

import com.examples.exceptions.SuchUserExistsException;
import com.examples.model.entity.Token;
import com.examples.model.entity.User;
import com.examples.utils.Passwords;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.lang.NonNull;
import org.bson.Document;
import org.bson.conversions.Bson;


public class UserRepositoryImpl implements UserRepository {

    private MongoCollection<Document> collection;

    public UserRepositoryImpl(MongoClient client) {
        this.collection = client.getDatabase("users").getCollection("users");
    }

    @Override
    public void addUser(User user) throws SuchUserExistsException{
        System.out.println("In add user");
        if(isSuchUserExists(user.getLogin())){
            throw new SuchUserExistsException();
        }
        Document document = new Document();

        document.put("FIRST_NAME", user.getFirstName());
        document.put("LAST_NAME", user.getLastName());
        document.put("LOGIN", user.getLogin());

//
        String hashedPassword = Passwords.getHash(user.getPassword());
        document.put("PASSWORD_HASH", hashedPassword);
        document.put("TOKEN", null);


        System.out.println("Before inserting");
        collection.insertOne(document);
        System.out.println("User is added");
    }

    @Override
    public User findUserByLogin(String login) {

        Document query = new Document();
        query.put("LOGIN", login);

        FindIterable<Document> docs = collection.find(query);
        for(Document temp : docs){
            if(temp.getString("LOGIN").equals(login)){
                User newUser = new User();

                newUser.setFirstName(temp.getString("FIRST_NAME"));
                newUser.setLastName(temp.getString("LAST_NAME"));
                newUser.setLogin(temp.getString("LOGIN"));
                newUser.setPasswordHash(temp.getString("PASSWORD_HASH"));

                return newUser;
            }
        }
        return null;
    }

    @Override
    public void updateWithToken(User user, Token token) {
        System.out.println("in update");
        Bson filter = Filters.eq("LOGIN", user.getLogin() );
        Bson updates = Updates.set("TOKEN", token.toString());

        collection.findOneAndUpdate(filter, updates);
        System.out.println("updated!");
    }


    public boolean isSuchUserExists(String login){
        return findUserByLogin(login) != null;
    }

    public boolean isPasswordValid(@NonNull User user, @NonNull String password){
        return Passwords.isPasswordValid(
                password,
                user.getPasswordHash()
        );
    }



}
