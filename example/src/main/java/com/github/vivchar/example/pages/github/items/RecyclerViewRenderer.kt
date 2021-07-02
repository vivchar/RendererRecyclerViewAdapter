package com.github.vivchar.example.pages.github.items

import com.github.vivchar.example.R
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration
import com.github.vivchar.example.widgets.NestedAdapter
import com.github.vivchar.rendererrecyclerviewadapter.*

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */
class RecyclerViewRenderer : CompositeViewRenderer<RecyclerViewModel, ViewFinder>(
	R.layout.item_composite,
	R.id.recycler_view,
	RecyclerViewModel::class.java
) {
	override fun bindAdapterItems(adapter: RendererRecyclerViewAdapter, models: List<ViewModel>) = adapter.setItems(models)
	override fun createViewState() = CompositeViewState<CompositeViewHolder<*>>()
	override fun createViewStateID(model: RecyclerViewModel) = model.id
	override fun createAdapter() = NestedAdapter().apply { setDiffCallback(DefaultDiffCallback<RecyclerViewModel>()) }
	override fun createItemDecorations() = listOf(BetweenSpacesItemDecoration(0, 10))
}