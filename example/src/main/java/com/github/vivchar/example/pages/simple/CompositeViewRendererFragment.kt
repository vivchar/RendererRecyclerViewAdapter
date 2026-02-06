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
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import kotlinx.coroutines.launch

class CompositeViewRendererFragment : BaseFragment<FragmentListBinding>() {

	private val viewModel: CompositeViewRendererViewModel by viewModels()
	private val adapter = MyAdapter()

	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentListBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

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
}
