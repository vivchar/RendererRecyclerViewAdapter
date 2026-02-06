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
import com.github.vivchar.example.widgets.ItemOffsetDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import kotlinx.coroutines.launch

class DiffUtilFragment : BaseFragment<FragmentListBinding>() {

	private val viewModel: DiffUtilViewModel by viewModels()
	private val adapter = MyAdapter()

	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentListBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		adapter.setDiffCallback(DiffCallback())
		adapter.registerRenderer(
			ViewRenderer<DiffViewModel, ViewFinder>(
				R.layout.item_simple_square,
				DiffViewModel::class.java
			) { model, finder, _ ->
				finder
					.setText(R.id.text, model.text)
					.setOnClickListener(R.id.text) { viewModel.onItemClicked(model) }
			}
		)

		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
		binding.recyclerView.addItemDecoration(ItemOffsetDecoration(10))

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.state.collect { state ->
					adapter.setItems(state.items)
				}
			}
		}
	}

	private inner class DiffCallback : DefaultDiffCallback<DiffViewModel>() {
		override fun areItemsTheSame(oldItem: DiffViewModel, newItem: DiffViewModel): Boolean {
			return oldItem.id == newItem.id
		}
	}

	data class DiffViewModel(val id: Int, val text: String) : ViewModel
}
