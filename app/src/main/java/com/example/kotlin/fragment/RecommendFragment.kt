package com.example.kotlin.fragment


import android.os.Messenger
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.kotlin.R
import com.example.kotlin.adapter.RecommendAdapter
import com.example.kotlin.modelView.RecommendViewModel
import kotlinx.android.synthetic.main.fragment_recommend.view.*


/**
 * A simple [Fragment] subclass.
 */
class RecommendFragment : BaseFragment() {

    override fun getInflateViewID(): Int {
        return R.layout.fragment_recommend
    }

     var recommendAdapter: RecommendAdapter?=null
    override fun initView(view: View) {
        view.recommendRv.layoutManager = LinearLayoutManager(context)
        recommendAdapter = RecommendAdapter(context)
        view.recommendRv.adapter = recommendAdapter

        RecommendViewModel.adapter = recommendAdapter
        RecommendViewModel.requestServer()
    }

    fun setMessenger(mServiceMessenger: Messenger) {
        if (recommendAdapter==null){
            return
        }
        recommendAdapter?.mServiceMessenger = mServiceMessenger
    }
}

