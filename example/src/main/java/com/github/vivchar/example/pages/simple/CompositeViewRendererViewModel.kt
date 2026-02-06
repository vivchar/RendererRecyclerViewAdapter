package com.github.vivchar.example.pages.simple

import androidx.lifecycle.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel as ItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CompositeUiState(val items: List<ItemModel>)

class CompositeViewRendererViewModel : ViewModel() {

	private val dataProvider = YourDataProvider()
	private val _state = MutableStateFlow(CompositeUiState(items = dataProvider.compositeSimpleItems))
	val state: StateFlow<CompositeUiState> = _state
}
