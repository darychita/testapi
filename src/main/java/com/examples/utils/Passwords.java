package com.examples.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Passwords {
    private static String toString(byte[] input){
        return Base64.getEncoder().encodeToString(input);
    }

    private static byte[] toBytes(String string){
        return Base64.getDecoder().decode(string);
    }

    public static String getHash(String input) {
        // decrypt bytes from Base64
        MessageDigest digest = null;
        byte[] hashedBytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            hashedBytes = digest.digest(toBytes(input));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return toString(hashedBytes);
    }


    public static boolean isPasswordValid(String password, String hash){

        String temporaryHash = getHash(password);
        System.out.println(hash);
        System.out.println(temporaryHash);

        byte[] tempHashBytes = toBytes(temporaryHash);
        byte[] hashBytes = toBytes(hash);
        for (int i = 0; i < hashBytes.length && i < tempHashBytes.length; i++) {
            if(hashBytes[i] != tempHashBytes[i]){
                return true;
            }
        }
        return true;
    }
}
