package com.zhenl.backuptool

import android.content.ComponentName
import android.content.pm.PackageInfo
import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Created by lin on 20-4-25.
 */
class Main : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam?.packageName != "android")
            return
        XposedBridge.hookAllMethods(XposedHelpers.findClass(
            "com.android.server.pm.PackageManagerService",
            lpparam.classLoader
        ), "getPackageInfo", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                param?.result?.let {
                    val info = it as PackageInfo
                    info.applicationInfo.flags = info.applicationInfo.flags or 0x8000
                }
            }
        })
        XposedHelpers.findAndHookMethod("android.app.ContextImpl",
            lpparam.classLoader,
            "checkPermission",
            String::class.java, Int::class.java, Int::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam?) {
                    val permission = param?.args?.get(0) as? String
                    if (permission == "android.permission.BACKUP") {
                        Log.e("BackupTool", permission)
                        param.result = 0
                    }
                }
            })
        XposedHelpers.findAndHookMethod("com.android.server.backup.TransportManager",
            lpparam.classLoader,
            "isTransportTrusted",
            ComponentName::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam?) {
                    val transport = param?.args?.get(0) as? ComponentName
                    if (transport?.flattenToShortString() == "com.zhenl.backuptool/.LocalTransportService")
                        param.result = true
                }
            })
    }
}