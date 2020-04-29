package com.zhenl.backuptool.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zhenl.backuptool.R
import com.zhenl.backuptool.databinding.ItemPackageBinding
import com.zhenl.backuptool.entity.AppInfo

/**
 * Created by lin on 20-4-28.
 */
class AppInfoAdapter: BaseQuickAdapter<AppInfo, BaseDataBindingHolder<ItemPackageBinding>>(R.layout.item_package) {

    override fun convert(holder: BaseDataBindingHolder<ItemPackageBinding>, item: AppInfo) {
        holder.dataBinding?.item = item
    }
}