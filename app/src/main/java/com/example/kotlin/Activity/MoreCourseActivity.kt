package com.example.kotlin.Activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.kotlin.R
import com.example.kotlin.adapter.CourseAdapter
import com.example.kotlin.adapter.LessonAdapter
import com.example.kotlin.model.CourseModelImpl
import kotlinx.android.synthetic.main.activity_more_lesson.*

class MoreCourseActivity : BaseActivity() {

    companion object {
        var course: Long = 1L
        var lesson: Long = 2L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_lesson)

        moreRv.setHasFixedSize(true)
        moreRv.layoutManager = LinearLayoutManager(this)

        getDataWithType(intent.getLongExtra("id", -1))
    }

    private fun getDataWithType(type: Long) {
        when (type) {
            course -> getCourse()
            lesson -> getLesson()
        }
    }

    private fun getLesson() {
        var adapter = LessonAdapter(this)
        moreRv.adapter = adapter
        CourseModelImpl().allLatestAuditionLessons(1, 1000).subscribe({ data ->
            adapter.dataSource = data
        }, {

        })
    }

    private fun getCourse() {
        var adapter = CourseAdapter(this)
        moreRv.adapter = adapter
        CourseModelImpl().getAllCourses(1, 1000).subscribe({ data ->
            adapter.dataSource = data
        }, {

        })
    }

    override fun onResume() {
        super.onResume()
        Log.i("CourseACTIVITY","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("CourseACTIVITY","onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("CourseACTIVITY","onDestroy")
    }
}