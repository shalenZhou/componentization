package com.example.module.login

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.libbase.model.User
import com.example.librouter.Router

private const val TAG = "LoginActivity"

@Route(path = "/login/LoginActivity")
class LoginActivity : AppCompatActivity() {

    @Autowired
    @JvmField
    var key1: Long = 0L

    @Autowired(name = "key2")
    @JvmField
    var str: String = ""

    @Autowired
    @JvmField
    var data: User = User("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ARouter.getInstance().inject(this)
        Log.d(TAG, "key1=$key1")
        Log.d(TAG, "key2=$str")
        Log.d(TAG, "data=$data")

        findViewById<TextView>(R.id.login_tv).setOnClickListener {
//            Router.getInstance().startActivity(this, "/main/MainActivity")
            ARouter.getInstance().build("/main/MainActivity").navigation()
        }
    }
}