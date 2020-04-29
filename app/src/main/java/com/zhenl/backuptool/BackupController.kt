package com.zhenl.backuptool

import android.Manifest
import android.app.Activity
import android.app.backup.*
import android.content.ComponentName
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Created by lin on 20-4-26.
 */
object BackupController {

    fun backup(activity: Activity, packageName: String) {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission(activity)
            return
        }
        val manager = BackupManager(MyApplication.instance)
        if (!isAvailable(manager))
            return
        val transport = ComponentName(activity, LocalTransportService::class.java)
        manager.selectBackupTransport(transport, object : SelectBackupTransportCallback() {
            override fun onSuccess(transportName: String?) {
                backupNow(manager, packageName)
            }

            override fun onFailure(reason: Int) {
                Toast.makeText(MyApplication.instance, "backup failure", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun restore(activity: Activity, packageName: String) {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission(activity)
            return
        }
        val manager = BackupManager(MyApplication.instance)
        if (!isAvailable(manager))
            return
        val transport = ComponentName(activity, LocalTransportService::class.java)
        manager.selectBackupTransport(transport, object : SelectBackupTransportCallback() {
            override fun onSuccess(transportName: String?) {
                restoreNow(manager, packageName)
            }

            override fun onFailure(reason: Int) {
                Toast.makeText(MyApplication.instance, "restore failure", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun backupNow(manager: BackupManager, packageName: String) {
        manager.setBackupEnabled(true)
        val packages = Array(1) { packageName }
        MyApplication.globalLoading.postValue(true)
        manager.requestBackup(packages, object : BackupObserver() {
            override fun backupFinished(status: Int) {
                MyApplication.globalLoading.postValue(false)
                Toast.makeText(
                    MyApplication.instance,
                    "backupFinished:::$status",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun restoreNow(manager: BackupManager, packageName: String) {
        val session = manager.beginRestoreSession() as? RestoreSession
        session?.restorePackage(packageName, object : RestoreObserver() {
            override fun restoreStarting(numPackages: Int) {
                MyApplication.globalLoading.postValue(true)
                Toast.makeText(MyApplication.instance, "restoreStarting", Toast.LENGTH_SHORT).show()
            }

            override fun restoreFinished(error: Int) {
                MyApplication.globalLoading.postValue(false)
                Toast.makeText(
                    MyApplication.instance,
                    "restoreFinished:::$error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun isAvailable(manager: BackupManager): Boolean {
        val list = manager.listAllTransports() as? Array<String>
        list?.forEach {
            Log.e("Transport", it)
            if (it == "com.zhenl.backuptool/.LocalTransport")
                return true
        }
        return false
    }

    private fun requestPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 101
        )
    }
}