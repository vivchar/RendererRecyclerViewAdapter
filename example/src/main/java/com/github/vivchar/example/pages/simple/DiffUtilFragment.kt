package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.example.BaseScreenFragment
import com.github.vivchar.example.R
import com.github.vivchar.example.widgets.ItemOffsetDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.*

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
class DiffUtilFragment : BaseScreenFragment() {
	private val yourDataProvider = YourDataProvider()
	private var adapter: RendererRecyclerViewAdapter? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

		adapter = MyAdapter()
		adapter?.setDiffCallback(DiffCallback())
//		adapter.enableDiffUtil(); /* Or just call it to enable DiffUtil with DefaultDiffCallback */

		adapter?.registerRenderer(
			ViewRenderer<DiffViewModel, ViewFinder>(
				R.layout.item_simple_square,
				DiffViewModel::class.java
			) { model, finder, _ ->
				finder
					.setText(R.id.text, model.text)
					.setOnClickListener(R.id.text) { reloadItems(model) }
			}
		)

		adapter?.setItems(yourDataProvider.diffItems)

		val view = inflater.inflate(R.layout.fragment_list, container, false)
		val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
		recyclerView.adapter = adapter
		recyclerView.layoutManager = GridLayoutManager(context, 4)
		recyclerView.addItemDecoration(ItemOffsetDecoration(10))

		return view
	}

	private fun reloadItems(model: DiffViewModel) = adapter!!.setItems(yourDataProvider.getUpdatedDiffItems(model))

	private inner class DiffCallback : DefaultDiffCallback<DiffViewModel>() {
		override fun areItemsTheSame(oldItem: DiffViewModel, newItem: DiffViewModel): Boolean {
			return oldItem.id == newItem.id
		}
	}

	data class DiffViewModel(val id: Int, val text: String) : ViewModel
}