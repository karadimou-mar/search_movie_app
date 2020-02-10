package com.example.moviessearchengine

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*

class MovieRecyclerView: RecyclerView {

    private var noResultImage: ImageView? = null
    private val adapterObserver = object : AdapterDataObserver(){
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

    private fun toggleView(){
        if (adapter != null && noResultImage != null){
            if (adapter!!.itemCount == 0){
                visibility = View.GONE
                noResultImage!!.visibility = View.VISIBLE
                recyclerView_movies.setBackgroundColor(Color.WHITE)
            }
            else{
                noResultImage!!.visibility = View.GONE
                visibility = View.VISIBLE
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(adapterObserver)
        adapterObserver.onChanged()
    }

    fun setNoResultImage(image: ImageView){
        noResultImage = image
    }



    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}