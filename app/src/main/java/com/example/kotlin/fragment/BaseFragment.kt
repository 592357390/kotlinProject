package com.example.kotlin.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by zx on 2017/11/9 0009.
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(getInflateViewID(), container, false)
        initView(view)
        return view
    }

    abstract fun initView(view: View)

    abstract fun getInflateViewID(): Int

}