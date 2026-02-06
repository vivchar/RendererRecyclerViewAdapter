package com.github.vivchar.example.pages.simple

import androidx.lifecycle.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel as ItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DiffUtilUiState(val items: List<ItemModel?>)

class DiffUtilViewModel : ViewModel() {

	private val dataProvider = YourDataProvider()
	private val _state = MutableStateFlow(DiffUtilUiState(items = dataProvider.diffItems.toList()))
	val state: StateFlow<DiffUtilUiState> = _state

	fun onItemClicked(model: DiffUtilFragment.DiffViewModel) {
		_state.value = DiffUtilUiState(items = dataProvider.getUpdatedDiffItems(model).toList())
	}
}
