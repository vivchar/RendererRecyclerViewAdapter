package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.example.BaseScreenFragment
import com.github.vivchar.example.R
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
class ViewRendererFragment : BaseScreenFragment() {
	private val yourDataProvider = YourDataProvider()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		val adapter = MyAdapter()
		adapter.registerRenderer(
			ViewRenderer<RectViewModel, ViewFinder>(
				R.layout.item_simple,
				RectViewModel::class.java
			) { model, finder, _ ->
				finder
					.setText(R.id.text, model.text)
					.setOnClickListener(R.id.text) { Toast.makeText(context, "Text Clicked " + model.text, Toast.LENGTH_SHORT).show() }
			}
		)
		adapter.setItems(yourDataProvider.squareItems)

		val view = inflater.inflate(R.layout.fragment_list, container, false)
		val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
		recyclerView.adapter = adapter
		recyclerView.addItemDecoration(BetweenSpacesItemDecoration(10, 10))

		return view
	}

	data class RectViewModel(val id: Int, val text: String) : ViewModel
}