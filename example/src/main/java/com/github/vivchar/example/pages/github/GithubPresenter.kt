package com.github.vivchar.example.pages.github

import android.util.Log
import com.github.vivchar.example.*
import com.github.vivchar.example.pages.github.items.CategoryModel
import com.github.vivchar.example.pages.github.items.ForkModel
import com.github.vivchar.example.pages.github.items.RecyclerViewModel
import com.github.vivchar.example.pages.github.items.StargazerModel
import com.github.vivchar.data.repositories.ForksRepository
import com.github.vivchar.data.repositories.StargazersRepository
import com.github.vivchar.example.interactors.GetAllCategoriesInteractor
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by Vivchar Vitaly on 10.10.17.
 */
internal class GithubPresenter(
	private val getAllCategoriesInteractor: GetAllCategoriesInteractor,
	private val menuController: OptionsMenuController,
	private val stargazersRepository: StargazersRepository,
	private val forksRepository: ForksRepository,
	private val view: View
) : BasePresenter() {
	private var count = 0
	private val selectedUsers = ArrayList<StargazerModel>()
	private var isLoadingMore = false

	public override fun viewShown() {

		getAllCategoriesInteractor.execute(Unit) {
			isLoadingMore = false
			view.updateList(it)
		}

		/* think about it, possible we should move it to Fragment */
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
		stargazersRepository.sendReloadRequest()
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
			stargazersRepository.sendLoadMoreRequest()
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