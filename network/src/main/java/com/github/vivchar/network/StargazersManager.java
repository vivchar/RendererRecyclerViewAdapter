package com.github.vivchar.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.vivchar.network.models.GithubUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.ReplaySubject;


/**
 * Created by Vivchar Vitaly on 09.10.17.
 */

public class StargazersManager {

	private static final String TAG = StargazersManager.class.getSimpleName();

	@NonNull
	private final GithubClient mClient;
	@NonNull
	private final ReplaySubject<List<GithubUser>> mGithubUsers = ReplaySubject.createWithSize(1);

	private int mCurrentPage = 1;
	private int mLastLoadedPage = 0;
	private boolean mHasMore = false;
	private boolean mReloading = false;
	@NonNull
	private final List<GithubUser> mOriginalUsers = new ArrayList<>();

	StargazersManager(@NonNull final GithubClient client) {
		mClient = client;
	}

	public void sendReloadRequest() {
		Log.d(TAG, "sendReloadRequest");
		/* vivchar: to avoid the API rate limit of the github https://developer.github.com/v3/#rate-limiting */
		if (mGithubUsers.getValue().isEmpty()) {
			mReloading = true;
			mCurrentPage = 1;
			sendPageLoadRequest(mCurrentPage);
		} else {
			mGithubUsers.onNext(new ArrayList<>(mOriginalUsers)); //temporary workaround
		}
	}

	public void sendLoadMoreRequest() {
		if (mHasMore) {
			sendPageLoadRequest(mLastLoadedPage + 1);
		}
	}

	private void sendPageLoadRequest(final int page) {
		Log.d(TAG, "sendPageLoadRequest: " + page);
		mClient.sendStargazersRequest(page);
	}

	public void onApplicationStarted() {
		sendPageLoadRequest(mCurrentPage);
	}

	public void onStargazersReceived(final int page, @NonNull final List<GithubUser> list) {
		Log.d(TAG, "onStargazersReceived: " + page + " list: " + list.size());
		mLastLoadedPage = page;
		mHasMore = !list.isEmpty();

		final ArrayList<GithubUser> newList = new ArrayList<>();
		if (mReloading) {
			mReloading = false;
		} else if (mGithubUsers.getValue() != null) { //load more response
			newList.addAll(0, mGithubUsers.getValue());
		}
		newList.addAll(list);
		mOriginalUsers.addAll(new ArrayList<>(newList));

		mGithubUsers.onNext(newList);
	}

	public void onStargazersFailed(final int page) {
		Log.e(TAG, "onStargazersFailed: " + page);
		mGithubUsers.onNext(new ArrayList<>());
	}

	@NonNull
	public Observable<List<GithubUser>> getAll() {
		return mGithubUsers.hide();
	}

	@NonNull
	public Observable<List<GithubUser>> getTop10() {
		return mGithubUsers.hide().map(githubUsers -> new ArrayList<>(githubUsers.subList(0, Math.min(githubUsers.size(), 10))));
	}
}