package com.example.kotlin.fragment


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.kotlin.R
import com.example.kotlin.adapter.LessonAdapter
import com.example.kotlin.utils.DownloadUtils
import com.handarui.acquire.server.api.bean.LessonInfoBean
import kotlinx.android.synthetic.main.fragment_course_lesson.view.*


/**
 * A simple [Fragment] subclass.
 */
class CourseLessonFragment : BaseFragment() {


    private lateinit var lessonAdapter: LessonAdapter

    override fun initView(view: View) {
        view.lesson_Rv.layoutManager = LinearLayoutManager(context)
        lessonAdapter = LessonAdapter(context)
        view.lesson_Rv.adapter = lessonAdapter
        view.download_lesson_Tv.setOnClickListener({
            lessonAdapter.dataSource
                    .filter { it.isFree || it.isPurchased }
                    .forEach { DownloadUtils.startDownload(it) }
        })
    }

    override fun getInflateViewID(): Int = R.layout.fragment_course_lesson


    public fun setAdapterData(data: ArrayList<LessonInfoBean>) {
        lessonAdapter.dataSource = data
    }
}
