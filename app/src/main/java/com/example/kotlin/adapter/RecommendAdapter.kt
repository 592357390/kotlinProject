package com.example.kotlin.adapter

import android.content.Context
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.ImageView
import com.example.kotlin.R
import com.example.kotlin.Service.PlayService
import com.example.kotlin.databinding.ItemRecommendLessonLayoutBinding
import com.example.kotlin.databinding.ItemRecommondTopLayoutBinding
import com.example.kotlin.utils.CropCircleTransformation
import com.example.kotlin.utils.OSSUtil
import com.example.kotlin.utils.bucket
import com.handarui.acquire.server.api.bean.BannerInfoBean
import com.handarui.acquire.server.api.bean.CourseBean
import com.handarui.acquire.server.api.bean.LessonInfoBean
import com.squareup.picasso.Picasso
import com.youth.banner.loader.ImageLoader
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by zx on 2017/11/9 0009.
 */
class RecommendAdapter(context: Context) : BaseRecycleViewAdapter<CourseBean>(context) {


    private var topView: Int = 0

    private var coustomView: Int = 1

    var bannerData: MutableList<BannerInfoBean> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var courseData: ArrayList<CourseBean> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lessonData: ArrayList<LessonInfoBean> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return lessonData.count() + 1
    }

    override fun getLayoutId(viewType: Int): Int {
        return if (viewType == topView) {
            R.layout.item_recommond_top_layout
        } else {
            R.layout.item_recommend_lesson_layout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return if (viewType == topView) {
            val view = layoutInflater.inflate(getLayoutId(viewType), parent, false)
            var topHolder = ViewHolder(view)
            initTopView(topHolder.dataBinding as ItemRecommondTopLayoutBinding)
            topHolder
        } else {
            val view = layoutInflater.inflate(getLayoutId(viewType), parent, false)
            ViewHolder(view)
        }
    }

    private fun initTopView(dataBinding: ItemRecommondTopLayoutBinding) {
        dataBinding.recommendCoursesRv.layoutManager = LinearLayoutManager(mContext)
        dataBinding.recommendCoursesRv.adapter = CourseAdapter(context = mContext)

        dataBinding.coursesMore.setOnClickListener({ AdapterItemClick(mContext).moreClick(AdapterItemClick.courseMore) })
        dataBinding.lessonMore.setOnClickListener({ AdapterItemClick(mContext).moreClick(AdapterItemClick.lessonMore) })

        dataBinding.banner.isAutoPlay(true)
                .setDelayTime(5000)
                .setImageLoader(object : ImageLoader() {
                    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                        Picasso.with(context).load(path.toString()).into(imageView)
                    }
                })
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            topView
        } else {
            coustomView
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (position == 0) {
            var topLayoutBinding = holder?.dataBinding as ItemRecommondTopLayoutBinding


            val images = ArrayList<String?>()

            bannerData.forEach { item ->
                doAsync {
                    images.add(OSSUtil.ossClient?.presignConstrainedObjectURL(bucket, item.objectKey, 3000 * 60))
                    uiThread {
                        topLayoutBinding.banner.setImages(images)
                        topLayoutBinding.banner.start()
                    }
                }
            }

            var courseAdapter = topLayoutBinding.recommendCoursesRv.adapter as CourseAdapter
            courseAdapter.dataSource = courseData
            topLayoutBinding.executePendingBindings()
        } else {
            val lessonBinding = holder!!.dataBinding as ItemRecommendLessonLayoutBinding
            lessonBinding.recommendLessonLayout.setOnClickListener({

                var message = Message.obtain()
                message.what = PlayService.INIT
                message.data.putSerializable("bean", lessonData)
                message.arg1 = position - 1
                mServiceMessenger.send(message)

                AdapterItemClick(mContext).startCourseActivity(position - 1, lessonData[position - 1].courseId)

            })

            doAsync {
                val url = OSSUtil.ossClient?.presignConstrainedObjectURL(bucket, lessonData[position - 1].playingPicture, 3000 * 60)
                val mediaUrl = OSSUtil.ossClient?.presignConstrainedObjectURL(bucket, lessonData[position - 1].audio, 3000 * 60)

                uiThread {
                    Picasso.with(mContext).load(url).transform(CropCircleTransformation()).into(lessonBinding.lessonImg)
                    lessonData[position - 1].audioPlayUrl = mediaUrl
                }
            }

            lessonBinding.lessonBean = lessonData[position - 1]
            lessonBinding.executePendingBindings()
        }
    }

}