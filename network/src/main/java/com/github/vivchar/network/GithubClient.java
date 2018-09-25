package com.github.vivchar.network;

import androidx.annotation.NonNull;

import com.github.vivchar.network.models.GithubFork;
import com.github.vivchar.network.models.GithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */

class GithubClient {

	@NonNull
	private final GithubAPI mGithubAPI;
	@NonNull
	private final EventListener mListener;

	public GithubClient(@NonNull final GithubAPI githubAPI, @NonNull final EventListener listener) {
		mGithubAPI = githubAPI;
		mListener = listener;
	}

	public void sendStargazersRequest(final int page) {
		mGithubAPI.getStargazers(page).enqueue(new Callback<List<GithubUser>>() {
			@Override
			public void onResponse(final Call<List<GithubUser>> call, final Response<List<GithubUser>> response) {
				final List<GithubUser> body = response.body();
				if (response.isSuccessful() && body != null) {
					mListener.onStargazersReceived(page, body);
				} else {
					mListener.onStargazersFailed(page);
				}
			}

			@Override
			public void onFailure(final Call<List<GithubUser>> call, final Throwable t) {
				mListener.onStargazersFailed(page);
			}
		});
	}

	public void sendForksRequest() {
		mGithubAPI.getForks().enqueue(new Callback<List<GithubFork>>() {
			@Override
			public void onResponse(final Call<List<GithubFork>> call, final Response<List<GithubFork>> response) {
				final List<GithubFork> body = response.body();
				if (response.isSuccessful() && body != null) {
					mListener.onForksReceived(body);
				} else {
					mListener.onForksFailed("Response is failed");
				}
			}

			@Override
			public void onFailure(final Call<List<GithubFork>> call, final Throwable t) {
				mListener.onForksFailed(t.getMessage());
			}
		});
	}

	public interface EventListener {
		void onStargazersReceived(int page, @NonNull List<GithubUser> stargazers);
		void onStargazersFailed(int page);
		void onForksReceived(@NonNull List<GithubFork> forks);
		void onForksFailed(@NonNull final String message);
	}
}
