package com.sporksoft.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.sporksoft.textpicker.R

class TextPicker(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0): RecyclerView(context, attrs, defStyle) {
    interface OnValueChangeListener {
        fun onValueChange(textPicker: TextPicker, value: String, index: Int)
    }

    var divider: Drawable? = context.getDrawable(R.drawable.tp_divider)

    internal var _value: String? = null
    val value: String?
        get() = _value

    internal var _index: Int = -1
    var index: Int
        get() = _index
        set(position) {
            _index = position
            _value = (adapter as TextAdapter).items[index]
            scrollToPosition(position +  2)
        }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        initAttributes(attrs)
        layoutManager = LinearLayoutManager(context)
        adapter = TextAdapter()
        setHasFixedSize(true)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this)
    }

    private fun initAttributes(attributeSet: AttributeSet?) {
        if (attributeSet == null) {
            return
        }
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TextPicker)
        divider = typedArray.getDrawable(R.styleable.TextPicker_divider) ?: context.getDrawable(R.drawable.tp_divider)
        typedArray.recycle()
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (SCROLL_STATE_IDLE == state) {
            with (layoutManager as LinearLayoutManager) {
                val textAdapter = adapter as TextAdapter
                _index = this.findFirstCompletelyVisibleItemPosition()
                _value = textAdapter.items[_index]
                textAdapter.listeners.forEach {
                    it.onValueChange(this@TextPicker, textAdapter.items[_index], _index)
                }
            }
        }
    }

    fun setItems(items: List<String>) {
        val textAdapter = adapter as TextAdapter
        textAdapter.items = items
        textAdapter.notifyDataSetChanged()
        scrollToPosition(0)
        if (items.isEmpty()) {
            _index = -1
            _value = null
        } else {
            _index = 0
            _value = items[0]
        }
    }

    fun getItems(): List<String> {
        return (adapter as TextAdapter).items
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val y = (height / 3)

        divider?.let {
            it.setBounds(0, y, width, y + it.intrinsicHeight)
            it.draw(canvas)
            it.setBounds(0, 2 * y, width, 2 * y + (it.intrinsicHeight))
            it.draw(canvas)
        }
    }

    fun addOnValueChangeListener(listener: OnValueChangeListener): Boolean {
        return (adapter as TextAdapter).listeners.add(listener)
    }

    fun removeOnValueChangeListener(listener: OnValueChangeListener): Boolean {
        return (adapter as TextAdapter).listeners.remove(listener)
    }

    override fun setAdapter(newAdapter: Adapter<*>?) {
        if (newAdapter is TextAdapter) {
            setTextAdapter(newAdapter)
        } else {
            throw Exception("Adapter must be an instance of TextAdapter")
        }
    }

    fun setTextAdapter(newAdapter: TextAdapter) {
        super.setAdapter(newAdapter)
    }
}
