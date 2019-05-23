package com.sporksoft.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class TextPicker(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0, items: List<String> = emptyList(), var divider: Drawable? = null): RecyclerView(context, attrs, defStyle) {
    interface OnValueChangeListener {
        fun onValueChange(textPicker: TextPicker, value: String, index: Int)
    }
    val onValueChangeListeners = mutableSetOf<OnValueChangeListener>()
    var listAdapter: TextAdapter

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        layoutManager = LinearLayoutManager(context)
        with (layoutManager as LinearLayoutManager) {
            listAdapter = TextAdapter(items = items, layoutManager = this, listeners = onValueChangeListeners)
        }
        adapter = listAdapter
        setHasFixedSize(true)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this)
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (SCROLL_STATE_IDLE == state) {
            with (layoutManager as LinearLayoutManager) {
                val position = this.findFirstVisibleItemPosition() + 1
                onValueChangeListeners.forEach {
                    it.onValueChange(this@TextPicker, listAdapter.items[position], position)
                }
            }
        }
    }

    fun setItems(items: List<String>) {
        listAdapter.items = items
        listAdapter.notifyDataSetChanged()
    }

    fun getItems(): List<String> {
        return listAdapter.items
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val y = (canvas.height / 3)

        divider?.let {
            it.setBounds(0, y, canvas.width, y + it.intrinsicHeight)
            it.draw(canvas)
            it.setBounds(0, 2 * y, canvas.width, 2 * y + (it.intrinsicHeight))
            it.draw(canvas)
        }
    }

    fun addOnValueChangeListener(listener: OnValueChangeListener): Boolean {
        return onValueChangeListeners.add(listener)
    }

    fun removeOnValueChangeListener(listener: OnValueChangeListener): Boolean {
        return onValueChangeListeners.remove(listener)
    }
}
