package com.example.kotlin.Activity

import android.os.Bundle
import com.example.kotlin.R
import com.example.kotlin.adapter.BaseFragmentAdapter
import com.example.kotlin.fragment.BriefFragment
import com.example.kotlin.fragment.CourseFragment
import com.example.kotlin.model.CourseModelImpl
import com.example.kotlin.utils.OSSUtil
import com.example.kotlin.utils.bucket
import com.handarui.acquire.server.api.bean.AuthorInfoBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_lecture.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.Serializable

class LectureActivity : BaseActivity() {

    private lateinit var briefFragment: BriefFragment

    private lateinit var courseFragment: CourseFragment

    private var lectureBean: AuthorInfoBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecture)

        lectureBean = intent.getSerializableExtra("bean") as AuthorInfoBean?

        initView()
        initData()
    }

    private fun initData() {
        CourseModelImpl().getCoursesByAuthorId(lectureBean?.id).subscribe({ courseBean ->
            courseFragment.setAdapterData(courseBean)
        }, {

        })
    }


    private fun initView() {
        courseFragment = CourseFragment()

        briefFragment = BriefFragment()
        var bundle = Bundle()
        bundle.putSerializable("bean",lectureBean!!.brief as Serializable)
        briefFragment.arguments= bundle

        toolbar_layout.title=lectureBean?.name

        var fragmentAdapter = BaseFragmentAdapter(supportFragmentManager, arrayListOf(courseFragment, briefFragment))

        fragmentAdapter.titles = arrayListOf(getString(R.string.contents), getString(R.string.brief))

        lecture_Vp.adapter = fragmentAdapter

        lecture_tab.setupWithViewPager(lecture_Vp)

        doAsync {
            val url = OSSUtil.ossClient?.presignConstrainedObjectURL(bucket, lectureBean?.portrait, 3000 * 60)
            uiThread {
                Picasso.with(this@LectureActivity).load(url).fit().into(lecture_Img)
            }
        }

    }

}
