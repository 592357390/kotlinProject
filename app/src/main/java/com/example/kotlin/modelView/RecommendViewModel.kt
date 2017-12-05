package com.example.kotlin.modelView

import android.annotation.SuppressLint
import com.example.kotlin.adapter.RecommendAdapter
import com.example.kotlin.model.CourseModelImpl
import com.handarui.acquire.server.api.bean.CourseBean
import com.handarui.acquire.server.api.bean.LessonInfoBean

/**
 * Created by zx on 2017/11/10 0010.
 */
object RecommendViewModel {

    @SuppressLint("StaticFieldLeak")
    var adapter: RecommendAdapter? = null

    fun requestServer() {

        CourseModelImpl().recommendCourses()
                .subscribe({ e ->
                    adapter?.courseData = e as ArrayList<CourseBean>
                }, {})

        CourseModelImpl().recommendAuditionLessons()
                .subscribe({ e ->
                    adapter?.lessonData = e as ArrayList<LessonInfoBean>
                }, {})
        CourseModelImpl ().banners
                .subscribe({ e ->
                    adapter?.bannerData = e
                }, {})

    }
}

