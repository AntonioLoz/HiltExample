package com.ajlozano.hiltexample.viewmodel;


import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ajlozano.hiltexample.R;
import com.ajlozano.hiltexample.model.LoginFormState;
import com.ajlozano.hiltexample.model.LoginResult;
import com.ajlozano.hiltexample.model.dto.LoginDTO;
import com.ajlozano.hiltexample.repository.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private static final String TAG = "TEST";

    private Repository repository;
    private MutableLiveData<LoginResult> loginResult;
    private MutableLiveData<LoginFormState> loginFormState;

    @Inject
    public LoginViewModel(Repository repository) {
        this.repository = repository;
        loginResult = new MutableLiveData<>();
        loginFormState = new MutableLiveData<>();
    }

    public void login(String email, String password) {
        LoginDTO loginDTO = new LoginDTO(email, password);

        this.repository.getToken(loginDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    String token = response.headers().get("authorization");
                    loginResult.setValue(new LoginResult(token));
                    Log.i(TAG, "onResponse.login -> Token: " + token);
                } else {
                    Log.e(TAG, "Response not successfull. Code: " + response.code());
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "Fallo en el proceso.");
                Log.e(TAG,call.request().url().toString());
                loginResult.setValue(new LoginResult(R.string.login_failed));
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }
}
