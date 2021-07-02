package com.github.vivchar.example.widgets

import android.util.Log
import com.github.vivchar.example.pages.github.items.RecyclerViewModel
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import java.util.*

/**
 * Created by Vivchar Vitaly on 20.10.17.
 */
@Suppress("RedundantOverride")
class ItemsDiffCallback : DefaultDiffCallback<ViewModel>() {

	override fun areItemsTheSame(oldItem: ViewModel, newItem: ViewModel): Boolean {
		/* vivchar: Ideally you should create a BaseItemModel with the getID method */
		return when {
			oldItem is RecyclerViewModel && newItem is RecyclerViewModel -> oldItem.id == newItem.id
			oldItem is RecyclerViewModel -> false
			newItem is RecyclerViewModel -> false
			else -> super.areItemsTheSame(oldItem, newItem)
		}
	}

	override fun areContentsTheSame(oldItem: ViewModel, newItem: ViewModel): Boolean {
		return super.areContentsTheSame(oldItem, newItem)
	}

	override fun getChangePayload(oldItem: ViewModel, newItem: ViewModel): Any? {
		if (oldItem is RecyclerViewModel && newItem is RecyclerViewModel) {
			/* vivchar: I just want to call the RecyclerViewRenderer.bindView() method */
			return mutableListOf(System.currentTimeMillis()).also {
				Log.d(TAG, "composite payload: $it")
			}
		}

		return super.getChangePayload(oldItem, newItem).also {
			Log.d(TAG, "default payload: $it")
		}
	}

	companion object {
		private val TAG = ItemsDiffCallback::class.java.simpleName
	}
}