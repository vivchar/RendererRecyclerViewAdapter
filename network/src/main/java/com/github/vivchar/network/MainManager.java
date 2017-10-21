package com.github.vivchar.network;

import android.app.Application;
import android.support.annotation.NonNull;

import com.github.vivchar.network.models.GithubFork;
import com.github.vivchar.network.models.GithubUser;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vivchar Vitaly on 08.10.17.
 */

public class MainManager implements GithubClient.EventListener {

	public static final String API_URL = "https://api.github.com/";
	private static MainManager sMainManager;
	@NonNull
	private final Application mContext;
	@NonNull
	private final StargazersManager mStargazersManager;
	@NonNull
	private final GithubClient mGithubClient;
	@NonNull
	private final ForksManager mForksManager;

	public static void init(@NonNull final Application application) {
		if (sMainManager == null) {
			sMainManager = new MainManager(application);
		}
	}

	@NonNull
	public static MainManager getInstance() {
		return sMainManager;
	}

	private MainManager(@NonNull final Application context) {
		mContext = context;
		final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
		final GithubAPI githubAPI = retrofit.create(GithubAPI.class);

		mGithubClient = new GithubClient(githubAPI, this);

		mStargazersManager = new StargazersManager(mGithubClient);
		mForksManager = new ForksManager(mGithubClient);

		onApplicationStarted();
	}

	@NonNull
	public StargazersManager getStargazersManager() {
		return mStargazersManager;
	}

	@NonNull
	public ForksManager getForksManager() {
		return mForksManager;
	}

	private void onApplicationStarted() {
		mStargazersManager.onApplicationStarted();
		mForksManager.onApplicationStarted();
	}

	@Override
	public void onStargazersReceived(final int page, @NonNull final List<GithubUser> stargazers) {
		mStargazersManager.onStargazersReceived(page, stargazers);
	}

	@Override
	public void onStargazersFailed(final int page) {
		mStargazersManager.onStargazersFailed(page);
	}

	@Override
	public void onForksReceived(@NonNull final List<GithubFork> forks) {
		mForksManager.onForksReceived(forks);
	}

	@Override
	public void onForksFailed() {
		mForksManager.onForksFailed();
	}
}
