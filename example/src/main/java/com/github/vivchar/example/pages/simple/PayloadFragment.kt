package com.github.vivchar.example.pages.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.example.BaseScreenFragment
import com.github.vivchar.example.R
import com.github.vivchar.example.widgets.ItemOffsetDecoration
import com.github.vivchar.example.widgets.MyAdapter
import com.github.vivchar.rendererrecyclerviewadapter.*

/**
 * Created by Vivchar Vitaly on 12/29/17.
 */
class PayloadFragment : BaseScreenFragment() {
	private val yourDataProvider = YourDataProvider()
	private var adapter: RendererRecyclerViewAdapter? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		val view = inflater.inflate(R.layout.fragment_list, container, false)
		adapter = MyAdapter()
		adapter?.setDiffCallback(PayloadDiffCallback())
		adapter?.registerRenderer(
			ViewRenderer<PayloadViewModel, ViewFinder>(
				R.layout.item_payload_square,
				PayloadViewModel::class.java
			) { model, finder, payloads: List<Any> ->
				finder.setOnClickListener(R.id.text) { changeItem(model) }

				val textView = finder.find<TextView>(R.id.text)
				if (payloads.isEmpty()) {
					/* full bind */
					textView.text = model.text
					finder.setText(R.id.desciption, model.description)
				} else {
					/* partially bind */
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
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		adapter?.setItems(yourDataProvider.payloadItems)

		val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
		recyclerView.adapter = adapter
		recyclerView.layoutManager = GridLayoutManager(context, 3)
		recyclerView.addItemDecoration(ItemOffsetDecoration(10))
		return view
	}

	private fun changeItem(model: PayloadViewModel) {
		adapter?.setItems(yourDataProvider.getChangedPayloadItems(model))
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