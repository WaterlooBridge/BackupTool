package com.zhenl.backuptool.events

import androidx.lifecycle.Observer

/**
 * Created by lin on 19-12-26.
 */
class ViewEventObserver(private val onEventUnhandled: (ViewEvent) -> Unit) : Observer<ViewEvent> {
    override fun onChanged(event: ViewEvent?) {
        event?.let {
            if (!it.handled) {
                it.handled = true
                onEventUnhandled(it)
            }
        }
    }
}