package com.example.kotlin.fragment


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.kotlin.R
import com.example.kotlin.adapter.BriefAdapter
import com.handarui.acquire.server.api.bean.RichTextItemBean
import kotlinx.android.synthetic.main.fragment_brief.view.*


/**
 * A simple [Fragment] subclass.
 */
class BriefFragment : BaseFragment() {
    private lateinit var briefAdapter: BriefAdapter

    override fun initView(view: View) {
        view.briefRv.layoutManager = LinearLayoutManager(context)
        briefAdapter = BriefAdapter(context!!)
        view.briefRv.adapter = briefAdapter

        val arguments = arguments
        if (arguments != null) {
            val data = arguments.getSerializable("bean") as List<RichTextItemBean>
            if (data != null)
                setAdapterData(data as ArrayList<RichTextItemBean>)
        }
    }

    override fun getInflateViewID(): Int = R.layout.fragment_brief

    fun setAdapterData(data: ArrayList<RichTextItemBean>) {
        briefAdapter.dataSource = data
    }
}// Required empty public constructor
