package com.github.vivchar.example.interactors

import android.util.Log
import com.github.vivchar.data.repositories.ForksRepository
import com.github.vivchar.data.repositories.StargazersRepository
import com.github.vivchar.domain.entities.Fork
import com.github.vivchar.domain.entities.User
import com.github.vivchar.example.pages.github.GithubPresenter
import com.github.vivchar.example.pages.github.items.CategoryModel
import com.github.vivchar.example.pages.github.items.ForkModel
import com.github.vivchar.example.pages.github.items.RecyclerViewModel
import com.github.vivchar.example.pages.github.items.StargazerModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables

class GetAllCategoriesInteractor(
	private val stargazersRepository: StargazersRepository,
	private val forksRepository: ForksRepository
) : Interactor2<Unit, Observable<List<Any>>> {
	private var count = 0

	override fun execute(params: Unit): Observable<List<Any>> {
		return Observables.combineLatest(
			stargazersRepository.all,
			stargazersRepository.top10,
			forksRepository.forks
		).map { map(it) }
	}

	/* think about moving this from Interactor to Presenter, since there are UI models which break dependency rule */
	private fun map(triple: Triple<List<User>, List<User>, List<Fork>>): List<Any> {
		val allModels = ArrayList<Any>()
		val forkModels = triple.third.map { ForkModel(it.owner.login, it.owner.avatarUrl, it.owner.htmlUrl) }.toMutableList()
		val topStargazersModels = triple.first.map { StargazerModel(it.id, it.login, it.avatarUrl, it.htmlUrl) }.toMutableList()
		val stargazerModels = triple.second.map { StargazerModel(it.id, it.login, it.avatarUrl, it.htmlUrl) }.toMutableList()
		/*
		 * vivchar: Let's change positions for the DiffUtil demonstration.
		 *
		 * I don't change the first item position because here is the bug
		 * https://stackoverflow.com/a/43461324/4894238
		 * use RendererRecyclerViewAdapter.setUpdateCallback(ListUpdateCallback) if you want
		 */
		Log.d(TAG, "topStargazersModels: " + topStargazersModels.size)
		if (count % 4 == 0 && topStargazersModels.size >= 3) {
			topStargazersModels.removeAt(2)
			Log.d(TAG, "removing $count")
		} else if (count % 2 == 0 && topStargazersModels.size >= 3) {
			val removed: StargazerModel = topStargazersModels.removeAt(2)
			topStargazersModels.add(1, removed)
			Log.d(TAG, "moving $count")
		} else {
			Log.d(TAG, "restoring $count")
		}
		val firstTitle = "First Stargazers"
		allModels.add(CategoryModel(firstTitle))
		val firstID = firstTitle.hashCode()
		allModels.add(RecyclerViewModel(firstID, ArrayList(topStargazersModels)))
		val forksTitle = "Forks"
		allModels.add(CategoryModel(forksTitle))
		val forksID = forksTitle.hashCode()
		allModels.add(RecyclerViewModel(forksID, forkModels))
		val allTitle = "All Stargazers"
		allModels.add(CategoryModel(allTitle))
		val stargazer: StargazerModel = stargazerModels.removeAt(0)
		stargazerModels.add(count % 3, stargazer)
		stargazerModels.removeAt(if (count % 2 == 0) 4 else 5)
		allModels.addAll(ArrayList(stargazerModels))
		return allModels
	}

	private fun <T, R> Observable<out Iterable<T>>.mapIterable(mapper: (T) -> R): Observable<List<R>> {
		return map { iterable -> iterable.map { mapper(it) } }
	}

	companion object {
		private val TAG = GithubPresenter::class.java.simpleName
	}
}