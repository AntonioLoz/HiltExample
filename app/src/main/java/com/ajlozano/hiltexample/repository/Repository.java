package com.ajlozano.hiltexample.repository;

import com.ajlozano.hiltexample.model.dto.LoginDTO;
import com.ajlozano.hiltexample.network.LoginApiService;

import javax.inject.Inject;

import retrofit2.Call;

public class Repository {

    private LoginApiService loginApiService;

    @Inject
    public Repository(LoginApiService loginApiService) {
        this.loginApiService = loginApiService;
    }

    public Call<Void> getToken(LoginDTO loginDTO) {
        return loginApiService.login(loginDTO);
    }
}
