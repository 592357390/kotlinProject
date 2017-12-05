package com.example.kotlin.Config

import android.util.Log
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken
import com.example.kotlin.Net.RetrofitManager
import com.handarui.acquire.server.api.service.OSSService
import com.zhexinit.ov.common.bean.RequestBean
import java.io.IOException

/**
 * 重载OSSFederationCredentialProvider生成自己的获取STS的功能
 */
class STSGetter : OSSFederationCredentialProvider {

    constructor(stsServer: String)

    constructor()

    constructor(stsServer: String, s2: String, s3: String)

    override fun getFederationToken(): OSSFederationToken? {

        val ossService = RetrofitManager.createService(OSSService::class.java)
        try {
            val response = ossService.getSTSReadAccessKey(RequestBean<Void>()).execute()
            if (response.isSuccessful) {
                val result = response.body()!!.result
                val ak = result.accessKeyId
                val sk = result.accessKeySecret
                val token = result.securityToken
                val expiration = result.expiration

                Log.i("STSGetter", "ak===" + ak)
                Log.i("STSGetter", "sk ===" + sk)
                Log.i("STSGetter", "token ===" + token)
                Log.i("STSGetter", "expiration ===" + expiration)
                return OSSFederationToken(ak, sk, token, expiration)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

}
