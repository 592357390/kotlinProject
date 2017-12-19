package com.example.kotlin.fragment


import android.os.SystemClock
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.kotlin.Activity.BaseActivity
import com.example.kotlin.DB.LessonInfo
import com.example.kotlin.R
import com.example.kotlin.adapter.LessonAdapter
import com.example.kotlin.boxStore
import kotlinx.android.synthetic.main.fragment_downloading.view.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class DownloadingFragment : BaseFragment() {
    override fun initView(view: View) {
        lessonAdapter = LessonAdapter(context!!)
        view.downloadingRv.setHasFixedSize(true)
        view.downloadingRv.layoutManager = LinearLayoutManager(context)
        view.downloadingRv.adapter = lessonAdapter

        val task = object : TimerTask() {
            override fun run() {
                SystemClock.sleep(1000)
                (context as BaseActivity).boxStore.boxFor(LessonInfo::class.java).query().build().find().forEach { lessonBean ->
                    Log.i("download", lessonBean.downloadedSize.toString() + "下载大小")
                }
            }
        }

        val timer = Timer()

        timer.schedule(task, 1000, 2000)
    }

    override fun getInflateViewID(): Int = R.layout.fragment_downloading


    lateinit var lessonAdapter: LessonAdapter

}
