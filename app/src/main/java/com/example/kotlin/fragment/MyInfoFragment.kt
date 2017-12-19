package com.example.kotlin.fragment


import android.support.v4.app.Fragment
import android.view.View
import com.example.kotlin.Activity.FeedBackActivity
import com.example.kotlin.Activity.LoginActivity
import com.example.kotlin.R
import com.example.kotlin.utils.LoginUtils
import kotlinx.android.synthetic.main.fragment_my_info.view.*


/**
 * A simple [Fragment] subclass.
 */
class MyInfoFragment : BaseFragment() {

    override fun getInflateViewID(): Int = R.layout.fragment_my_info


    override fun initView(view: View) {
        if (LoginUtils.isAppHasSignIn) {
            view.login_layout.visibility = View.VISIBLE
            view.unLogin_layout.visibility = View.GONE
        } else {
            view.login_layout.visibility = View.GONE
            view.unLogin_layout.visibility = View.VISIBLE
        }
        view.go_login.setOnClickListener({ LoginActivity().startSelf(context!!) })
        view.my_feedback.setOnClickListener({ FeedBackActivity().startSelf(context!!) })
    }

}
