package com.ronggosukowati.hisan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.ronggosukowati.hisan.R;
import com.ronggosukowati.hisan.model.User;
import com.ronggosukowati.hisan.services.ApiBuilder;
import com.ronggosukowati.hisan.services.BaseApiService;
import com.ronggosukowati.hisan.session.SharePrefmanager;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textUsername, textPassword;
    private Button btnLogin;
    private ImageView imageView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging to your account ...");

        if (SharePrefmanager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        textUsername = findViewById(R.id.til_username);
        textPassword = findViewById(R.id.til_password);
        btnLogin = findViewById(R.id.btn_login);
        imageView = findViewById(R.id.imageView);

        Glide.with(this).load(R.drawable.hisan).into(imageView);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = textUsername.getEditText().getText().toString().trim();
                String password = textPassword.getEditText().getText().toString().trim();

                if (username.isEmpty()){
                    textUsername.setError(getString(R.string.text_username_empty));
                    textUsername.requestFocus();
                }else if (password.isEmpty()){
                    textPassword.setError(getString(R.string.text_password_empty));
                    textPassword.requestFocus();
                }else {

                    loginToApp(username, password);
                }
            }
        });
    }

    private void loginToApp(String username, String password) {

        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.loginUser(username, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null){
                    if (response.body().getStatus()){
                        progressDialog.dismiss();
                        Toasty.success(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                        SharePrefmanager.getInstance(LoginActivity.this).userLogin(response.body().getUserData());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        progressDialog.dismiss();
                        Toasty.error(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("onFailure", "onFailure" + t.getMessage());
                Toasty.error(LoginActivity.this, "Can not connect to server. Check your internet connection", Toast.LENGTH_SHORT).show();


            }
        });


    }
}
