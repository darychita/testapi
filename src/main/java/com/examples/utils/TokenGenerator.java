package com.examples.utils;

import com.examples.model.entity.Token;
import com.examples.model.entity.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

public class TokenGenerator {

    public static Token generateToken(User user){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] temp = user.getLogin().concat(LocalDateTime.now().toString()).getBytes();
            digest.digest(temp);
            return new Token(Base64.getEncoder().encodeToString(temp));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }
}
