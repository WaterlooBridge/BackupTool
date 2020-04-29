package com.zhenl.backuptool

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import com.zhenl.backuptool.base.BaseRepo
import com.zhenl.backuptool.entity.AppInfo


/**
 * Created by lin on 20-4-28.
 */
class MainRepo : BaseRepo() {

    suspend fun loadAppList(): MutableList<AppInfo> = launch {
        val context = MyApplication.instance
        val apps: ArrayList<AppInfo> = ArrayList()
        val installedPackages: List<PackageInfo> =
            MyApplication.instance.packageManager.getInstalledPackages(0)
        for (installedPackage in installedPackages) {
            if (installedPackage.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0)
                apps.add(
                    AppInfo(
                        installedPackage.applicationInfo.loadLabel(context.packageManager)
                            .toString(),
                        installedPackage.packageName,
                        installedPackage.applicationInfo.loadIcon(context.packageManager)
                    )
                )
        }
        apps
    }
}