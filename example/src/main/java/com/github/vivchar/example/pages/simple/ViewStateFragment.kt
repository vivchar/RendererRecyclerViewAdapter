package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.vivchar.example.R
import com.github.vivchar.example.base.BaseFragment
import com.github.vivchar.example.databinding.FragmentListBinding
import com.github.vivchar.example.pages.simple.DiffUtilFragment.DiffViewModel
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.*
import kotlinx.coroutines.launch

class ViewStateFragment : BaseFragment<FragmentListBinding>() {

	private val viewModel: ViewStateViewModel by viewModels()
	private val adapter = MyAdapter()
	private var instanceState: Bundle? = null

	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentListBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

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
		binding.recyclerView.adapter = adapter
		binding.recyclerView.addItemDecoration(BetweenSpacesItemDecoration(10, 10))

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.state.collect { state ->
					adapter.setItems(state.items)
				}
			}
		}
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

	data class StateViewModel(val id: Int, val list: List<com.github.vivchar.rendererrecyclerviewadapter.ViewModel>) : DefaultCompositeViewModel(list)
}
