package com.github.vivchar.example.network

import com.github.vivchar.network.ForksManager
import com.github.vivchar.network.StargazersManager
import com.github.vivchar.network.models.GithubFork
import com.github.vivchar.network.models.GithubUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.rx3.asFlow

class NetworkBridge(
	private val stargazersManager: StargazersManager,
	private val forksManager: ForksManager,
) {

	val allStargazers: Flow<List<GithubUser>> get() = stargazersManager.all.asFlow()

	val topStargazers: Flow<List<GithubUser>> get() = stargazersManager.top10.asFlow()

	val forks: Flow<List<GithubFork>> get() = forksManager.githubForks.asFlow()

	fun reloadStargazers() = stargazersManager.sendReloadRequest()

	fun loadMoreStargazers() = stargazersManager.sendLoadMoreRequest()
}
