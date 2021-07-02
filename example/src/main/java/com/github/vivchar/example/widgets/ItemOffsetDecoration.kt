package com.github.vivchar.example.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by Vivchar Vitaly on 12/29/17.
 * https://gist.github.com/yqritc/ccca77dc42f2364777e1
 */
class ItemOffsetDecoration(private val itemOffset: Int) : ItemDecoration() {

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		super.getItemOffsets(outRect, view, parent, state)
		outRect[itemOffset, itemOffset, itemOffset] = itemOffset
	}
}