package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.example.BaseScreenFragment
import com.github.vivchar.example.R
import com.github.vivchar.example.widgets.EndlessScrollListener
import com.github.vivchar.example.widgets.ItemOffsetDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.*

/**
 * Created by Vivchar Vitaly on 12/29/17.
 */
class LoadMoreFragment : BaseScreenFragment() {
	private val yourDataProvider = YourDataProvider()
	private var adapter: RendererRecyclerViewAdapter? = null
	private var layoutManager: GridLayoutManager? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

		adapter = MyAdapter()
		adapter?.enableDiffUtil()

//		adapter?.setLoadMoreModel(YourLoadMoreModel()); /* you can change the LoadMoreModel if needed */

		adapter?.registerRenderer(LoadMoreViewBinder(R.layout.item_load_more))

		adapter?.registerRenderer(
			ViewRenderer<SimpleViewModel, ViewFinder>(
				R.layout.item_simple_square, SimpleViewModel::class.java
			) { model, finder, _ -> finder.setText(R.id.text, model.text) }
		)
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		adapter?.setItems(yourDataProvider.loadMoreItems)
		layoutManager = GridLayoutManager(context, COLUMNS_COUNT)
		layoutManager?.spanSizeLookup = object : SpanSizeLookup() {
			override fun getSpanSize(position: Int): Int {
				return when (adapter?.getType(position)) {
					SimpleViewModel::class.java -> 1
					else -> COLUMNS_COUNT
				}
			}
		}

		val view = inflater.inflate(R.layout.fragment_list, container, false)
		val recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
		recyclerView.adapter = adapter
		recyclerView.layoutManager = layoutManager
		recyclerView.addItemDecoration(ItemOffsetDecoration(10))
		recyclerView.addOnScrollListener(object : EndlessScrollListener() {
			override fun onLoadMore(page: Int, totalItemsCount: Int) {
				Log.d("#####", "onLoadMore $page")
				adapter?.showLoadMore()
//				adapter?.hideLoadMore(); /* if you need force hide progress or call setItems() */
				yourDataProvider.getLoadMoreItems { activity?.runOnUiThread { adapter?.setItems(it) } }
			}
		})
		return view
	}

	data class SimpleViewModel(val text: String) : ViewModel

	companion object {
		const val COLUMNS_COUNT = 4
	}
}