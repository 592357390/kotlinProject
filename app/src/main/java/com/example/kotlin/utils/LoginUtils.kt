package com.example.kotlin.utils

import android.content.Context
import com.example.kotlin.Config.Constants
import com.example.kotlin.MyApplication
import com.example.kotlin.Net.TokenManager


/**
 * Created by zx on 2016/8/28 0028.
 */
object LoginUtils {

    private val TAG = "LoginUtils"

    /**
     * 判断是否登陆
     *
     * @param
     * @return
     */

    val isAppHasSignIn: Boolean
        get() = SPUtils.getBoolean(MyApplication.myApplication, Constants.SIGN_IN)

    val userBalance: Float
        get() = SPUtils.getFloat(MyApplication.myApplication, Constants.USER_BALANCE)

    var userLearnTimes: Long
        get() = SPUtils.getLongPreferences(MyApplication.myApplication, Constants.LEARN_TIMES)
        set(times) = SPUtils.putLong(MyApplication.myApplication, Constants.LEARN_TIMES, times)

    var userLearnDays: Long
        get() {
            var longPreferences = SPUtils.getLongPreferences(MyApplication.myApplication, Constants.LEARN_DAYS)
            if (longPreferences == 0L) {
                longPreferences = 1
            }
            return longPreferences
        }
        set(days) = SPUtils.putLong(MyApplication.myApplication, Constants.LEARN_DAYS, days)

    /**
     * 开始登录
     */
    fun startInsideLogin(context: Context, code: Int) {
        loginOut(context)
    }

    fun isPostActiveInfo(context: Context): Boolean {
        return SPUtils.getBoolean(context, Constants.IS_ACTIVATE)
    }

    fun setIsPostActiveInfo(context: Context, isActive: Boolean) {
        SPUtils.putBoolean(context, Constants.IS_ACTIVATE, isActive)
    }

    fun isFristEvent(context: Context, key: String): Boolean {
        val sharedPreferences = context.getSharedPreferences("lanch", Context.MODE_PRIVATE)
        //第一次发送，没有该文件，默认返回true
        val isFirstLunch = sharedPreferences.getBoolean(key, true)
        //返回true说明第一次启动，调用该方法说明已经启动一次，写入文件设为false
        if (isFirstLunch) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(key, false)
            editor.commit()
        }

        return isFirstLunch
    }

    /**
     * 设置登录信息
     */
    fun setSignIn(context: Context, userId: Long, userName: String?, userAva: String?, balance: Double, learnDays: Long, learnTimes: Long) {
        /**
         * 用户信息
         */
        SPUtils.putLong(context, Constants.USER_ID, userId)

        if (userName != null)
            SPUtils.putString(context, Constants.USER_NAME, userName)

        if (userAva != null)
            setUserAva(context, userAva)
        /**
         * 登陆状态
         */
        SPUtils.putBoolean(context, Constants.SIGN_IN, true)


        setUserBalance(balance)

        userLearnDays = learnDays

        userLearnTimes = learnTimes
    }

    fun getUserId(context: Context): Long {
        return SPUtils.getLongPreferences(context, Constants.USER_ID)
    }

    fun getUserName(context: Context): String {
        return SPUtils.getString(context, Constants.USER_NAME)
    }

    fun getUserAva(context: Context): String {
        return SPUtils.getString(context, Constants.USER_AVA)
    }

    fun setUserAva(context: Context, userAva: String) {
        SPUtils.putString(context, Constants.USER_AVA, userAva)
    }

    fun setUserBalance(balance: Double) {
        SPUtils.putFloat(MyApplication.myApplication, Constants.USER_BALANCE, balance.toFloat())
    }

    /**
     * 退出登录
     *
     * @param context
     */
    fun loginOut(context: Context) {
//            LoginManager.myApplication.logOut()
//            // 登出google
//            if (GoogleManager.getInstance(AppManager.getAppManager().currentActivity()) != null) {
//                if (GoogleManager.getInstance(AppManager.getAppManager().currentActivity()).isConnected()) {
//                    Auth.GoogleSignInApi.signOut(GoogleManager.getInstance(context))
//                }
//            }

            SPUtils.putBoolean(context, Constants.SIGN_IN, false)

            SPUtils.putLong(context, Constants.USER_ID, 0)
            SPUtils.putString(context, Constants.USER_NAME, null)
            SPUtils.putString(context, Constants.USER_AVA, null)
            TokenManager.removeToken(context)
            // 清空数据库
//            for (abstractDao in DbCore.getDaoSession().getAllDaos()) {
//                if (!(abstractDao is GuideBeanDao || abstractDao is LocalDownLoadLessonBeanDao)) {
//                    abstractDao.deleteAll()
//                }
//            }


        //用户退出

        //        DbCore.getDaoSession().getLocalCourseBeanDao().deleteAll();
        //        DbCore.getDaoSession().getLocalCourseBeanDao().loadAll();
        //        DbCore.getDaoSession().getPurchasedCourseIdsBeanDao().deleteAll();
    }


    /**
     *
     */
    fun setIsAllowUnWifiDownload(context: Context, isAllowed: Boolean) {
        SPUtils.putBoolean(context, Constants.IS_ALLOW_UN_WIFI_DOWNLOAD, isAllowed)
    }

    fun getIsAllowUnWifiDownload(context: Context): Boolean {
        return SPUtils.getBoolean(context, Constants.IS_ALLOW_UN_WIFI_DOWNLOAD)
    }


}

