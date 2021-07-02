package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.example.BaseScreenFragment
import com.github.vivchar.example.R
import com.github.vivchar.example.pages.simple.DiffUtilFragment.DiffViewModel
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */
class CompositeViewRendererFragment : BaseScreenFragment() {
	private val yourDataProvider = YourDataProvider()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		val compositeViewRenderer = CompositeViewRenderer<DefaultCompositeViewModel, ViewFinder>(
			R.layout.item_simple_composite,
			R.id.recycler_view,
			DefaultCompositeViewModel::class.java,
			listOf(BetweenSpacesItemDecoration(10, 10))
		)

		compositeViewRenderer.registerRenderer(
			ViewRenderer<DiffViewModel, ViewFinder>(
				R.layout.item_simple_square,
				DiffViewModel::class.java
			) { model, finder, _ -> finder.setText(R.id.text, model.text) }
		)

		val adapter = MyAdapter()
		adapter.registerRenderer(compositeViewRenderer)
		adapter.setItems(yourDataProvider.compositeSimpleItems)

		val view = inflater.inflate(R.layout.fragment_list, container, false)
		val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

		recyclerView.adapter = adapter
		recyclerView.addItemDecoration(BetweenSpacesItemDecoration(10, 10))

		return view
	}
}