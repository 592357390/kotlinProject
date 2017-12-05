package com.example.kotlin.Net

import com.example.kotlin.Config.AppConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Created by zx on 2017/11/6 0006.
 */
object RetrofitManager {

    val httpUrl = "http://" + AppConfig.getProperty("ip")
    val httpsUrl = "https://" + AppConfig.getProperty("ip")
    private var retrofitBuilder = Retrofit.Builder()

    fun init() {

        if (AppConfig.getProperty("https") == "false") {
            retrofitBuilder.baseUrl(httpUrl)
        } else {
            retrofitBuilder.baseUrl(httpsUrl)
        }


        retrofitBuilder.client(OkHttpManager.getOkHttpManager())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getRetrofit(): Retrofit {
        return retrofitBuilder.build()
    }

    fun <T> createService(className: Class<T>): T {
        return retrofitBuilder.build().create(className)
    }

}
