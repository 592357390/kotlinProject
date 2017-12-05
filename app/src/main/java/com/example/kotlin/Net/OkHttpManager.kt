package com.example.kotlin.Net

import android.content.Context
import android.util.Log
import com.example.kotlin.Config.AppConfig
import com.example.kotlin.Config.Constants
import com.safframework.log.L
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

/**
 * Created by zx on 2017/11/6 0006.
 */
object OkHttpManager {

    private var okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

    var sslOkHttpClient: OkHttpClient? = null

    fun init(context: Context) {
        okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(RequestInterceptor(context))

        try {
            val `is` = context.assets.open(Constants.cerFilename)
            setCertificates(`is`)
        } catch (e: IOException) {
            L.i(OkHttpManager::class.java.simpleName, e.toString() + "")
        }
    }


    private fun setCertificates(vararg certificates: InputStream) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(
                    null)
            var index = 0
            for (certificate in certificates) {
                val certificateAlias = Integer.toString(index++)
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate))

                try {
                    certificate.close()
                } catch (e: IOException) {
                    Log.i(OkHttpManager::class.java.simpleName, e.toString() + "")
                }

            }

            val sslContext = SSLContext.getInstance("TLS")

            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())

            trustManagerFactory.init(keyStore)
            sslContext.init(null,
                    trustManagerFactory.trustManagers,
                    SecureRandom()
            )

            val builder = okHttpClientBuilder.sslSocketFactory(sslContext.socketFactory)

            //            if (MilierConfigs.DEBUG) {
//            builder.hostnameVerifier(NullHostNameVerifier())
            //            }
            sslOkHttpClient = builder.build()

        } catch (e: Exception) {
            L.i(OkHttpManager::class.java.simpleName, e.toString() + "")
        }

    }


    fun getOkHttpManager(): OkHttpClient {
        return if (AppConfig.getProperty("https") == "true") {
            okHttpClientBuilder.build()
        } else {
            this.sslOkHttpClient!!
        }
    }
}

