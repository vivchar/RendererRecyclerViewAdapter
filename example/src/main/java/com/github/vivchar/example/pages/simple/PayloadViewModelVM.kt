package com.github.vivchar.example.pages.simple

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class PayloadUiState(val items: List<PayloadFragment.PayloadViewModel>)

class PayloadViewModelVM : ViewModel() {

	private val dataProvider = YourDataProvider()
	private val _state = MutableStateFlow(PayloadUiState(items = dataProvider.payloadItems.toList()))
	val state: StateFlow<PayloadUiState> = _state

	fun onItemClicked(model: PayloadFragment.PayloadViewModel) {
		_state.value = PayloadUiState(items = dataProvider.getChangedPayloadItems(model).toList())
	}
}
