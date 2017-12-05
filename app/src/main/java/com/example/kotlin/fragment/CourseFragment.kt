package com.example.kotlin.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.kotlin.R
import com.example.kotlin.adapter.CourseAdapter
import com.handarui.acquire.server.api.bean.CourseBean
import kotlinx.android.synthetic.main.fragment_course_lesson.view.*

/**
 * A simple [Fragment] subclass.
 */
class CourseFragment : BaseFragment() {


    private lateinit var courseAdapter: CourseAdapter

    override fun initView(view: View) {

        view.lesson_top_layout.visibility=View.GONE

        view.lesson_Rv.layoutManager = LinearLayoutManager(context)
        courseAdapter = CourseAdapter(context)
        view.lesson_Rv.adapter = courseAdapter
    }

    override fun getInflateViewID(): Int = R.layout.fragment_course_lesson


    public fun setAdapterData(data: List<CourseBean>) {
        courseAdapter.dataSource = data
    }
}// Required empty public constructor