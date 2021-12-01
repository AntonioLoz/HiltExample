package com.ajlozano.hiltexample.network;

import com.ajlozano.hiltexample.model.dto.LoginDTO;

import dagger.hilt.DefineComponent;
import dagger.hilt.android.internal.managers.ApplicationComponentManager;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {

    @POST("login")
    Call<Void> login(@Body LoginDTO loginDTO);
}
