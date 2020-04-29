package com.zhenl.backuptool

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhenl.backuptool.adapter.AppInfoAdapter
import com.zhenl.backuptool.base.BaseActivity
import com.zhenl.backuptool.entity.AppInfo
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by lin on 20-4-26.
 */
class MainActivity : BaseActivity() {

    lateinit var viewModel: MainVM
    private val adapter: AppInfoAdapter by lazy { AppInfoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initVM()
        MyApplication.globalLoading.postValue(true)
        viewModel.loadAppList()
    }

    private fun initView() {
        rv?.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
            adapter.setOnItemClickListener { adapter, view, position ->
                val item = adapter.getItem(position) as AppInfo
                val intent = Intent(this, AppInfoActivity::class.java)
                intent.putExtra("app", item)
                startActivity(intent)
            }
        }
    }

    private fun initVM() {
        viewModel = getViewModel(MainVM::class.java)
        viewModel.appList.observe(this, Observer {
            MyApplication.globalLoading.postValue(false)
            adapter.setNewInstance(it)
        })
    }
}