package com.zhenl.backuptool

import android.app.Application
import androidx.lifecycle.MutableLiveData

/**
 * Created by lin on 20-4-26.
 */
class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
        val globalLoading = MutableLiveData<Boolean>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}