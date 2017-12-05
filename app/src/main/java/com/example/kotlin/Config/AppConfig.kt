package com.example.kotlin.Config

import android.content.Context
import android.util.Log
import java.util.*

/**
 * Created by zx on 2017/3/30 0030.
 */

object AppConfig {

    private val TAG = "AppConfig"

    private var urlProps: Properties? = null

    @Throws(RuntimeException::class)
    fun init(context: Context) {
        val props = Properties()

        //通过activity中的context攻取setting.properties的FileInputStream
        val `in` = context.assets.open("appConfig.properties")
        props.load(`in`)

        urlProps = props
    }

    fun getProperty(key: String): String {
        Log.e(TAG, key + " = " + urlProps!!.getProperty(key))
        return urlProps!!.getProperty(key)
    }

}