package com.github.vivchar.example.widgets

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by Vivchar Vitaly on 8/25/17.
 */
class BetweenSpacesItemDecoration(private val verticalSpace: Int, private val horizontalSpace: Int) : ItemDecoration() {

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		val position = parent.getChildViewHolder(view).adapterPosition
		val itemCount = state.itemCount
		val layoutManager = parent.layoutManager
		setSpacingForDirection(outRect, layoutManager!!, position, itemCount)
	}

	/* https://gist.github.com/alexfu/f7b8278009f3119f523a */
	private fun setSpacingForDirection(outRect: Rect, layoutManager: RecyclerView.LayoutManager, position: Int, itemCount: Int) {
		/* Resolve display mode automatically */
		when (resolveDisplayMode(layoutManager)) {
			HORIZONTAL -> {
				outRect.left = 0
				outRect.right = if (position == itemCount - 1) 0 else horizontalSpace.dpToPixels()
				outRect.top = 0
				outRect.bottom = 0
			}
			VERTICAL -> {
				outRect.left = 0
				outRect.right = 0
				outRect.top = 0
				outRect.bottom = if (position == itemCount - 1) 0 else verticalSpace.dpToPixels()
			}
			GRID -> {
				val gridLayoutManager = layoutManager as GridLayoutManager
				val cols = gridLayoutManager.spanCount
				val rows = itemCount / cols
				outRect.left = if (position % cols == 0) 0 else horizontalSpace.dpToPixels() / 2
				outRect.right = if ((position + 1) % cols == 0) 0 else horizontalSpace.dpToPixels() / 2
				outRect.top = if (position < cols) 0 else verticalSpace.dpToPixels() / 2
				outRect.bottom = if (position / cols == rows) 0 else verticalSpace.dpToPixels() / 2
			}
		}
	}

	private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager): Int {
		if (layoutManager is GridLayoutManager) {
			return GRID
		}
		return if (layoutManager.canScrollHorizontally()) {
			HORIZONTAL
		} else VERTICAL
	}

	companion object {
		private const val HORIZONTAL = 0
		private const val VERTICAL = 1
		private const val GRID = 2
	}

}

fun Int.dpToPixels(): Int {
	return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Float.dpToPixels(): Int {
	return (this * Resources.getSystem().displayMetrics.density).toInt()
}
