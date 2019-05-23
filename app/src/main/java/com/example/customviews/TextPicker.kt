package com.example.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

class TextPicker(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0, items: List<String> = emptyList(), var divider: Drawable? = null): RecyclerView(context, attrs, defStyle) {
    lateinit var listAdapter: TextAdapter
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        layoutManager = LinearLayoutManager(context)
        with (layoutManager as LinearLayoutManager) {
            listAdapter = TextAdapter(items = items, layoutManager = this)
        }
        adapter = listAdapter
        setHasFixedSize(true)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this)
    }

    fun setItems(items: List<String>) {
        listAdapter.items = items
        listAdapter.notifyDataSetChanged()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        val y = (canvas.height / 3)

        divider?.let {
            it.setBounds(0, y, canvas.width, y + it.intrinsicHeight)
            it.draw(canvas)
            it.setBounds(0, 2 * y, canvas.width, 2 * y + (it.intrinsicHeight))
            it.draw(canvas)
        }
    }
}