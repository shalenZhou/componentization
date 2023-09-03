package com.example.component_demo

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.librouter.Router
import com.example.module.login.LoginActivity
import com.example.module.main.MainActivity

class App : Application() {
    override fun onCreate() {
        super.onCreate()

//        Router.getInstance().register("/main/MainActivity", MainActivity::class.java)
//        Router.getInstance().register("/login/LoginActivity", LoginActivity::class.java)

        //必须在初始化ARouter之前配置
        if (BuildConfig.DEBUG) {
            //日志开启
            ARouter.openLog()
            //调试模式开启，如果在install run模式下运行，则必须开启调试模式
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}

/*
    Router.getInstance().register("/main/MainActivity", MainActivity::class.java);
    Router.getInstance().register("/main/LoginActivity", LoginActivity::class.java);
    总结：1. 开发人员也许不知道该项目的activity有多少，具体名称
         2. 不灵活，要手动注册
 */