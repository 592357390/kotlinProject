//package com.example.kotlin.Config
//
//import android.content.Context
//import android.util.Log
//
//import com.handarui.obtain.MyApplication
//import com.handarui.obtain.config.Constants
//import com.handarui.obtain.config.UrielConfig
//import com.handarui.obtain.utils.NetWorkUtils
//import com.handarui.obtain.utils.RxBus
//import com.handarui.obtain.utils.SystemUtils
//
//import java.io.IOException
//
//import okhttp3.Interceptor
//import okhttp3.Request
//import okhttp3.Response
//
///**
// * api请求的拦截器
// * Created by guofe on 2015/12/25.
// */
//class RequestInterceptor(private val context: Context) : Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val builder = chain.request().newBuilder()
//
//        if (!NetWorkUtils.isNetWorkUseful(context)) {
//            RxBus.getDefault().post(Constants.NET_UNCONNECTED)
//            OkHttpClientManager.getOkHttpClient().dispatcher().cancelAll()
//            OkHttpClientManager.getOkHttpClient().newCall(builder.build()).cancel()
//        }
//
//        builder.addHeader("Connection", "close")
//
//        /** 设备信息 */
//        builder.header(Constants.HEADER_DEVIECE_INFO, SystemUtils.getDeviceInfo(context))
//
//        /**用户token  */
//        //        if (TokenManager.getToken(context) != null) {
//        builder.header(Constants.HEADER_TOKEN_NAME, TokenManager.getToken(MyApplication.getInstance()))
//        //        } else {
//        //            builder.header(Constants.HEADER_TOKEN_NAME, null);
//        //        }f
//
//        val request = builder.build()
//
//        val t1 = System.nanoTime()
//        Log.i("API", String.format("Sending request %s on %s%n%s",
//                request.url(), chain.connection(), request.headers()))
//
//        val response = chain.proceed(request)
//
//        if (response.isSuccessful) {
//            // 检查是否是登录请求
//            checkLogin(response)
//            checkLogout(response)
//
//            val requestUrl = response.request().method() + " " + response.request().url().toString()
//            val responseCode = response.code()
//            val errorMessage = responseCode.toString() + " on " + requestUrl
//            Log.e("API_ERROR", errorMessage)
//        }
//
//        val t2 = System.nanoTime()
//        Log.i("API", String.format("Received response for %s in %.1fms%n%s",
//                response.request().url(), (t2 - t1) / 1e6, response.headers()))
//
//        return response
//    }
//
//    /**
//     * 检查是否是登录请求,并存储token
//     *
//     * @param response
//     */
//    private fun checkLogin(response: Response) {
//        var token: String? = null
//        val path = response.request().url().uri().path
//        if (path.contains(UrielConfig.LOGIN_URL)) {
//            token = response.header(Constants.HEADER_TOKEN_NAME, null)
//            if (token != null) {
//                Log.i("checkLogin", "token " + token + path)
//                TokenManager.setToken(context, token)
//            }
//        }
//    }
//
//    /**
//     * 检查是否是注销请求，并清除TOKEN
//     *
//     * @param response
//     */
//    private fun checkLogout(response: Response) {
//        if (response.request().url().uri().path.contains(UrielConfig.LOGOUT_URL)) {
//            TokenManager.removeToken(context)
//        }
//    }
//}
//
