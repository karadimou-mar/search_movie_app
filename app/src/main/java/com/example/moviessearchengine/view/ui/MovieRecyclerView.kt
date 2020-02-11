package com.example.moviessearchengine.view.ui

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView


class MovieRecyclerView : RecyclerView {


    private var noResultImage: ImageView? = null
    private var progressBar: ProgressBar? = null

    private val hand = Handler()
    private val runnable = Runnable {
        noResultImage!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.GONE
    }

    private fun start() {
        noResultImage!!.visibility = View.GONE
        progressBar!!.visibility = View.VISIBLE
        hand.postDelayed(runnable, 2000)
    }

    private fun stop() {
        noResultImage!!.visibility = View.GONE
        progressBar!!.visibility = View.GONE
        hand.removeCallbacks(runnable)
    }


    private val adapterObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            toggleView()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            toggleView()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            toggleView()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            toggleView()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            toggleView()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            toggleView()
        }
    }


    private fun toggleView() {
        if (adapter != null && noResultImage != null) {
            if (adapter!!.itemCount == 0) {
                start()
            } else {
                stop()
                visibility = View.VISIBLE

            }
        }
    }


    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(adapterObserver)
        adapterObserver.onChanged()
    }

    fun setNoResultImage(image: ImageView) {
        noResultImage = image
    }

    fun setProgressBar(pb: ProgressBar) {
        progressBar = pb
    }


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}