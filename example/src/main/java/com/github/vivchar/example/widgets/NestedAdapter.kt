package com.github.vivchar.example.widgets

import android.util.Log
import android.view.ViewGroup
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */
class NestedAdapter : MyAdapter() {

	override fun onCreateViewHolder(parent: ViewGroup, typeIndex: Int): ViewHolder<*> {
		return super.onCreateViewHolder(parent, typeIndex).apply {
			Log.d(TAG, "onCreateViewHolder: " + javaClass.simpleName)
		}
	}

	override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
		super.onBindViewHolder(holder, position)
	}

	override fun onBindViewHolder(holder: ViewHolder<*>, position: Int, payloads: MutableList<Any?>) {
		Log.d(TAG, "onBindViewHolder: " + holder.javaClass.simpleName)
		super.onBindViewHolder(holder, position, payloads)
	}

	override fun onViewRecycled(holder: ViewHolder<*>) {
		Log.d(TAG, "onViewRecycled: " + holder.javaClass.simpleName)
		super.onViewRecycled(holder)
	}

	companion object {
		private val TAG = NestedAdapter::class.java.simpleName
	}
}