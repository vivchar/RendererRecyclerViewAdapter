package com.github.vivchar.example.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.github.vivchar.example.pages.github.items.CategoryModel
import com.github.vivchar.example.pages.github.items.RecyclerViewModel
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel

/**
 * Created by Vivchar Vitaly on 7/24/17.
 */
class MyItemDecoration: ItemDecoration() {

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		val layoutManager = parent.layoutManager
		val itemPosition = parent.getChildAdapterPosition(view)
		if (itemPosition != RecyclerView.NO_POSITION) {
			if (layoutManager is GridLayoutManager) {
				val adapter = parent.adapter as RendererRecyclerViewAdapter?
				when (adapter!!.getItem<ViewModel>(itemPosition)) {
					is RecyclerViewModel -> {
						outRect.left = (-10f).dpToPixels()
						outRect.right = (-10f).dpToPixels()
						outRect.top = (5f).dpToPixels()
						outRect.bottom = (5f).dpToPixels()
					}
					is CategoryModel -> {
						outRect.left = (5f).dpToPixels()
						outRect.right = (5f).dpToPixels()
						outRect.top = (10f).dpToPixels()
						outRect.bottom = (2f).dpToPixels()
					}
					else -> {
						outRect.left = (5f).dpToPixels()
						outRect.right = (5f).dpToPixels()
						outRect.top = (5f).dpToPixels()
						outRect.bottom = (5f).dpToPixels()
					}
				}
			} else {
				throw UnsupportedClassVersionError("Unsupported LayoutManager")
			}
		}
	}
}