package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.github.vivchar.example.R
import com.github.vivchar.example.base.BaseFragment
import com.github.vivchar.example.databinding.FragmentListBinding
import com.github.vivchar.example.widgets.EndlessScrollListener
import com.github.vivchar.example.widgets.ItemOffsetDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.LoadMoreViewBinder
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import kotlinx.coroutines.launch

class LoadMoreFragment : BaseFragment<FragmentListBinding>() {

	private val viewModel: LoadMoreViewModelVM by viewModels()
	private val adapter = MyAdapter()

	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentListBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		adapter.enableDiffUtil()
		adapter.registerRenderer(LoadMoreViewBinder(R.layout.item_load_more))
		adapter.registerRenderer(
			ViewRenderer<SimpleViewModel, ViewFinder>(
				R.layout.item_simple_square, SimpleViewModel::class.java
			) { model, finder, _ -> finder.setText(R.id.text, model.text) }
		)

		val layoutManager = GridLayoutManager(context, COLUMNS_COUNT)
		layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
			override fun getSpanSize(position: Int): Int {
				return when (adapter.getType(position)) {
					SimpleViewModel::class.java -> 1
					else -> COLUMNS_COUNT
				}
			}
		}

		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager = layoutManager
		binding.recyclerView.addItemDecoration(ItemOffsetDecoration(10))
		binding.recyclerView.addOnScrollListener(object : EndlessScrollListener() {
			override fun onLoadMore(page: Int, totalItemsCount: Int) {
				viewModel.onLoadMore()
			}
		})

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.state.collect { state ->
					if (state.showLoadMore) {
						adapter.showLoadMore()
					} else {
						adapter.setItems(state.items)
					}
				}
			}
		}
	}

	data class SimpleViewModel(val text: String) : ViewModel

	companion object {
		const val COLUMNS_COUNT = 4
	}
}
