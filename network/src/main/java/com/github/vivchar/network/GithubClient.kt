package com.github.vivchar.network

import com.github.vivchar.network.models.GithubFork
import com.github.vivchar.network.models.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */
internal class GithubClient(private val githubAPI: GithubAPI, private val listener: EventListener) {

	fun sendStargazersRequest(page: Int) {
		githubAPI.getStargazers(page).enqueue(object : Callback<List<GithubUser>> {
			override fun onResponse(call: Call<List<GithubUser>>, response: Response<List<GithubUser>>) {
				val body = response.body()
				if (response.isSuccessful && body != null) {
					listener.onStargazersReceived(page, body)
				} else {
					listener.onStargazersFailed(page)
				}
			}

			override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
				listener.onStargazersFailed(page)
			}
		})
	}

	fun sendForksRequest() {
		githubAPI.forks.enqueue(object : Callback<List<GithubFork>> {
			override fun onResponse(call: Call<List<GithubFork>>, response: Response<List<GithubFork>>) {
				val body = response.body()
				if (response.isSuccessful && body != null) {
					listener.onForksReceived(body)
				} else {
					listener.onForksFailed("Response is failed")
				}
			}

			override fun onFailure(call: Call<List<GithubFork>>, t: Throwable) {
				listener.onForksFailed(t.message!!)
			}
		})
	}

	interface EventListener {
		fun onStargazersReceived(page: Int, stargazers: List<GithubUser>)
		fun onStargazersFailed(page: Int)
		fun onForksReceived(forks: List<GithubFork>)
		fun onForksFailed(message: String)
	}
}