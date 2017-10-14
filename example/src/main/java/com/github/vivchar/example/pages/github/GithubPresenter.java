package com.github.vivchar.example.pages.github;

import android.support.annotation.NonNull;

import com.github.vivchar.example.IPresenter;
import com.github.vivchar.example.IView;
import com.github.vivchar.example.pages.github.items.category.CategoryModel;
import com.github.vivchar.example.pages.github.items.list.RecyclerViewModel;
import com.github.vivchar.example.pages.github.items.fork.ForkModel;
import com.github.vivchar.example.pages.github.items.stargazer.StargazerModel;
import com.github.vivchar.network.ForksManager;
import com.github.vivchar.network.StargazersManager;
import com.github.vivchar.network.Listener;
import com.github.vivchar.network.models.GithubFork;
import com.github.vivchar.network.models.GithubUser;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vivchar on 10.10.17.
 */

class GithubPresenter
		implements IPresenter
{
	@NonNull
	private final StargazersManager mStargazersManager;
	@NonNull
	private final ForksManager mForksManager;
	@NonNull
	private final View mView;

	@NonNull
	private final ArrayList<ItemModel> mForksModels = new ArrayList<>();
	@NonNull
	private final ArrayList<ItemModel> mStargazersModels = new ArrayList<>();
	@NonNull
	private final ArrayList<ItemModel> mFirstStargazersModels = new ArrayList<>();

	public GithubPresenter(@NonNull final StargazersManager stargazersManager,
	                       @NonNull final ForksManager forksManager,
	                       @NonNull final View view) {
		mStargazersManager = stargazersManager;
		mForksManager = forksManager;
		mView = view;
	}

	@Override
	public void viewShown() {
		mView.showProgressView();
		mStargazersManager.addStargazersListener(mStargazersListener);
		mForksManager.addForksListener(mForksListener);
	}

	@Override
	public void viewHidden() {
		mStargazersManager.removeStargazersListener(mStargazersListener);
		mForksManager.removeForksListener(mForksListener);
	}

	@NonNull
	private final Listener<List<GithubFork>> mForksListener = new Listener<List<GithubFork>>()
	{
		@Override
		public void onChange(@NonNull final List<GithubFork> data) {
			if (!data.isEmpty()) {
				mForksModels.clear();
				for (final GithubFork fork : data) {
					mForksModels.add(new ForkModel(fork.getOwner().getLogin(), fork.getOwner().getAvatarUrl(), fork.getOwner().getHtmlUrl()));
				}
				updateView();
			}
		}
	};

	@NonNull
	private final Listener<List<GithubUser>> mStargazersListener = new Listener<List<GithubUser>>()
	{
		@Override
		public void onChange(@NonNull final List<GithubUser> data) {
			if (!data.isEmpty()) {

				/* vivchar: just for example, You can use other data. */
				final List<GithubUser> topList = data.subList(0, Math.min(10, data.size()));

				mFirstStargazersModels.clear();
				for (int i = 0; i < topList.size(); i++) {
					final GithubUser topUser = topList.get(i);
					mFirstStargazersModels.add(new StargazerModel(topUser.getLogin(), topUser.getAvatarUrl(), topUser.getHtmlUrl()));
				}

				mStargazersModels.clear();
				for (final GithubUser githubUser : data) {
					mStargazersModels.add(new StargazerModel(githubUser.getLogin(), githubUser.getAvatarUrl(), githubUser.getHtmlUrl()));
				}

				updateView();
			}
		}
	};

	//TODO: use the combineLatest of the RX library
	private void updateView() {
		final ArrayList<ItemModel> items = new ArrayList<>();

		final String firstTitle = "First Stargazers";
		items.add(new CategoryModel(firstTitle));
		items.add(new RecyclerViewModel(new ArrayList<>(mFirstStargazersModels)));

		final String forksTitle = "Forks";
		items.add(new CategoryModel(forksTitle));
		items.add(new RecyclerViewModel(new ArrayList<>(mForksModels)));

		final String allTitle = "All Stargazers";
		items.add(new CategoryModel(allTitle));
		Collections.shuffle(mStargazersModels);
		items.addAll(new ArrayList<>(mStargazersModels));

		mView.hideProgressView();
		mView.updateList(items);
	}

	public void onRefresh() {
		mView.showProgressView();
		mStargazersManager.sendReloadRequest();
	}

	public void onStargazerClicked(@NonNull final StargazerModel model) {
		mView.showMessageView(model.getName(), model.getHtmlUrl());
	}

	public void onCategoryClicked(@NonNull final CategoryModel model) {
		mView.showMessageView(model.getName());
	}

	public void onForkClicked(@NonNull final ForkModel model) {
		mView.showMessageView(model.getName(), model.getHtmlUrl());
	}

	public interface View
			extends IView
	{
		void updateList(@NonNull ArrayList<ItemModel> list);
		void showProgressView();
		void hideProgressView();
		void showMessageView(@NonNull String message, @NonNull String url);
		void showMessageView(@NonNull String message);
	}
}
