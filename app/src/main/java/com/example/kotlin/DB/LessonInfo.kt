package com.example.kotlin.DB

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by zx on 2017/11/23 0023.
 */
@Entity
data class LessonInfo(
        @Id var DBid: Long=0,
         var id: Long,
        var courseId: Long,
        var audioName: String? = null,
        var isFree: Boolean? = null,
        var isPurchased: Boolean? = null,
        var clickCount: Long? = null,
        var audioPlayUrl: String? = null,
        var playingPicture: String? = null,
        var audio: String? = null,
        var essay: String? = null,
        var audioFormat: String? = null,
        var audioSize: Long? = null,
        var downloadedSize: Long? = null,
        var audioDuration: Long? = null,
        var downloadState: Int = 0,
        var isDownloadComplete: Boolean = false,
        var playedProgress: Long = 0
)