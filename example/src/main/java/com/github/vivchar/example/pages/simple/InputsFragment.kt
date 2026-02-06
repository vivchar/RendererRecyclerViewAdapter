package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.vivchar.example.R
import com.github.vivchar.example.base.BaseFragment
import com.github.vivchar.example.databinding.FragmentListBinding
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.*
import kotlinx.coroutines.launch

class InputsFragment : BaseFragment<FragmentListBinding>() {

	private val viewModel: InputsViewModelVM by viewModels()
	private val adapter = MyAdapter()

	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentListBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		adapter.registerRenderer(
			ViewRenderer(
				R.layout.item_input,
				InputViewModel::class.java,
				{ model, finder, _ -> finder.setText(R.id.text, model.value) },
				viewStateProvider
			)
		)

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

	private val viewStateProvider: ViewStateProvider<InputViewModel, ViewHolder<ViewFinder>>
		get() = object : ViewStateProvider<InputViewModel, ViewHolder<ViewFinder>> {
			override fun createViewState(): ViewState<*> {
				return object : ViewState<ViewHolder<ViewFinder>> {
					private var savedValue: String? = null

					override fun clear(holder: ViewHolder<ViewFinder>) {
						holder.viewFinder.setText(R.id.text, "")
					}

					override fun save(holder: ViewHolder<ViewFinder>) {
						savedValue = holder.viewFinder.find<EditText>(R.id.text).text.toString()
					}

					override fun restore(holder: ViewHolder<ViewFinder>) {
						holder.viewFinder.setText(R.id.text, savedValue)
					}
				}
			}

			override fun createViewStateID(model: InputViewModel) = model.id
		}

	data class InputViewModel(val id: Int, val value: String) : ViewModel
}
