package com.ajlozano.hiltexample.model;

import android.util.Log;

public class LoginResult {

    private String token;
    private Integer error;

    public LoginResult(String token) {
        this.token = token;
    }

    public LoginResult(Integer error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public Integer getError() {
        return error;
    }
}
