package com.example.moviessearchengine.utils

import android.util.Log

fun Any.logD(message: String) {
    Log.d(tag, message)
}

fun Any.logE(message: String, exception: Throwable? = null) {
    Log.e(tag, message, exception)
}

private val Any.tag: String
    get() {
        val name = this::class.java.simpleName
        return if (name.isNotBlank()) name
        else "Any"
    }