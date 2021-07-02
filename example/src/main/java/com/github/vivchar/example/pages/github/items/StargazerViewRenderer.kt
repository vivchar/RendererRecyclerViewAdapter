package com.github.vivchar.example.pages.github.items

import android.view.View
import com.github.vivchar.example.R
import com.github.vivchar.example.widgets.CustomViewFinder
import com.github.vivchar.rendererrecyclerviewadapter.BaseViewRenderer.Binder
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
class StargazerViewRenderer(layoutID: Int, listener: (StargazerModel, Boolean) -> Unit) : ViewRenderer<StargazerModel, CustomViewFinder>(
	layoutID,
	StargazerModel::class.java,
	Binder { model, finder, _ ->
		finder
			.setUrl(R.id.avatar, model.avatarUrl)
			.setOnClickListener {
				val willChecked = finder.find<View>(R.id.check).visibility == View.GONE
				finder.find<View>(R.id.check).visibility = if (willChecked) View.VISIBLE else View.GONE
				listener.invoke(model, willChecked)
			}
			.setOnClickListener(R.id.check) {
				val willChecked = finder.find<View>(R.id.check).visibility == View.GONE
				finder.find<View>(R.id.check).visibility = if (willChecked) View.VISIBLE else View.GONE
				listener.invoke(model, willChecked)
			}
	}) {

	override fun createViewState() = StargazerViewState()
	override fun createViewStateID(model: StargazerModel) = model.id
}