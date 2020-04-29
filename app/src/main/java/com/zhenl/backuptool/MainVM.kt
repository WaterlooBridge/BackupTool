package com.zhenl.backuptool

import androidx.lifecycle.MutableLiveData
import com.zhenl.backuptool.base.BaseViewModel
import com.zhenl.backuptool.entity.AppInfo

/**
 * Created by lin on 20-4-28.
 */
class MainVM : BaseViewModel() {

    val mRepo = MainRepo()

    val appList: MutableLiveData<MutableList<AppInfo>> by lazy { MutableLiveData<MutableList<AppInfo>>() }

    fun loadAppList() {
        launch {
            appList.value = mRepo.loadAppList()
        }
    }
}