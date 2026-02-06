package com.github.vivchar.example.pages.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DemoScreen(val title: String, val actionId: Int)

data class HomeUiState(val screens: List<DemoScreen>)

class HomeViewModel : ViewModel() {

	private val _state = MutableStateFlow(HomeUiState(screens = emptyList()))
	val state: StateFlow<HomeUiState> = _state

	init {
		_state.value = HomeUiState(
			screens = listOf(
				DemoScreen("View Renderer", com.github.vivchar.example.R.id.action_home_to_viewRenderer),
				DemoScreen("Composite View Renderer", com.github.vivchar.example.R.id.action_home_to_compositeViewRenderer),
				DemoScreen("View State", com.github.vivchar.example.R.id.action_home_to_viewState),
				DemoScreen("Diff Util", com.github.vivchar.example.R.id.action_home_to_diffUtil),
				DemoScreen("Payload", com.github.vivchar.example.R.id.action_home_to_payload),
				DemoScreen("Inputs View State", com.github.vivchar.example.R.id.action_home_to_inputs),
				DemoScreen("Load More", com.github.vivchar.example.R.id.action_home_to_loadMore),
				DemoScreen("GitHub Demo", com.github.vivchar.example.R.id.action_home_to_github),
			)
		)
	}
}
