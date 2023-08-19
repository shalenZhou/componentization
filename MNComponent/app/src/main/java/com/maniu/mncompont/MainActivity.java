package com.maniu.mncompont;


import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.maniu.arouter.ARouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button jumpBtn = findViewById(R.id.jumpPage);

        jumpBtn.setOnClickListener(view -> ARouter.getInstance().jumpActivity("member/MemberActivity", null));
    }
}