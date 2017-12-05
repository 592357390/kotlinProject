package com.example.kotlin.utils

import android.content.Context
import java.util.*

/**
 * Created by jly on 2016/8/30.
 */
object SPUtils {
    val PREFERENCES_NAME = "RAPHAE"

    /**
     * put long preferences
     *
     * @param context
     * @param key
     * @param value
     */
    fun putLong(context: Context, key: String,
                value: Long) {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key
     * @return
     */
    fun getLongPreferences(context: Context, key: String): Long {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        return settings.getLong(key, -1)
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key
     * @param value
     */
    fun putInt(context: Context, key: String, value: Int) {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key
     * @return
     */
    fun getInt(context: Context, key: String): Int {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        return settings.getInt(key, 0)
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key
     * @param value
     */
    fun putFloat(context: Context, key: String, value: Float) {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key
     * @return
     */
    fun getFloat(context: Context, key: String): Float {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        return settings.getFloat(key, 0.0f)
    }

    /**
     * put string preferences
     *
     * @param context
     * @param key
     * @param value
     */
    fun putString(context: Context, key: String, value: String?) {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(key, value ?: "")
        editor.apply()
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key
     * @return
     */
    fun getString(context: Context, key: String): String {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        return settings.getString(key, "")
    }

    /**
     * put boolean preferences
     *
     * @param context
     * @param key
     * @param value
     */
    fun putBoolean(context: Context, key: String, value: Boolean) {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key
     * @return 默认为false
     */
    fun getBoolean(context: Context, key: String): Boolean {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        return settings.getBoolean(key, false)
    }

    fun putHashMap(context: Context, map: HashMap<String, Any>) {
        val settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        val mapSet = map.keys // 获取所有的key值 为set的集合
        val itor = mapSet.iterator()// 获取key的Iterator便利
        while (itor.hasNext()) {// 存在下一个值
            val key = itor.next()// 当前key值
            editor.putString(key, if (map[key] == null) "" else "" + map[key])
            editor.apply()
        }
    }

}
