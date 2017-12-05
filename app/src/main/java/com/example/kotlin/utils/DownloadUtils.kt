package com.example.kotlin.utils

import android.os.Environment
import android.util.Log
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.alibaba.sdk.android.oss.model.GetObjectResult
import com.example.kotlin.DB.LessonInfo
import com.example.kotlin.MyApplication
import com.handarui.acquire.server.api.bean.LessonInfoBean
import java.io.File
import java.io.RandomAccessFile

/**
 * Created by zx on 2017/11/23 0023.
 */
object DownloadUtils {
    fun getFileName(lessonBean: LessonInfoBean): String {

        var downloadFile: String = ""
        if (MyApplication.myApplication.filesDir.exists()) {
            return File(MyApplication.myApplication.filesDir.absolutePath, lessonBean.audioName + "." + lessonBean.audioFormat).absolutePath
        }

        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            return File(Environment.getExternalStorageDirectory(), lessonBean.audioName + "." + lessonBean.audioFormat).absolutePath
        }

        return File(Environment.getDataDirectory(), lessonBean.audioName + "." + lessonBean.audioFormat).absolutePath
    }

    fun startDownload(lessonBean: LessonInfoBean) {

        lessonBean.audioPlayUrl = getFileName(lessonBean)

        saveDownloadState(download, 0, lessonBean)

        val get = GetObjectRequest(bucket, lessonBean.audio)
        OSSUtil.ossClient?.asyncGetObject(get, object : OSSCompletedCallback<GetObjectRequest, GetObjectResult> {
            override fun onSuccess(request: GetObjectRequest?, result: GetObjectResult?) {
                val rwd = RandomAccessFile(lessonBean.audioPlayUrl, "rwd")
                val inputStream = result?.objectContent
                var size: Long = 0
                val buffer = ByteArray(2048)
                var len: Int
                len = inputStream?.read(buffer)!!
                while (len != -1) {
                    size += len.toLong()
                    rwd.write(buffer, 0, len)
                    len = inputStream.read(buffer)
                    Log.i("downloadSize", (size).toString() + "/" + lessonBean.audioSize)
                    saveDownloadState(download, size, lessonBean)
                }
                rwd.close()
                saveDownloadState(success, size, lessonBean)
            }

            override fun onFailure(request: GetObjectRequest?, clientException: ClientException?, serviceException: ServiceException?) {
                saveDownloadState(fail, 0, lessonBean)
                Log.i("downloadError", clientException?.message + " "
                        + serviceException?.message
                )
            }
        })
    }

    fun saveDownloadState(type: Int, downloadSize: Long, lessonBean: LessonInfoBean) {
        var lessonBox = MyApplication.boxStore.boxFor(LessonInfo::class.java)



        lessonBox.put(LessonInfo(audioName = lessonBean.audioName, id = lessonBean.id, courseId = lessonBean.courseId,
                audioPlayUrl = lessonBean.audioPlayUrl,
                playingPicture = lessonBean.playingPicture,
                audio = lessonBean.audio,
                essay = "",
                audioFormat = lessonBean.audioFormat,
                audioSize = lessonBean.audioSize,
                downloadedSize = downloadSize,
                audioDuration = lessonBean.audioDuration,
                downloadState = type
        ))
    }

    val success: Int = 0

    val fail: Int = 1

    val download: Int = 2

}