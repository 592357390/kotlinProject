package com.example.kotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * Created by zx on 2017/11/9 0009.
 */
class BaseFragmentAdapter(fm: FragmentManager, fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    var fragments: ArrayList<Fragment> = fragments

    var titles: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.count()
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titles.count() == 0) {
            null
        } else {
            titles[position]
        }
    }
}