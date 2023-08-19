package com.maniu.mncompont;

import android.app.Application;

import com.maniu.arouter.ARouter;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
