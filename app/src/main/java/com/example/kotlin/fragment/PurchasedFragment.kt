package com.example.kotlin.fragment


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.kotlin.R
import com.example.kotlin.adapter.CourseAdapter
import com.example.kotlin.model.CourseModelImpl
import com.example.kotlin.utils.LoginUtils
import kotlinx.android.synthetic.main.fragment_purchased.view.*


/**
 * A simple [Fragment] subclass.
 */
class PurchasedFragment : BaseFragment() {

    lateinit var adapter: CourseAdapter

    override fun initView(view: View) {
        view.purchasedRv.layoutManager = LinearLayoutManager(context)

        view.purchasedRv.setHasFixedSize(true)

        adapter = CourseAdapter(context!!)

        view.purchasedRv.adapter = adapter

        initData()
    }

    private fun initData() {
        if (LoginUtils.isAppHasSignIn) {
            CourseModelImpl().getAllBuyCourses(1, 1000).subscribe({ data ->
                adapter.dataSource = data
            }, {

            })
        }
    }

    override fun getInflateViewID(): Int = R.layout.fragment_purchased

}
