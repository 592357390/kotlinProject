package com.example.kotlin.Service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Toast
import com.handarui.acquire.server.api.bean.LessonInfoBean

class PlayService : Service() {

    companion object {
        val INIT: Int = 0
        val NEXT: Int = 1
        val LAST: Int = 2
        val PAUSE: Int = 3
        val PLAY: Int = 4

        val LessonInfoBean: Int = 5

        var mLessonBean: LessonInfoBean = LessonInfoBean()
        var mPlayList: ArrayList<LessonInfoBean> = ArrayList()
    }

    private lateinit var serviceMessenger: Messenger
    private lateinit var mediaPlay: MediaPlayer

    //播放位置 从0开始
    private var position: Int = 0


    override fun onCreate() {
        super.onCreate()
        serviceMessenger = Messenger(ServiceHandler(this))
        mediaPlay = MediaPlayer()
        mediaPlay.setOnCompletionListener {
            playNext()
        }
        mediaPlay.setOnErrorListener { p0, p1, p2 ->
            true
        }
        mediaPlay.setOnPreparedListener {
            mediaPlay.start()
            sendMediaName()
            sendProgressEverySecond()
        }
    }

    /**
     * 初始化播放列表和位置
     */
    fun initPlayData(playList: ArrayList<LessonInfoBean>, position: Int) {
        if (playList.size > 0) {
            mPlayList.clear()
            mPlayList.addAll(playList)
            this.position = position
            play(position)
        }
        sendMediaName()
        sendPlayMediaState(if (mediaPlay.isPlaying) PLAY else PAUSE)
    }

    fun playNext() {
        if (position < mPlayList.count() - 1) {
            position++
        }
        // 已经是播放列表最后一首
        if (position == mPlayList.count() - 1) {
            position = 0
        }

        play(position)
    }

    fun playLast() {
        if (position == 0) {
            Toast.makeText(this, "已经是第一首了", Toast.LENGTH_LONG).show()
        }
    }

    private fun play(position: Int) {

        //用户点击的歌曲是否一样,一样的时候重新播放
        if (mPlayList[position] != mLessonBean) {
            mLessonBean = mPlayList[position]
            mediaPlay.reset()
            mediaPlay.setDataSource(mLessonBean?.audioPlayUrl)
            mediaPlay.prepareAsync()
            sendMediaName()
        } else {
            if (mediaPlay.isPlaying) {
                mediaPlay.seekTo(0)
            } else {
                mediaPlay.start()
            }
        }
    }

    fun changePlayState() {
        if (mediaPlay.isPlaying) {
            mediaPlay.pause()
            sendPlayMediaState(PAUSE)
        } else {
            mediaPlay.start()
            sendPlayMediaState(PLAY)
        }
    }

    private fun sendPlayMediaState(playState: Int) {
        var message = Message.obtain()
        message.what = playState
        baseMessenger.send(message)
    }

    /**
     * 发送播放进度
     */
    private fun sendProgressEverySecond() {

    }

    private fun sendMediaName() {
        if (mLessonBean.id != null && mLessonBean.id != 0L) {
            var message = Message.obtain()
            message.what = LessonInfoBean
            message.data.putSerializable("bean", mLessonBean)
            baseMessenger.send(message)
        }
    }


    override fun onBind(intent: Intent): IBinder? {
        return serviceMessenger.binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private lateinit var baseMessenger: Messenger

    class ServiceHandler(playService: PlayService) : Handler() {
        private val mPlayService: PlayService = playService


        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                INIT -> {
                    if (msg.replyTo != null) {
                        mPlayService.baseMessenger = msg.replyTo
                    }
                    var playList = msg.data.getSerializable("bean")
                    var position = msg.arg1
                    if (playList != null) {
                        mPlayService.initPlayData(playList = playList as ArrayList<LessonInfoBean>, position = position)
                    }
                }
                NEXT -> {
                    mPlayService.playNext()
                }
                LAST -> {
                    mPlayService.playLast()
                }
                PLAY -> {
                    mPlayService.changePlayState()
                }
                PAUSE -> {
                    mPlayService.changePlayState()
                }
            }
        }
    }


}
