package com.example.kotlin.Activity

import android.content.Intent
import android.os.Bundle
import com.example.kotlin.R
import com.example.kotlin.model.LoginModelImpl
import com.example.kotlin.utils.LoginUtils
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : BaseActivity() {

    private lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initLogin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(this@LoginActivity, Arrays.asList("public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                LoginModelImpl().facebookLogin(loginResult, { userInfo ->
                    LoginUtils.setSignIn(this@LoginActivity, userInfo.userId, userInfo.name, userInfo.portrait, userInfo.balance, userInfo.continuousLearningDays.toLong(), userInfo.totalLearnTime)
                    MainActivity().startSelf(this@LoginActivity)
                    finish()
                })

            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }
        })

    }

    private fun initView() {
        google_login.setOnClickListener({})
        facebook_login.setOnClickListener({
            initLogin()
        })
        see_anywhere.setOnClickListener({})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
