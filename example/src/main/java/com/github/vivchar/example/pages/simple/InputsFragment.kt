package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.example.BaseScreenFragment
import com.github.vivchar.example.R
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.*

class InputsFragment : BaseScreenFragment() {

	private val yourDataProvider = YourDataProvider()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

		val adapter = MyAdapter()
		adapter.registerRenderer(
			ViewRenderer(
				R.layout.item_input,
				InputViewModel::class.java,
				{ model, finder, _ -> finder.setText(R.id.text, model.value) },
				viewStateProvider
			)
		)
		adapter.setItems(yourDataProvider.inputsModels)

		val view = inflater.inflate(R.layout.fragment_list, container, false)
		val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
		recyclerView.adapter = adapter
		recyclerView.addItemDecoration(BetweenSpacesItemDecoration(10, 10))

		return view
	}

	private val viewStateProvider: ViewStateProvider<InputViewModel, ViewHolder<ViewFinder>>
		get() = object : ViewStateProvider<InputViewModel, ViewHolder<ViewFinder>> {
			override fun createViewState(): ViewState<*> {
				return object : ViewState<ViewHolder<ViewFinder>> {
					/* here will be saved a value for each item by createViewStateID() */
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