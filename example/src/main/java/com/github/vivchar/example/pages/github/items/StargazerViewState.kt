package com.github.vivchar.example.pages.github.items

import android.view.View
import com.github.vivchar.example.R
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import java.io.Serializable

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */
class StargazerViewState : ViewState<ViewHolder<*>>, Serializable {
	private var visibility = 0

	override fun clear(holder: ViewHolder<*>) {
		holder.viewFinder.setVisible(R.id.check, false)
	}

	override fun save(holder: ViewHolder<*>) {
		holder.viewFinder.find(R.id.check) { view: View -> visibility = view.visibility }
	}

	override fun restore(holder: ViewHolder<*>) {
		holder.viewFinder.setVisibility(R.id.check, visibility)
	}
}