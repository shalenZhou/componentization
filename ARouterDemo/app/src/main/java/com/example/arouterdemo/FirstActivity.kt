package com.example.arouterdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route

// 配置的path至少需要两级，如/xx/xxx
@Route(path="/test/test")
class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
    }

    companion object {
        const val PATH = "/test/firstActivity"
    }
}