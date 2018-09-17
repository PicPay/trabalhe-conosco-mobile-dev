package com.picpay.david.davidrockpicpay.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class BottomOffsetDecoration(bottomOffset:Int): RecyclerView.ItemDecoration() {
    private var mBottomOffset:Int = 0

    init{
        mBottomOffset = bottomOffset
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent:RecyclerView, state:RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val dataSize = state.getItemCount()
        val position = parent.getChildAdapterPosition(view)
        if (dataSize > 0 && position == dataSize - 1)
        {
            outRect.set(0, 0, 0, mBottomOffset)
        }
        else
        {
            outRect.set(0, 0, 0, 0)
        }
    }
}