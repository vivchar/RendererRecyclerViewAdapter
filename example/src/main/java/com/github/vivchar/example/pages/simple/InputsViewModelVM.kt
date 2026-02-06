package com.github.vivchar.example.pages.simple

import androidx.lifecycle.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel as ItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class InputsUiState(val items: List<ItemModel>)

class InputsViewModelVM : ViewModel() {

	private val dataProvider = YourDataProvider()
	private val _state = MutableStateFlow(InputsUiState(items = dataProvider.inputsModels))
	val state: StateFlow<InputsUiState> = _state
}
