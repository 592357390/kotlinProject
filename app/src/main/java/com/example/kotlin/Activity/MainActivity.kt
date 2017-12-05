package com.example.kotlin.Activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Messenger
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.kotlin.R
import com.example.kotlin.adapter.BaseFragmentAdapter
import com.example.kotlin.databinding.ActivityMainBinding
import com.example.kotlin.fragment.DownloadFragment
import com.example.kotlin.fragment.MyInfoFragment
import com.example.kotlin.fragment.PurchasedFragment
import com.example.kotlin.fragment.RecommendFragment
import com.example.kotlin.modelView.MainModelView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainModelView {

    private var imageSrc = intArrayOf(R.drawable.select_navigation_discover, R.drawable.select_navigation_purchased, R.drawable.select_navigation_download, R.drawable.select_navigation_my)
    private lateinit var contentView: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentView = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initTabLayout()

        initMainVp()
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainACTIVITY","onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i("MainACTIVITY","onPause")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainACTIVITY","onDestroy")

    }
    override fun sendClientMessage(mServiceMessenger: Messenger) {
        super.sendClientMessage(mServiceMessenger)
        recommendFragment.setMessenger(mServiceMessenger)
    }

    private fun initTabLayout() {
        for (i in 0..3) {
            val tab = contentView.mainTab.newTab()

            val inflater = LayoutInflater.from(this)

            val tabBg: View = inflater.inflate(R.layout.navigativon_tab_layout, null, false)

            val tabText = tabBg.findViewById<ImageView>(R.id.navigation_image)
            tabText.setImageResource(imageSrc[i])
            tab.customView = tabBg
            if (i == 0)
                tabText.isFocusable = true
            mainTab.addTab(tab)
        }

        mainTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView!!.findViewById<ImageView>(R.id.navigation_image).isFocusable = false
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                mainVp.currentItem = tab!!.position
                tab.customView!!.findViewById<ImageView>(R.id.navigation_image).isFocusable = true
            }
        })


    }

    lateinit var recommendFragment: RecommendFragment
    private fun initMainVp() {

        recommendFragment = RecommendFragment()

        mainVp.adapter = BaseFragmentAdapter(supportFragmentManager, arrayListOf(
                recommendFragment,
                PurchasedFragment(),
                DownloadFragment(),
                MyInfoFragment()
        ))

        mainVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                contentView.mainTab.getTabAt(position)?.select()
            }
        })
    }

    /**
     * 返回 不退出应用
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}


