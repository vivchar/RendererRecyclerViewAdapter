package com.github.vivchar.example.pages.simple

import androidx.lifecycle.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel as ItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ViewStateUiState(val items: List<ItemModel>)

class ViewStateViewModel : ViewModel() {

	private val dataProvider = YourDataProvider()
	private val _state = MutableStateFlow(ViewStateUiState(items = dataProvider.stateItems))
	val state: StateFlow<ViewStateUiState> = _state
}
