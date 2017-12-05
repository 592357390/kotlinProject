package com.example.kotlin.Activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.kotlin.Service.PlayService
import com.handarui.acquire.server.api.bean.LessonInfoBean

internal typealias Serializable = java.io.Serializable

open class BaseActivity : AppCompatActivity() {

    open fun startSelf(context: Context, id: Long, infoBean: Serializable?) {
        var intent = Intent(context, this.javaClass)
        intent.putExtra("id", id)
        intent.putExtra("bean", infoBean)
        context.startActivity(intent)
    }

    open fun startSelf(context: Context) {
        var intent = Intent(context, this.javaClass)
        context.startActivity(intent)
    }

    lateinit var mServiceMessenger: Messenger
    lateinit var baseService: Messenger

    var mServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.i("连接服务", "连接成功")
            mServiceMessenger = Messenger(iBinder)
            //用于在服务端初始化来自客户端的Messenger对象,连接成功的时候，就进行初始化
            sendClientMessage(mServiceMessenger)
        }


        override fun onServiceDisconnected(componentName: ComponentName) {

        }
    }

    open fun sendClientMessage(mServiceMessenger: Messenger) {
        var message = Message.obtain()
        message.replyTo = baseService
        message.what = PlayService.INIT
        mServiceMessenger.send(message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseService = Messenger(MyHandler(this))
    }

    override fun onResume() {
        super.onResume()
        bindService(Intent(this, PlayService::class.java), mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mServiceConnection)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home// 点击返回图标事件
            -> {
                this.finish()
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class MyHandler(activity: BaseActivity) : Handler() {
        var baseActivity: BaseActivity = activity
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                PlayService.LessonInfoBean -> baseActivity.dealPlayBean(msg.data.getSerializable("bean") as LessonInfoBean)
                PlayService.PLAY -> baseActivity.dealPlayState(msg.what)
                PlayService.PAUSE -> baseActivity.dealPlayState(msg.what)
            }
            super.handleMessage(msg)
        }
    }

    open fun dealPlayState(playState: Int) {

    }

    open fun dealPlayBean(lessonInfo: LessonInfoBean) {

    }
}
