package com.example.kotlin.adapter

import android.content.Context
import com.example.kotlin.R
import com.example.kotlin.databinding.ItemRecommendCourseBinding
import com.example.kotlin.modelView.BaseModelView
import com.handarui.acquire.server.api.bean.CourseBean

/**
 * Created by zx on 2017/11/9 0009.
 */
class CourseAdapter(context: Context) : BaseRecycleViewAdapter<CourseBean>(context) {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_recommend_course
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val courseBinding = holder!!.dataBinding as ItemRecommendCourseBinding
        courseBinding.recommendCourseLayout.setOnClickListener({
            AdapterItemClick(mContext).startCourseActivity(position, dataSource[position].id)
        })

        BaseModelView.setImage(dataSource[position].playingPicture,courseBinding.courseImg,mContext)

        courseBinding.courseBean = dataSource[position]
        courseBinding.executePendingBindings()
    }
}