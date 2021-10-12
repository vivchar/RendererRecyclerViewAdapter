package com.github.vivchar.data.repositories

import android.util.Log
import com.github.vivchar.data.GithubClient
import com.github.vivchar.domain.entities.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.ReplaySubject

/**
 * Created by Vivchar Vitaly on 09.10.17.
 */
class StargazersRepository(private val client: GithubClient) {

	private val usersSubject = ReplaySubject.createWithSize<List<User>>(1)
	private var currentPage = 1
	private var lastLoadedPage = 0
	private var hasMore = false
	private var isReloading = false
	private val originalUsers: MutableList<User> = ArrayList()
	val all: Observable<List<User>> get() = usersSubject.hide()
	val top10: Observable<List<User>> get() = usersSubject.hide().map { ArrayList(it.subList(0, it.size.coerceAtMost(10))) }

//	fun sendReloadRequest(): Observable<List<User>> {
//		Log.d(TAG, "sendReloadRequest")
//		/* vivchar: to avoid the API rate limit of the github https://developer.github.com/v3/#rate-limiting */
//		if (usersSubject.value!!.isEmpty()) {
//			isReloading = true
//			currentPage = 1
//			fetchStargazers(currentPage)
//		} else {
//			usersSubject.onNext(ArrayList(originalUsers)) //temporary workaround
//		}
//	}

	fun sendLoadMoreRequest() {
		if (hasMore) {
			/* vivchar: think about moving subscribe to Presenter or Interactor */
			fetchStargazers(lastLoadedPage + 1).subscribe()
		}
	}

	private fun fetchStargazers(page: Int): Observable<List<User>> {
		Log.d(TAG, "sendPageLoadRequest: $page")
		return client.fetchStargazers(page).doOnNext { onStargazersReceived(page, it) }
	}

	fun onApplicationStarted() {
		/* move to interactor/presenter */
		fetchStargazers(currentPage).subscribe()
	}

	private fun onStargazersReceived(page: Int, list: List<User>) {
		Log.d(TAG, "onStargazersReceived: " + page + " list: " + list.size)
		lastLoadedPage = page
		hasMore = list.isNotEmpty()
		val newList = ArrayList<User>()
		if (isReloading) {
			isReloading = false
		} else if (usersSubject.value != null) { //load more response
			newList.addAll(0, usersSubject.value!!)
		}
		newList.addAll(list)
		originalUsers.addAll(ArrayList(newList))
		usersSubject.onNext(newList)
	}

	companion object {
		private val TAG = StargazersRepository::class.java.simpleName
	}
}