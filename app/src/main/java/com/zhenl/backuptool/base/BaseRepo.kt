package com.zhenl.backuptool.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by lin on 20-4-28.
 */
abstract class BaseRepo {

    protected suspend fun <T> launch(block: () -> T): T = withContext(Dispatchers.IO) {
        block()
    }
}