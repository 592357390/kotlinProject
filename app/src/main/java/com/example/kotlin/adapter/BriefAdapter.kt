package com.example.kotlin.adapter

import android.content.Context
import com.example.kotlin.R
import com.example.kotlin.databinding.ItemBriefLayoutBinding
import com.example.kotlin.modelView.BaseModelView
import com.handarui.acquire.server.api.bean.RichTextItemBean

/**
 * Created by zx on 2017/11/14 0014.
 */
class BriefAdapter(context: Context) : BaseRecycleViewAdapter<RichTextItemBean>(context) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_brief_layout


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var layoutBinding = holder?.dataBinding as ItemBriefLayoutBinding
        var richTextItemBean = dataSource[position]
        if (richTextItemBean.type == RichTextItemBean.TYPE_IMAGE) {
            BaseModelView.setImage(richTextItemBean.content, layoutBinding.imgBrief, mContext)
        }
        layoutBinding.itemBean = richTextItemBean
        layoutBinding.executePendingBindings()
    }
}