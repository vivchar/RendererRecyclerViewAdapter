package com.github.vivchar.example.pages.github

import android.util.Log
import com.github.vivchar.example.*
import com.github.vivchar.example.pages.github.items.CategoryModel
import com.github.vivchar.example.pages.github.items.ForkModel
import com.github.vivchar.example.pages.github.items.RecyclerViewModel
import com.github.vivchar.example.pages.github.items.StargazerModel
import com.github.vivchar.network.ForksManager
import com.github.vivchar.network.StargazersManager
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by Vivchar Vitaly on 10.10.17.
 */
internal class GithubPresenter(
	private val router: UIRouter,
	private val menuController: OptionsMenuController,
	private val stargazersManager: StargazersManager,
	private val forksManager: ForksManager,
	private val view: View
) : BasePresenter() {
	private var count = 0
	private val selectedUsers = ArrayList<StargazerModel>()
	private var isLoadingMore = false

	public override fun viewShown() {

		val combineLatest = Observables.combineLatest(
			stargazersManager.all.mapIterable { StargazerModel(it.id, it.login, it.avatarUrl, it.htmlUrl) },
			stargazersManager.top10.mapIterable { StargazerModel(it.id, it.login, it.avatarUrl, it.htmlUrl) },
			forksManager.githubForks.mapIterable { ForkModel(it.ownerLogin, it.ownerAvatarUrl, it.ownerHtmlUrl) }
		) { stargazers, topStargazers, forkModels ->
			val allModels = ArrayList<ViewModel>()
			val topStargazersModels = topStargazers.toMutableList()
			val stargazerModels = stargazers.toMutableList()
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
			allModels.add(RecyclerViewModel(forksID, ArrayList(forkModels)))
			val allTitle = "All Stargazers"
			allModels.add(CategoryModel(allTitle))
			val stargazer: StargazerModel = stargazerModels.removeAt(0)
			stargazerModels.add(count % 3, stargazer)
			stargazerModels.removeAt(if (count % 2 == 0) 4 else 5)
			allModels.addAll(ArrayList(stargazerModels))
			allModels
		}

		addSubscription(combineLatest
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.doOnNext { view.hideProgressView() }
			.distinctUntilChanged()
			.subscribe({ itemModels ->
				Log.d(TAG, "updating...")
				isLoadingMore = false
				view.updateList(itemModels)
			}) { throwable -> Log.d(TAG, "Can't update list: " + throwable.message) }
		)

		addSubscription(
			menuController.itemSelection.subscribe {
				if (it == MenuItemID.DONE) {
					onDoneClicked()
				}
			})
	}

	fun onRefresh() {
		Log.d(TAG, "================================================")
		count++
		view.showProgressView()
		stargazersManager.sendReloadRequest()
	}

	fun onStargazerClicked(model: StargazerModel, isChecked: Boolean) {
		if (isChecked) {
			selectedUsers.add(model)
			view.showMessageView(model.name, model.htmlUrl)
		} else {
			selectedUsers.remove(model)
		}
		if (selectedUsers.isEmpty()) {
			menuController.hideMenuItem(MenuItemID.DONE)
		} else {
			menuController.showMenuItem(MenuItemID.DONE)
		}
	}

	fun onCategoryClicked(model: CategoryModel) {
		view.showMessageView(model.name)
	}

	fun onForkClicked(model: ForkModel) {
		view.showMessageView(model.name, model.htmlUrl)
	}

	fun onDoneClicked() {
		if (selectedUsers.isNotEmpty()) {
			/* removing duplicates: https://stackoverflow.com/a/203992/4894238 */
			val hs: MutableSet<StargazerModel> = HashSet()
			hs.addAll(selectedUsers)
			selectedUsers.clear()
			selectedUsers.addAll(hs)

			/* vivchar: ideally we should map to other model */view.showSelectedUsers(ArrayList(selectedUsers))
			view.clearSelections()
			menuController.hideMenuItem(MenuItemID.DONE)
			selectedUsers.clear()
		}
	}

	fun onLoadMore() {
		if (!isLoadingMore) {
			Log.d(TAG, "onLoadMore")
			isLoadingMore = true
			stargazersManager.sendLoadMoreRequest()
			view.showLoadMoreView()
		}
	}

	interface View : IView {
		fun updateList(list: List<ViewModel>)
		fun showProgressView()
		fun hideProgressView()
		fun showMessageView(message: String, url: String)
		fun showMessageView(message: String)
		fun showSelectedUsers(list: ArrayList<ViewModel>)
		fun clearSelections()
		fun showLoadMoreView()
	}

	private fun <T, R> Observable<out Iterable<T>>.mapIterable(mapper: (T) -> R): Observable<List<R>> {
		return map { iterable -> iterable.map { mapper(it) } }
	}

	companion object {
		private val TAG = GithubPresenter::class.java.simpleName
	}
}