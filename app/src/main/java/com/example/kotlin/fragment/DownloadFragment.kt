package com.example.kotlin.fragment


import android.support.v4.app.Fragment
import android.view.View
import com.example.kotlin.R
import com.example.kotlin.adapter.BaseFragmentAdapter
import kotlinx.android.synthetic.main.fragment_download.view.*


/**
 * A simple [Fragment] subclass.
 */
class DownloadFragment : BaseFragment() {
    override fun initView(view: View) {
        downloading = DownloadingFragment()
        downloaded = DownloadedFragment()

        var baseFragmentAdapter = BaseFragmentAdapter(activity!!.supportFragmentManager, arrayListOf(downloading, downloaded))
        baseFragmentAdapter.titles = arrayListOf("下载中", "已下载")

        view.downloadPager.adapter = baseFragmentAdapter

        view.downloadTab.setupWithViewPager(view.downloadPager)
    }

    override fun getInflateViewID(): Int = R.layout.fragment_download


    lateinit var downloading: DownloadingFragment

    lateinit var downloaded: DownloadedFragment

}// Required empty public constructor
