package com.example.kotlin.Net

import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.example.kotlin.Config.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by zx on 2017/11/6 0006.
 */
open class RequestInterceptor(context: Context) : Interceptor {

    var context: Context = context
    var tag = this.javaClass.toString()


    override fun intercept(chain: Interceptor.Chain): Response {
        var requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Connection", "close")
        //添加token
        requestBuilder.addHeader(Constants.HEADER_TOKEN_NAME, TokenManager.getToken(context))
        //添加设备信息
        requestBuilder.addHeader(Constants.HEADER_DEVICE_INFO, "IMEI:868568024520097,IMSI:460028691111061,androidVersion:23,appVersion:1.0.1,channelId:default_Channel,deviceName:MI 4LTE,iccId:89860016111551208299")

        var requestTime = SystemClock.currentThreadTimeMillis()
        var request = requestBuilder.build()
        // 打印网络请求日志
        Log.i(tag, String.format("%s request  on %s%n %s", request.method(), request.url(), request.headers()))

        var response = chain.proceed(request)

        var responseTime = SystemClock.currentThreadTimeMillis()

        if (response.isSuccessful) {
            //取出登录token
            checkLogin(response)
            checkLogout(response)
        }

        Log.i(tag, String.format("Received response for %s in %sms %n%s",
                response.request().url(), (responseTime - requestTime).toString(), response.headers()))
        return response
    }

    /**
     * 检查是否是登录请求,并存储token
     *
     * @param response
     */
    private fun checkLogin(response: Response) {
        var token: String? = null
        val path = response.request().url().uri().path
        if (path.contains(Constants.LOGIN_URL)) {
            token = response.header(Constants.HEADER_TOKEN_NAME)
            if (token != null) {
                Log.i("checkLogin", "token " + token + path)
                TokenManager.setToken(context, token)
            }
        }
    }

    /**
     * 检查是否是注销请求，并清除TOKEN
     *
     * @param response
     */
    private fun checkLogout(response: Response) {
        if (response.request().url().uri().path.contains(Constants.LOGOUT_URL)) {
            TokenManager.removeToken(context)
        }
    }
}