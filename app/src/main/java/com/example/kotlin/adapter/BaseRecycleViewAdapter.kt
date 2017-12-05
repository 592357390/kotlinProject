package com.example.kotlin.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Messenger
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin.Activity.CourseActivity
import com.example.kotlin.Activity.MoreCourseActivity

/**
 * Created by zx on 2017/11/9 0009.
 */
abstract class BaseRecycleViewAdapter<T>(context: Context) : RecyclerView.Adapter<BaseRecycleViewAdapter.ViewHolder>() {
    var mContext: Context = context

    lateinit var mServiceMessenger: Messenger

    class AdapterItemClick(context: Context) {
        var context: Context = context

        companion object {
            val courseMore: String = "CourseMore"
            val lessonMore: String = "LessonMore"
        }

        public fun bannerClick(position: Int) {

        }

        public fun startCourseActivity(position: Int, courseId: Long) {

            CourseActivity().startSelf(context, courseId, null)
        }

        public fun CourseLessonClick(position: Int, courseId: Long) {
            CourseActivity().startSelf(context, courseId, null)
        }

        public fun moreClick(moreType: String) {
            when (moreType) {
                courseMore -> MoreCourseActivity().startSelf(context, MoreCourseActivity.course, null)
                lessonMore -> MoreCourseActivity().startSelf(context, MoreCourseActivity.lesson, null)
            }
        }
    }

    var dataSource: List<T> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    open var layoutInflater = LayoutInflater.from(context)

    abstract fun getLayoutId(viewType: Int = -1): Int

    override fun getItemCount(): Int {
        return dataSource.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = layoutInflater.inflate(getLayoutId(), parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var dataBinding: ViewDataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)

    }


}

