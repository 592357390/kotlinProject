package com.example.kotlin.Activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import com.example.kotlin.R
import com.example.kotlin.model.AppModelImpl
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_feed_back.*
import java.util.concurrent.TimeUnit



class FeedBackActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContentView(R.layout.activity_feed_back)

        supportActionBar!!.title=getString(R.string.feedback)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        RxTextView.textChanges(feedback_content)
                .subscribe({ value ->
                    dealConfirmBg(TextUtils.isEmpty(value) || TextUtils.isEmpty(feedback_phones_et.text))
                })

        RxTextView.textChanges(feedback_phones_et)
                .subscribe({ value ->
                    dealConfirmBg(TextUtils.isEmpty(value) || TextUtils.isEmpty(feedback_content.text))
                })

        RxView.clicks(feedback_confirm).throttleFirst(1, TimeUnit.SECONDS).subscribe({
            sendFeedBack()
        })
    }

    private fun dealConfirmBg(isEmpty: Boolean) {
        if (isEmpty) {
            feedback_confirm.setBackgroundResource(R.drawable.bg_empty_content_gray_with_radius)
        } else {
            feedback_confirm.setBackgroundResource(R.drawable.select_main_color_btn_with_corner)
        }
    }

    private fun sendFeedBack() {
        if (!TextUtils.isEmpty(feedback_phones_et.text) && !TextUtils.isEmpty(feedback_content.text)) {
            AppModelImpl().feedBack(feedback_content.text.toString(), feedback_phones_et.text.toString()).subscribe({
                Snackbar.make(feedback_layout, "反馈完成", Snackbar.LENGTH_LONG).addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        this@FeedBackActivity.finish()
                    }
                }).show()
            }, { error ->
                Log.i("error", error.message)
                error.printStackTrace()
            })
        } else {
            Snackbar.make(feedback_layout, "请输入所有信息", Snackbar.LENGTH_LONG).show()
        }
    }


}

