package com.github.vivchar.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.vivchar.network.models.GithubUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
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

	StargazersManager(@NonNull final GithubClient client) {
		mClient = client;
	}

	public void sendReloadRequest() {
		/* vivchar: to avoid the API rate limit of the github https://developer.github.com/v3/#rate-limiting */
		if (mGithubUsers.getValue().isEmpty()) {
			mReloading = true;
			mCurrentPage = 1;
			sendPageLoadRequest(mCurrentPage);
		} else {
			mGithubUsers.onNext(mGithubUsers.getValue()); //temporary workaround
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
		if (mReloading) {
			mReloading = false;
			mGithubUsers.onNext(list);
		} else {
			if (mGithubUsers.getValue() != null) {
				mGithubUsers.getValue().addAll(list);
				mGithubUsers.onNext(mGithubUsers.getValue());
			} else {
				mGithubUsers.onNext(list);
			}
		}
	}

	public void onStargazersFailed(final int page) {
		Log.e(TAG, "onStargazersFailed: " + page);
		mGithubUsers.onNext(new ArrayList<>());
	}

	@NonNull
	public Observable<List<GithubUser>> getGithubUsers() {
		return mGithubUsers.hide();
	}
}