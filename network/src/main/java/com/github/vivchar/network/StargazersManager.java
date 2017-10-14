package com.github.vivchar.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.vivchar.network.models.GithubUser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by vivchar on 09.10.17.
 */

public class StargazersManager
{

	private static final String TAG = StargazersManager.class.getSimpleName();

	@NonNull
	private final GithubClient mClient;
	@NonNull
	private final List<GithubUser> mGithubUsers = new ArrayList<>();
	@NonNull
	private final List<Listener<List<GithubUser>>> mStargazersListeners = new ArrayList<>();
	private int mCurrentPage = 1;
	private int mLastLoadedPage = 0;
	private boolean mHasMore = false;
	private boolean mReloading = false;

	StargazersManager(@NonNull final GithubClient client) {
		mClient = client;
	}

	public void sendReloadRequest() {
		mReloading = true;
		mCurrentPage = 1;
		sendPageLoadRequest(mCurrentPage);
	}

	private void sendPageLoadRequest(final int page) {
		mClient.sendStargazersRequest(page);
	}

	public void onApplicationStarted() {
		sendPageLoadRequest(mCurrentPage);
	}

	public void onStargazersReceived(final int page, @NonNull final List<GithubUser> list) {
		mLastLoadedPage = page;
		mHasMore = !list.isEmpty();
		if (mReloading) {
			mReloading = false;
			mGithubUsers.clear();
		}
		mGithubUsers.addAll(list);
		notifyStargazersChanged();
	}

	public void onStargazersFailed(final int page) {
		Log.e(TAG, "onStargazersFailed: " + page);
		notifyStargazersChanged();
	}

	public void addStargazersListener(@NonNull final Listener<List<GithubUser>> listener) {
		mStargazersListeners.add(listener);
		listener.onChange(mGithubUsers);
	}

	public void removeStargazersListener(@NonNull final Listener<List<GithubUser>> listener) {
		mStargazersListeners.remove(listener);
	}

	//TODO: rework to rx
	private void notifyStargazersChanged() {
		for (final Listener<List<GithubUser>> listener : mStargazersListeners) {
			listener.onChange(mGithubUsers);
		}
	}
}