package com.example.kotlin.Activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.kotlin.R
import com.example.kotlin.Service.PlayService
import com.example.kotlin.adapter.LessonAdapter
import kotlinx.android.synthetic.main.activity_playing_list.*

class PlayingListActivity : BaseActivity() {

    var adapter: LessonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_playing_list)

        playListRv.layoutManager = LinearLayoutManager(this)

        playListRv.setHasFixedSize(true)

        adapter = LessonAdapter(this)

        playListRv.adapter = adapter

        adapter?.dataSource = PlayService.mPlayList

        var arrayList = ArrayList<User>()
        for ((name, age) in arrayList) {
        }
    }

    fun vovo():User{

        return User("A",1)
    }
    data class User(val name: String, val age: Int)

}


