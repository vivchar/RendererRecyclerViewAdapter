package com.github.vivchar.network;

import android.support.annotation.NonNull;

import com.github.vivchar.network.models.GithubFork;
import com.github.vivchar.network.models.GithubUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivchar on 12.10.17.
 */

public class ForksManager
{

	@NonNull
	private final GithubClient mClient;
	@NonNull
	private final List<GithubFork> mGithubForks = new ArrayList<>();
	@NonNull
	private final List<Listener<List<GithubFork>>> mForksListeners = new ArrayList<>();

	public ForksManager(@NonNull final GithubClient client) {
		mClient = client;
	}

	public void onApplicationStarted() {
		mClient.sendForksRequest();
	}

	public void onForksReceived(@NonNull final List<GithubFork> forks) {
		mGithubForks.clear();
		mGithubForks.addAll(forks);
		notifyStargazersChanged();
	}

	public void onForksFailed() {
		mGithubForks.clear();
		notifyStargazersChanged();
	}

	public void addForksListener(@NonNull final Listener<List<GithubFork>> listener) {
		mForksListeners.add(listener);
		listener.onChange(mGithubForks);
	}

	public void removeForksListener(@NonNull final Listener<List<GithubFork>> listener) {
		mForksListeners.remove(listener);
	}

	//TODO: rework to rx
	private void notifyStargazersChanged() {
		for (final Listener<List<GithubFork>> listener : mForksListeners) {
			listener.onChange(mGithubForks);
		}
	}
}
