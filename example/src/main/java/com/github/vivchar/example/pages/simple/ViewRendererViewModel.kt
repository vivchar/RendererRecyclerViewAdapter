package com.github.vivchar.example.pages.simple

import androidx.lifecycle.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel as ItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ViewRendererUiState(val items: List<ItemModel>)

class ViewRendererViewModel : ViewModel() {

	private val dataProvider = YourDataProvider()
	private val _state = MutableStateFlow(ViewRendererUiState(items = dataProvider.squareItems))
	val state: StateFlow<ViewRendererUiState> = _state
}
