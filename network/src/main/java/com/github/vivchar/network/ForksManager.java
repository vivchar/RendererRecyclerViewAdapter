package com.github.vivchar.network;

import androidx.annotation.NonNull;

import com.github.vivchar.network.models.GithubFork;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */

public class ForksManager {

	@NonNull
	private final GithubClient mClient;
	@NonNull
	private final ReplaySubject<List<GithubFork>> mGithubForks = ReplaySubject.createWithSize(1);

	public ForksManager(@NonNull final GithubClient client) {
		mClient = client;
	}

	public void onApplicationStarted() {
		mClient.sendForksRequest();
	}

	public void onForksReceived(@NonNull final List<GithubFork> forks) {
		mGithubForks.onNext(forks);
	}

	public void onForksFailed(@NonNull final String message) {
		mGithubForks.onNext(new ArrayList<>());
//		mGithubForks.onError(new Exception(message));
	}

	@NonNull
	public Observable<List<GithubFork>> getGithubForks() {
		return mGithubForks.hide();
	}
}
