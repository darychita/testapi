package com.examples.model.entity;

import java.util.Objects;

public class Token {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public boolean equals(Token token) {
        return this.token.equals(token.getToken());
    }
}
