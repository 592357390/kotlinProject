package com.example.kotlin.Net

import android.content.Context

import com.example.kotlin.Config.Constants


/**
 * Created by konie on 16-8-22.
 */
object TokenManager {

    /**
     * 缓存TOKEN的变量
     */
    private var TOKEN: String = ""

    /**
     * 设置TOKEN
     *
     * @param context
     * @param token
     */
    fun setToken(context: Context, token: String) {
        val sp = context.getSharedPreferences(Constants.SP_NAME_STORE_TOKEN, 0)
        val editor = sp.edit()
        editor.putString(Constants.SP_KEY_STORE_TOKEN, token)
        editor.apply()
        TOKEN = token
    }

    /**
     * 获取TOKEN
     *
     * @param context
     * @return
     */
    fun getToken(context: Context): String {
        val sp = context.getSharedPreferences(Constants.SP_NAME_STORE_TOKEN, 0)
        TOKEN = sp.getString(Constants.SP_KEY_STORE_TOKEN, "")
        return TOKEN
    }

    /**
     * 清除TOKEN
     *
     * @param context
     */
    fun removeToken(context: Context) {
        val sp = context.getSharedPreferences(Constants.SP_NAME_STORE_TOKEN, 0)
        val editor = sp.edit()
        editor.remove(Constants.SP_KEY_STORE_TOKEN)
        editor.apply()
        TOKEN = ""
    }

}
