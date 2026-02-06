package com.github.vivchar.example.pages.github

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vivchar.example.network.NetworkBridge
import com.github.vivchar.example.pages.github.items.CategoryModel
import com.github.vivchar.example.pages.github.items.ForkModel
import com.github.vivchar.example.pages.github.items.RecyclerViewModel
import com.github.vivchar.example.pages.github.items.StargazerModel
import com.github.vivchar.network.MainManager
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel as ItemModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class GithubUiState(
	val items: List<ItemModel> = emptyList(),
	val isLoading: Boolean = false,
	val showLoadMore: Boolean = false,
)

sealed class GithubEvent {
	data class ShowSnackbar(val message: String, val url: String? = null) : GithubEvent()
	data class ShowSelectedUsers(val users: ArrayList<ItemModel>) : GithubEvent()
	object ClearSelections : GithubEvent()
}

class GithubViewModel : ViewModel() {

	private val networkBridge = NetworkBridge(
		MainManager.instance.stargazersManager,
		MainManager.instance.forksManager,
	)

	private val _state = MutableStateFlow(GithubUiState())
	val state: StateFlow<GithubUiState> = _state

	private val _events = MutableSharedFlow<GithubEvent>()
	val events = _events.asSharedFlow()

	private var count = 0
	private val selectedUsers = ArrayList<StargazerModel>()
	private var isLoadingMore = false
	var showDoneButton = false
		private set

	init {
		viewModelScope.launch {
			combine(
				networkBridge.allStargazers,
				networkBridge.topStargazers,
				networkBridge.forks,
			) { stargazers, topStargazers, forks ->
				buildItems(stargazers, topStargazers, forks)
			}.collect { items ->
				Log.d(TAG, "updating...")
				isLoadingMore = false
				_state.value = _state.value.copy(items = items, isLoading = false, showLoadMore = false)
			}
		}
	}

	private fun buildItems(
		stargazers: List<com.github.vivchar.network.models.GithubUser>,
		topStargazers: List<com.github.vivchar.network.models.GithubUser>,
		forks: List<com.github.vivchar.network.models.GithubFork>,
	): List<ItemModel> {
		val topStargazerModels = topStargazers.map {
			StargazerModel(it.id, it.login, it.avatarUrl, it.htmlUrl)
		}.toMutableList()
		val stargazerModels = stargazers.map {
			StargazerModel(it.id, it.login, it.avatarUrl, it.htmlUrl)
		}.toMutableList()
		val forkModels = forks.map {
			ForkModel(it.ownerLogin, it.ownerAvatarUrl, it.ownerHtmlUrl)
		}

		Log.d(TAG, "topStargazersModels: " + topStargazerModels.size)
		if (count % 4 == 0 && topStargazerModels.size >= 3) {
			topStargazerModels.removeAt(2)
			Log.d(TAG, "removing $count")
		} else if (count % 2 == 0 && topStargazerModels.size >= 3) {
			val removed = topStargazerModels.removeAt(2)
			topStargazerModels.add(1, removed)
			Log.d(TAG, "moving $count")
		} else {
			Log.d(TAG, "restoring $count")
		}

		val allModels = ArrayList<ItemModel>()
		val firstTitle = "First Stargazers"
		allModels.add(CategoryModel(firstTitle))
		allModels.add(RecyclerViewModel(firstTitle.hashCode(), ArrayList(topStargazerModels)))
		val forksTitle = "Forks"
		allModels.add(CategoryModel(forksTitle))
		allModels.add(RecyclerViewModel(forksTitle.hashCode(), ArrayList(forkModels)))
		val allTitle = "All Stargazers"
		allModels.add(CategoryModel(allTitle))
		val stargazer = stargazerModels.removeAt(0)
		stargazerModels.add(count % 3, stargazer)
		stargazerModels.removeAt(if (count % 2 == 0) 4 else 5)
		allModels.addAll(ArrayList(stargazerModels))
		return allModels
	}

	fun onRefresh() {
		Log.d(TAG, "================================================")
		count++
		_state.value = _state.value.copy(isLoading = true)
		networkBridge.reloadStargazers()
	}

	fun onStargazerClicked(model: StargazerModel, isChecked: Boolean) {
		if (isChecked) {
			selectedUsers.add(model)
			viewModelScope.launch { _events.emit(GithubEvent.ShowSnackbar(model.name, model.htmlUrl)) }
		} else {
			selectedUsers.remove(model)
		}
		showDoneButton = selectedUsers.isNotEmpty()
	}

	fun onCategoryClicked(model: CategoryModel) {
		viewModelScope.launch { _events.emit(GithubEvent.ShowSnackbar(model.name)) }
	}

	fun onDoneClicked() {
		if (selectedUsers.isNotEmpty()) {
			val unique = HashSet(selectedUsers)
			selectedUsers.clear()
			selectedUsers.addAll(unique)
			viewModelScope.launch {
				_events.emit(GithubEvent.ShowSelectedUsers(ArrayList(selectedUsers)))
				_events.emit(GithubEvent.ClearSelections)
			}
			selectedUsers.clear()
			showDoneButton = false
		}
	}

	fun onLoadMore() {
		if (!isLoadingMore) {
			Log.d(TAG, "onLoadMore")
			isLoadingMore = true
			networkBridge.loadMoreStargazers()
			_state.value = _state.value.copy(showLoadMore = true)
		}
	}

	companion object {
		private val TAG = GithubViewModel::class.java.simpleName
	}
}
