package com.example.kotlin.utils

import android.widget.Toast
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.common.OSSLog
import com.example.kotlin.Config.STSGetter
import com.example.kotlin.MyApplication
import com.example.kotlin.model.AppModelImpl


/**
 * Created by zx on 2017/11/13 0013.
 */


var bucket: String? = null

var endPoint: String? = null

fun getOssConfig() {
    AppModelImpl().ossConfig.subscribe({ e ->
        bucket = e.bucket
        endPoint = e.endpoint
    }, {
        Toast.makeText(MyApplication.myApplication, "获取配置失败", Toast.LENGTH_LONG).show()
    })
}

object OSSUtil {

     var ossClient: OSSClient?=null

    init {
        initOss()
    }

    private fun initOss() {
        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000 // 连接超时，默认15秒
        conf.socketTimeout = 15 * 1000 // socket超时，默认15秒
        conf.maxConcurrentRequest = 5 // 最大并发请求数，默认5个
        conf.maxErrorRetry = 2 // 失败后最大重试次数，默认2次
        OSSLog.enableLog() //这个开启会支持写入手机sd卡中的一份日志文件位置在SDCard_path\OSSLog\logs.csv

        val credentialProvider = STSGetter()

        ossClient = OSSClient(MyApplication.myApplication, endPoint, credentialProvider, conf)
    }
}