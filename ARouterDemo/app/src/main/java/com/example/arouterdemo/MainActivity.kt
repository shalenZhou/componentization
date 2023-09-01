package com.example.arouterdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.arouterdemo.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setButtonClickListener()
    }

    private fun ActivityMainBinding.setButtonClickListener() {
        //页面跳转
        jumpPage.setOnClickListener {
            ARouter.getInstance()
                .build("/test/test")
                .navigation(this@MainActivity, object : NavCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        Log.d(TAG, "onArrival: 跳转成功")
                    }

                    override fun onFound(postcard: Postcard?) {
                        super.onFound(postcard)
                        Log.d(TAG, "onFound: 找到了")
                    }

                    override fun onLost(postcard: Postcard?) {
                        super.onLost(postcard)
                        Log.d(TAG, "onLost: 没有找到")
                    }

                    override fun onInterrupt(postcard: Postcard?) {
                        super.onInterrupt(postcard)
                        Log.d(TAG, "onInterrupt: 被拦截了")
                    }
                })
        }
    }
}