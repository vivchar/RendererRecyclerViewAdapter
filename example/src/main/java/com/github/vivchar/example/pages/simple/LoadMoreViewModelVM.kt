package com.github.vivchar.example.pages.simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel as ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoadMoreUiState(
	val items: List<ItemModel>,
	val showLoadMore: Boolean = false,
)

class LoadMoreViewModelVM : ViewModel() {

	private val dataProvider = YourDataProvider()
	private val _state = MutableStateFlow(LoadMoreUiState(items = dataProvider.loadMoreItems.toList()))
	val state: StateFlow<LoadMoreUiState> = _state

	private var isLoading = false

	fun onLoadMore() {
		if (isLoading) return
		isLoading = true
		_state.value = _state.value.copy(showLoadMore = true)

		viewModelScope.launch(Dispatchers.IO) {
			delay(2000)
			val size = dataProvider.loadMoreItems.size
			for (i in size until size + 30) {
				dataProvider.loadMoreItems.add(LoadMoreFragment.SimpleViewModel(i.toString()))
			}
			_state.value = LoadMoreUiState(
				items = ArrayList(dataProvider.loadMoreItems),
				showLoadMore = false
			)
			isLoading = false
		}
	}
}
