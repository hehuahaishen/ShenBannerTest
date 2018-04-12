package com.shen.shenbannertest;

import android.app.Application;

/**
 * Created by zhouwei on 17/6/10.
 */

public class ShenApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       //ANR检测
       // new ANRWatchDog().start();
    }
}
