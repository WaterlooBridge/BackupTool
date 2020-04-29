package com.zhenl.backuptool

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by lin on 20-4-27.
 */
class LocalTransportService : Service() {

    private var sTransport: LocalTransport? = null

    override fun onCreate() {
        if (sTransport == null)
            sTransport = LocalTransport(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return sTransport?.binder
    }
}