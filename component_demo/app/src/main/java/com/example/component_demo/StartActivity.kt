package com.example.component_demo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.libbase.model.User
import com.example.librouter.Router

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<TextView>(R.id.start_tv).setOnClickListener {
//            Router.getInstance().startActivity(this, "/login/LoginActivity")

            ARouter.getInstance().build("/login/LoginActivity")
                .withLong("key1", 666L).withString("key2", "8888")
                .withSerializable("data", User("huan"))
                .navigation()
        }
    }
}