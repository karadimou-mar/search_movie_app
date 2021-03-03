package com.example.moviessearchengine.utils

import android.view.View

fun View?.visibleElseGone(predicate: View.() -> Boolean) {
    this?.visibility = if (this?.predicate() == true) View.VISIBLE else View.GONE
}

fun View?.visibleElseInvisible(predicate: View.() -> Boolean) {
    this?.visibility = if (this?.predicate() == true) View.VISIBLE else View.INVISIBLE
}