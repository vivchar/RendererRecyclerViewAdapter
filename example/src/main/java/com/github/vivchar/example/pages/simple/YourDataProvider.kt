package com.github.vivchar.example.pages.simple

import com.github.vivchar.example.pages.simple.DiffUtilFragment.DiffViewModel
import com.github.vivchar.example.pages.simple.InputsFragment.InputViewModel
import com.github.vivchar.example.pages.simple.LoadMoreFragment.SimpleViewModel
import com.github.vivchar.example.pages.simple.PayloadFragment.PayloadViewModel
import com.github.vivchar.example.pages.simple.ViewRendererFragment.RectViewModel
import com.github.vivchar.example.pages.simple.ViewStateFragment.StateViewModel
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
class YourDataProvider {
	private val executor = ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, LinkedBlockingQueue())

	val diffItems = mutableListOf<ViewModel>().apply { for (i in 0..49) add(DiffViewModel(i, i.toString())) }
	val squareItems = mutableListOf<ViewModel>().apply { for (i in 0..49) add(RectViewModel(i, i.toString())) }
	val compositeSimpleItems = mutableListOf<ViewModel>().apply { for (i in 0..49) add(DefaultCompositeViewModel(diffItems)) }
	val inputsModels = mutableListOf<ViewModel>().apply { for (i in 0..49) add(InputViewModel(i, String.format("Value %d", i))) }
	val stateItems = mutableListOf<ViewModel>().apply { for (i in 0..49) add(StateViewModel(i, diffItems)) }
	val payloadItems = mutableListOf<PayloadViewModel>().apply { for (i in 0..49) add(PayloadViewModel(i, i.toString(), "model ID: $i")) }
	val loadMoreItems = mutableListOf<ViewModel>().apply { for (i in 0..30) add(SimpleViewModel(i.toString())) }

	fun getUpdatedDiffItems(model: DiffViewModel?): List<ViewModel?> {
		val clickedIndex = diffItems.indexOf(model)
		val clickedModel = diffItems.removeAt(clickedIndex)
		val remove = diffItems.removeAt(0)
		diffItems.shuffle()

		/* https://stackoverflow.com/a/43461324/4894238
		 use RendererRecyclerViewAdapter.setUpdateCallback(ListUpdateCallback) if you want */
		diffItems.add(0, remove)
		diffItems.add(clickedIndex, clickedModel)
		return diffItems
	}

	fun getChangedPayloadItems(model: PayloadViewModel): List<PayloadViewModel> {
		/* Just for example */
		val newList = ArrayList<PayloadViewModel>()
		for (viewModel in payloadItems) {
			if (viewModel.id == model.id) {
				newList.add(
					PayloadViewModel(
						model.id, (Integer.valueOf(model.text) + 1).toString(),
						"model ID: " + model.id
					)
				)
			} else {
				newList.add(viewModel)
			}
		}
		payloadItems.clear()
		payloadItems.addAll(newList)
		return payloadItems
	}

	fun getLoadMoreItems(listener: (List<ViewModel>) -> Unit) {
		executor.execute {
			try {
				Thread.sleep(2000)
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
			val size = loadMoreItems.size
			for (i in size until size + 30) {
				loadMoreItems.add(SimpleViewModel(i.toString()))
			}
			listener.invoke(ArrayList(loadMoreItems))
		}
	}
}