package com.zhenl.backuptool

import android.os.Bundle
import com.zhenl.backuptool.base.BaseActivity
import com.zhenl.backuptool.entity.AppInfo
import kotlinx.android.synthetic.main.activity_app_info.*

/**
 * Created by lin on 20-4-29.
 */
class AppInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
        val app = intent.getSerializableExtra("app") as? AppInfo
        app?.let {
            title = it.title
            btn_backup.setOnClickListener { BackupController.backup(this, app.packageName) }
            btn_restore.setOnClickListener { BackupController.restore(this, app.packageName) }
        }
    }
}