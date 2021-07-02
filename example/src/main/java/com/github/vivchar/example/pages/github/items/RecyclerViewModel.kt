package com.github.vivchar.example.pages.github.items

import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */
data class RecyclerViewModel(val id: Int, val list: List<ViewModel>) : DefaultCompositeViewModel(list) {
	override fun toString() = javaClass.simpleName + "{" + mItems.toString() + "}"
}