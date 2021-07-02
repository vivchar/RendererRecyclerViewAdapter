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
import com.github.vivchar.rendererrecyclerviewadapter.*

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
class ViewStateFragment : BaseScreenFragment() {
	private val yourDataProvider = YourDataProvider()
	private var instanceState: Bundle? = null
	private var adapter: MyAdapter = MyAdapter()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

		val compositeViewRenderer = CompositeViewRenderer(
			R.layout.item_simple_composite,
			R.id.recycler_view,
			StateViewModel::class.java,
			listOf(BetweenSpacesItemDecoration(10, 10)),
			object : CompositeViewStateProvider<StateViewModel?, CompositeViewHolder<ViewFinder>> {
				override fun createViewState() = CompositeViewState<CompositeViewHolder<*>>()
				override fun createViewStateID(model: StateViewModel) = model.id
			}
		)
		compositeViewRenderer.registerRenderer(
			ViewRenderer<DiffViewModel, ViewFinder>(
				R.layout.item_simple_square,
				DiffViewModel::class.java
			) { (_, text), finder, _ -> finder.setText(R.id.text, text) }
		)

		adapter.registerRenderer(compositeViewRenderer)
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		adapter.setItems(yourDataProvider.stateItems)

		val view = inflater.inflate(R.layout.fragment_list, container, false)
		val recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
		recyclerView.adapter = adapter
		recyclerView.addItemDecoration(BetweenSpacesItemDecoration(10, 10))
		return view
	}

	override fun onViewStateRestored(savedInstanceState: Bundle?) {
		super.onViewStateRestored(savedInstanceState)
		instanceState = savedInstanceState
	}

	override fun onResume() {
		super.onResume()
		adapter.onRestoreInstanceState(instanceState)
		instanceState = null
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		adapter.onSaveInstanceState(outState)
	}

	data class StateViewModel(val id: Int, val list: List<ViewModel>) : DefaultCompositeViewModel(list)
}