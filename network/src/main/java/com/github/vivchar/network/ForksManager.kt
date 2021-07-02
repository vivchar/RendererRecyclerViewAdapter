package com.github.vivchar.network

import com.github.vivchar.network.models.GithubFork
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.ReplaySubject

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */
class ForksManager internal constructor(private val client: GithubClient) {

	private val githubForksSubject = ReplaySubject.createWithSize<List<GithubFork>>(1)

	fun onApplicationStarted() {
		client.sendForksRequest()
	}

	fun onForksReceived(forks: List<GithubFork>) {
		githubForksSubject.onNext(forks)
	}

	fun onForksFailed(message: String) {
		githubForksSubject.onNext(ArrayList())
//		mGithubForks.onError(new Exception(message));
	}

	val githubForks: Observable<List<GithubFork>> get() = githubForksSubject.hide()
}