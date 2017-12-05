package com.example.kotlin.modelView

import android.content.Context
import com.example.kotlin.Activity.CourseActivity
import com.example.kotlin.adapter.BaseFragmentAdapter
import com.example.kotlin.databinding.ActivityCourseBinding
import com.example.kotlin.fragment.BriefFragment
import com.example.kotlin.fragment.CourseLessonFragment
import com.example.kotlin.model.CourseModelImpl
import com.example.kotlin.utils.OSSUtil
import com.example.kotlin.utils.RoundedCornersTransformation
import com.example.kotlin.utils.bucket
import com.handarui.acquire.server.api.bean.LessonInfoBean
import com.handarui.acquire.server.api.bean.RichTextItemBean
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by zx on 2017/11/14 0014.
 */
class CoursePresenter(binding: ActivityCourseBinding, context: Context)  {

    private var mBindIng: ActivityCourseBinding = binding
    private var mContext: Context = context
    private var activity: CourseActivity = context as CourseActivity

    fun requestServer(id: Long) {

        CourseModelImpl().getCourseInfoByCourseId(id).subscribe({ courseBean ->

            activity.setCourseData(courseBean)

            mBindIng.courseDetailTop.courseBean = courseBean
            mBindIng.courseBean=courseBean

            var fragmentAdapter = mBindIng.courseDetailVp.adapter as BaseFragmentAdapter
            var fragment = fragmentAdapter.fragments[1] as BriefFragment
            fragment.setAdapterData(courseBean.brief as ArrayList<RichTextItemBean>)

            doAsync {
                val url = OSSUtil.ossClient?.presignConstrainedObjectURL(bucket, courseBean.playingPicture, 3000 * 60)
                uiThread {
                    Picasso.with(mContext).load(url).transform(RoundedCornersTransformation(30, 10)).into(mBindIng.courseDetailTop.courseImg)
                }
            }
        }, {

        })

        CourseModelImpl().getLessonInfoBeanById(id).subscribe({ lessonBeans ->
            var fragmentAdapter = mBindIng.courseDetailVp.adapter as BaseFragmentAdapter
            var fragment = fragmentAdapter.fragments[0] as CourseLessonFragment
            fragment.setAdapterData(lessonBeans as ArrayList<LessonInfoBean>)
        }, {

        })

    }

}