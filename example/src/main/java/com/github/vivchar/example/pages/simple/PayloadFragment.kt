package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

class PayloadFragment : BaseFragment<FragmentListBinding>() {

	private val viewModel: PayloadViewModelVM by viewModels()
	private val adapter = MyAdapter()

	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentListBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		adapter.setDiffCallback(PayloadDiffCallback())
		adapter.registerRenderer(
			ViewRenderer<PayloadViewModel, ViewFinder>(
				R.layout.item_payload_square,
				PayloadViewModel::class.java
			) { model, finder, payloads: List<Any> ->
				finder.setOnClickListener(R.id.text) { viewModel.onItemClicked(model) }

				val textView = finder.find<TextView>(R.id.text)
				if (payloads.isEmpty()) {
					textView.text = model.text
					finder.setText(R.id.desciption, model.description)
				} else {
					val payload = payloads[0]
					if (payload == TEXT_CHANGED) {
						textView.rotation = 0f
						textView.animate().rotation(360f).start()
						textView.text = model.text
					} else if (payload == DESCRIPTION_CHANGED) {
						finder.setText(R.id.desciption, model.description)
					}
				}
			}
		)

		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
		binding.recyclerView.addItemDecoration(ItemOffsetDecoration(10))

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.state.collect { state ->
					adapter.setItems(state.items)
				}
			}
		}
	}

	private inner class PayloadDiffCallback : DefaultDiffCallback<PayloadViewModel>() {
		override fun areItemsTheSame(oldItem: PayloadViewModel, newItem: PayloadViewModel): Boolean {
			return oldItem.id == newItem.id
		}

		override fun getChangePayload(oldItem: PayloadViewModel, newItem: PayloadViewModel): Any? {
			return when {
				oldItem.text != newItem.text -> TEXT_CHANGED
				oldItem.description != newItem.description -> DESCRIPTION_CHANGED
				else -> super.getChangePayload(oldItem, newItem)
			}
		}
	}

	data class PayloadViewModel(val id: Int, val text: String, val description: String) : ViewModel

	companion object {
		const val TEXT_CHANGED = 1
		const val DESCRIPTION_CHANGED = 2
	}
}
