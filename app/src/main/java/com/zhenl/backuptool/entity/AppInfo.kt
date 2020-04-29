package com.zhenl.backuptool.entity

import android.graphics.drawable.Drawable
import java.io.Serializable

/**
 * Created by lin on 20-4-28.
 */
data class AppInfo(
    val title: String,
    val packageName: String,
    @Transient val icon: Drawable
) : Serializable