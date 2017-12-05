package com.example.kotlin.Activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import com.example.kotlin.R
import com.example.kotlin.Service.PlayService
import com.example.kotlin.adapter.BaseFragmentAdapter
import com.example.kotlin.databinding.ActivityCourseBinding
import com.example.kotlin.fragment.BriefFragment
import com.example.kotlin.fragment.CourseLessonFragment
import com.example.kotlin.modelView.BaseModelView
import com.example.kotlin.modelView.CoursePresenter
import com.handarui.acquire.server.api.bean.CourseBean
import com.handarui.acquire.server.api.bean.LessonInfoBean

class CourseActivity : BaseActivity() {

    private var courseID: Long = -1
    private lateinit var binding: ActivityCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        courseID = intent.getLongExtra("id", -1L)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_course)

        initView()

        initData()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    private fun initData() {
        if (courseID != -1L) {
            CoursePresenter(binding, this).requestServer(courseID)
        }
    }

    private lateinit var briefFragment: BriefFragment

    private lateinit var lessonFragment: CourseLessonFragment

    private fun initView() {

        lessonFragment = CourseLessonFragment()

        briefFragment = BriefFragment()

        var fragmentAdapter = BaseFragmentAdapter(supportFragmentManager, arrayListOf(lessonFragment, briefFragment))

        fragmentAdapter.titles = arrayListOf(getString(R.string.contents), getString(R.string.brief))

        binding.courseDetailVp.adapter = fragmentAdapter

        binding.courseDetailTab.setupWithViewPager(binding.courseDetailVp)

        binding.courseDetailTop.courseLecturer.setOnClickListener({
            LectureActivity().startSelf(this, -1, courseInfoBean?.authorInfo)
        })

        binding.backImage.setOnClickListener({ finish() })

        binding.playControl.close.setOnClickListener({
            binding.playControl.controlLayout.visibility = View.GONE
        })
        binding.playControl.playControl.setOnClickListener({
            var message = Message.obtain()
            message.what = PlayService.PLAY
            mServiceMessenger.send(message)
        })

        binding.playControl.lessonTitle.setOnClickListener({
            PlayingActivity().startSelf(this@CourseActivity)
        })
    }

    private var courseInfoBean: CourseBean? = null

    fun setCourseData(bean: CourseBean) {
        courseInfoBean = bean
    }

    override fun dealPlayBean(lessonInfo: LessonInfoBean) {
        super.dealPlayBean(lessonInfo)
        binding.playControl.lessonTitle.text = lessonInfo.audioName
        binding.playControl.playControl.setImageResource(R.mipmap.ic_pause_fragment)
        BaseModelView.setImage(lessonInfo.playingPicture, binding.playControl.lessonImg, this)
    }

    override fun dealPlayState(playState: Int) {
        super.dealPlayState(playState)
        when (playState) {
            PlayService.PLAY -> binding.playControl.playControl.setImageResource(R.mipmap.ic_pause_fragment)
            PlayService.PAUSE -> binding.playControl.playControl.setImageResource(R.mipmap.ic_play_fragment)
        }
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
