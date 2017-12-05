package com.example.kotlin

import android.app.Activity
import android.app.Application
import com.example.kotlin.Config.AppConfig
import com.example.kotlin.DB.MyObjectBox
import com.example.kotlin.Net.OkHttpManager
import com.example.kotlin.Net.RetrofitManager
import com.example.kotlin.utils.getOssConfig
import com.facebook.appevents.AppEventsLogger
import io.objectbox.BoxStore

/**
 * Created by zx on 2017/11/6 0006.
 */

class MyApplication : Application() {

    companion object {
        lateinit var myApplication: MyApplication
        lateinit var boxStore: BoxStore
    }


    override fun onCreate() {
        super.onCreate()

        myApplication = this
        AppEventsLogger.activateApp(myApplication)


        AppConfig.init(this)
        OkHttpManager.init(this)
        RetrofitManager.init()

        getOssConfig()

        boxStore = MyObjectBox.builder().androidContext(this).build()
    }
}

val Activity.boxStore: BoxStore
    get() = MyApplication.boxStore
