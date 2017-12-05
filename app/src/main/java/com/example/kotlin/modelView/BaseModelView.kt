package com.example.kotlin.modelView

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.BindingAdapter
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlin.MyApplication
import com.example.kotlin.R
import com.example.kotlin.utils.OSSUtil
import com.example.kotlin.utils.bucket
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat

/**
 * Created by zx on 2017/11/10 0010.
 */


@BindingAdapter("load_image")
fun loadImage(imageView: ImageView, url: String?) =
        Picasso.with(imageView.context).load(url)
                .into(imageView)

@BindingAdapter("clickNum")
fun setClickNum(textView: TextView, num: Long) {
    textView.text = dealClick(num)
}

@BindingAdapter("lessonTime")
fun setLessonTime(textView: TextView, duration: Long) {
    textView.text = dealAudioDuration(duration)
}


/**
 * @param clickNum
 * @return 小于K，显示全部数字；大于K小于M，显示XXX K；
 * 大于M ,显示XXX M
 */
fun dealClick(clickNum: Long): String {

    if (clickNum < 1000) {
        return clickNum.toString()
    }
    if (clickNum in 1000..999999) {
        return Math.round(clickNum.toFloat() / 1000).toString() + MyApplication.myApplication.getString(R.string.k)
    }

    if (clickNum >= 1000000) {
        return Math.round(clickNum.toFloat() / 1000000).toString() + MyApplication.myApplication.getString(R.string.m)
    }

    return 0.toString()
}

@SuppressLint("SimpleDateFormat")
        /**
         * 處理時間顯示
         *
         * @param duration
         * @return
         */
fun dealAudioDuration(duration: Long?): String {
    var duration = duration
    var hour: Long = 0

    if (duration == null) {
        return "00:00"
    }

    duration *= 1000

    val formatter = SimpleDateFormat("mm:ss")

    if (duration >= 60 * 60 * 1000) {
        // 当音频长度大于一小时
        hour = duration / (60 * 60 * 1000)
    }

    val hms = formatter.format(duration)

    return if (hour == 0L) hms else hour.toString() + ":" + hms
}


fun dealImageUrl(objectKey: String) {


    val url = OSSUtil.ossClient?.presignConstrainedObjectURL(bucket, objectKey + "@80p", 3000 * 60)
    Log.i("imageUrl", "imageUrl = " + url)
}


object BaseModelView {

    fun setImage(objectKey: String, imageView: ImageView, context: Context) {
        doAsync {
            val url = OSSUtil.ossClient?.presignConstrainedObjectURL(bucket, objectKey, 3000 * 60)
            uiThread {
                Picasso.with(context).load(url).into(imageView)
            }
        }
    }

}


