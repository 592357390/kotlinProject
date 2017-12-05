package com.example.kotlin.Activity

import android.os.Bundle
import com.example.kotlin.R
import com.example.kotlin.utils.LoginUtils
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Schedulers.newThread().createWorker().schedule({
            runOnUiThread {
                if (LoginUtils.isAppHasSignIn) {
                    MainActivity().startSelf(this@SplashActivity)
                    overridePendingTransition(R.anim.main_in, R.anim.splash_out)
                    finish()
                } else {
                    LoginActivity().startSelf(this@SplashActivity)
                    overridePendingTransition(R.anim.main_in, R.anim.splash_out)
                    finish()
                }
            }
        }, 1000, TimeUnit.MILLISECONDS)

    }
}
