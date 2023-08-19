package com.maniu.member;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.maniu.annotation.BindPath;

@BindPath("member/PersonActivity")
public class PersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
    }
}