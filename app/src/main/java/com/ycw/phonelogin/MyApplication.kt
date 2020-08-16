package com.ycw.phonelogin

import android.app.Application
import cn.bmob.v3.Bmob

/**
 * @Description
 * @Author 闫彩威
 * @QQ
 */
class MyApplication: Application() {
    override fun onCreate() {

        super.onCreate()
        Bmob.initialize(this, "073e980e99b6af07a6e22565783c6d5a")
    }
}