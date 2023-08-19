package com.maniu.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.maniu.annotation.BindPath;

@BindPath("login/LoginActivity")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}