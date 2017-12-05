package com.example.kotlin.Activity

import android.os.Bundle
import android.os.Message
import android.util.Log
import com.example.kotlin.R
import com.example.kotlin.Service.PlayService
import com.example.kotlin.modelView.BaseModelView
import com.example.kotlin.modelView.dealAudioDuration
import com.handarui.acquire.server.api.bean.LessonInfoBean
import kotlinx.android.synthetic.main.activity_playing.*

class PlayingActivity : BaseActivity() {

    lateinit var lessonBean: LessonInfoBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing)

        lessonBean = PlayService.mLessonBean

        initView()
    }

    private fun initView() {
        iniLessonLayout()

        previous.setOnClickListener({ sendPlayMessage(PlayService.LAST) })
        next.setOnClickListener({ sendPlayMessage(PlayService.NEXT) })
        play.setOnClickListener({ sendPlayMessage(PlayService.PLAY) })

        openCourse.setOnClickListener({})
        openBrief.setOnClickListener({})
        download_Img.setOnClickListener({})
        share.setOnClickListener({})
    }

    private fun iniLessonLayout() {
        BaseModelView.setImage(lessonBean.playingPicture, course_Img, this)
        lesson_name_Tv.text = lessonBean.audioName
        totalTime.text = dealAudioDuration(lessonBean.audioDuration)
    }

    private fun sendPlayMessage(type: Int) {
        var message = Message.obtain()
        message.what = type
        mServiceMessenger.send(message)
    }

    override fun dealPlayBean(lessonInfo: LessonInfoBean) {
        super.dealPlayBean(lessonInfo)
        this.lessonBean = lessonBean
        iniLessonLayout()
    }

    override fun dealPlayState(playState: Int) {
        super.dealPlayState(playState)
        when (playState) {
            PlayService.PLAY -> play.setImageResource(R.mipmap.ic_play_pause_normal)
            PlayService.PAUSE -> play.setImageResource(R.mipmap.ic_play_normal)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Playing","onDestroy")
    }


}
