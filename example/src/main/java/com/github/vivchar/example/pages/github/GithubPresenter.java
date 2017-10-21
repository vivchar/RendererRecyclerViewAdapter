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
import java.util.List;

/**
 * Created by Vivchar Vitaly on 10.10.17.
 */

class GithubPresenter implements IPresenter {

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
	private int mCount = 0;

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
	private final Listener<List<GithubFork>> mForksListener = new Listener<List<GithubFork>>() {
		@Override
		public void onChange(@NonNull final List<GithubFork> data) {
			if (!data.isEmpty()) {
				mForksModels.clear();
				for (final GithubFork fork : data) {
					mForksModels.add(new ForkModel(
							fork.getOwner().getLogin(),
							fork.getOwner().getAvatarUrl(),
							fork.getOwner().getHtmlUrl()
					));
				}
				updateView();
			}
		}
	};

	@NonNull
	private final Listener<List<GithubUser>> mStargazersListener = new Listener<List<GithubUser>>() {
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

		/*
		* vivchar: Let's change positions for the DiffUtil demonstration.
		*
		* I don't change the first item position because here is the bug
		* https://stackoverflow.com/a/43461324/4894238
		*/
		final ItemModel removed = mFirstStargazersModels.remove(1);
		mFirstStargazersModels.add(mCount % 2 + 1, removed);
		final int stargazersID = firstTitle.hashCode();
		items.add(new RecyclerViewModel(stargazersID, new ArrayList<>(mFirstStargazersModels)));

		final String forksTitle = "Forks";
		items.add(new CategoryModel(forksTitle));
		final int forksID = forksTitle.hashCode();
		items.add(new RecyclerViewModel(forksID, new ArrayList<>(mForksModels)));

		final String allTitle = "All Stargazers";
		items.add(new CategoryModel(allTitle));

		final ItemModel remove = mStargazersModels.remove(0);
		mStargazersModels.add(mCount % 3, remove);

		items.addAll(new ArrayList<>(mStargazersModels));

		mView.hideProgressView();
		mView.updateList(items);
	}

	public void onRefresh() {
		mCount++;
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
			extends IView {
		void updateList(@NonNull ArrayList<ItemModel> list);
		void showProgressView();
		void hideProgressView();
		void showMessageView(@NonNull String message, @NonNull String url);
		void showMessageView(@NonNull String message);
	}
}
