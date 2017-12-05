package com.example.kotlin.adapter

import android.content.Context
import com.example.kotlin.R
import com.example.kotlin.databinding.ItemCourseLessonLayoutBinding
import com.example.kotlin.utils.DownloadUtils
import com.handarui.acquire.server.api.bean.LessonInfoBean

/**
 * Created by zx on 2017/11/9 0009.
 */
class LessonAdapter(context: Context) : BaseRecycleViewAdapter<LessonInfoBean>(context) {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_course_lesson_layout
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val lessonBinding = holder!!.dataBinding as ItemCourseLessonLayoutBinding
        lessonBinding.courseLessonLayout.setOnClickListener({
            AdapterItemClick(mContext).startCourseActivity(position, dataSource[position].courseId)
        })
        lessonBinding.lessonDownload.setOnClickListener({
            DownloadUtils.startDownload(dataSource[position])
        })
        lessonBinding.lessonBean = dataSource[position]
        lessonBinding.executePendingBindings()
    }
}