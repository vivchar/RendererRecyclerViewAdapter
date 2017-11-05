package com.github.vivchar.example.pages.github;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.vivchar.example.IView;
import com.github.vivchar.example.Presenter;
import com.github.vivchar.example.pages.github.items.category.CategoryModel;
import com.github.vivchar.example.pages.github.items.fork.ForkModel;
import com.github.vivchar.example.pages.github.items.list.RecyclerViewModel;
import com.github.vivchar.example.pages.github.items.stargazer.StargazerModel;
import com.github.vivchar.network.ForksManager;
import com.github.vivchar.network.StargazersManager;
import com.github.vivchar.network.models.GithubFork;
import com.github.vivchar.network.models.GithubUser;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Vivchar Vitaly on 10.10.17.
 */

class GithubPresenter extends Presenter {

	private static final String TAG = GithubPresenter.class.getSimpleName();

	@NonNull
	private final StargazersManager mStargazersManager;
	@NonNull
	private final ForksManager mForksManager;
	@NonNull
	private final View mView;

	private int mCount = 0;
	private final ArrayList<StargazerModel> mSelectedUsers = new ArrayList<>();
	private boolean mLoadingMore = false;

	GithubPresenter(@NonNull final StargazersManager stargazersManager,
	                @NonNull final ForksManager forksManager,
	                @NonNull final View view) {
		mStargazersManager = stargazersManager;
		mForksManager = forksManager;
		mView = view;
	}

	@Override
	public void viewShown() {
		final Observable<List<StargazerModel>> stargazers = mStargazersManager.getGithubUsers()
				.map(githubUsers -> {
					final ArrayList<StargazerModel> items = new ArrayList<>();
					for (final GithubUser githubUser : githubUsers) {
						items.add(new StargazerModel(
								githubUser.getLogin(),
								githubUser.getAvatarUrl(),
								githubUser.getHtmlUrl()
						));
					}
					return items;
				});

		final Observable<List<ForkModel>> forks = mForksManager.getGithubForks()
				.map(items -> {
					final ArrayList<ForkModel> models = new ArrayList<>();
					for (final GithubFork fork : items) {
						models.add(new ForkModel(
								fork.getOwner().getLogin(),
								fork.getOwner().getAvatarUrl(),
								fork.getOwner().getHtmlUrl()
						));
					}
					return models;
				});

		final Observable<List<ItemModel>> combineLatest = Observable.combineLatest(stargazers, forks, (stargazerModels, forkModels) -> {
					final List<StargazerModel> topModels = new ArrayList<>(stargazerModels.subList(0, Math.min(10, stargazerModels.size()
					)));

					/*
					 * vivchar: Let's change positions for the DiffUtil demonstration.
					 *
					 * I don't change the first item position because here is the bug
					 * https://stackoverflow.com/a/43461324/4894238
					 */
					final StargazerModel remove = topModels.remove(2);
					topModels.add(mCount % 2 == 0 ? 2 : 3, remove);

//					if (mCount % 2 == 0) {
//						final int index = 1;
//						topModels.remove(index);
//						topModels.add(index, new StargazerModel(
//								"minion",
//								"http://telegram.org.ru/uploads/posts/2017-03/1490220304_5.png",
//								""
//						));
//					}


					final ArrayList<ItemModel> allModels = new ArrayList<>();

					final String firstTitle = "First Stargazers";
					allModels.add(new CategoryModel(firstTitle));
					final int firstID = firstTitle.hashCode();
					allModels.add(new RecyclerViewModel(firstID, new ArrayList<>(topModels)));


					final String forksTitle = "Forks";
					allModels.add(new CategoryModel(forksTitle));
					final int forksID = forksTitle.hashCode();
					allModels.add(new RecyclerViewModel(forksID, new ArrayList<>(forkModels)));


					final String allTitle = "All Stargazers";
					allModels.add(new CategoryModel(allTitle));
					final StargazerModel stargazer = stargazerModels.remove(0);
					stargazerModels.add(mCount % 3, stargazer);
					allModels.addAll(new ArrayList<>(stargazerModels));

					return allModels;
				}
		);

		addSubscription(combineLatest
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						itemModels -> {
							mLoadingMore = false;
							mView.hideProgressView();
							mView.updateList(itemModels);
						},
						throwable -> Log.d(TAG, "Can't update list: " + throwable.getMessage())
				));
	}

	public void onRefresh() {
		mCount++;
		mView.showProgressView();
		mStargazersManager.sendReloadRequest();
	}

	public void onStargazerClicked(@NonNull final StargazerModel model, final boolean isChecked) {
		if (isChecked) {
			mSelectedUsers.add(model);
			mView.showMessageView(model.getName(), model.getHtmlUrl());
		} else {
			mSelectedUsers.remove(model);
		}

		if (mSelectedUsers.isEmpty()) {
			mView.hideDoneButton();
		} else {
			mView.showDoneButton();
		}
	}

	public void onCategoryClicked(@NonNull final CategoryModel model) {
		mView.showMessageView(model.getName());
	}

	public void onForkClicked(@NonNull final ForkModel model) {
		mView.showMessageView(model.getName(), model.getHtmlUrl());
	}

	public void onDoneClicked() {
		if (!mSelectedUsers.isEmpty()) {

			/* removing duplicates: https://stackoverflow.com/a/203992/4894238 */
			final Set<StargazerModel> hs = new HashSet<>();
			hs.addAll(mSelectedUsers);
			mSelectedUsers.clear();
			mSelectedUsers.addAll(hs);

			/* vivchar: ideally we should map to other model */
			mView.showSelectedUsers(new ArrayList<>(mSelectedUsers));
			mView.clearSelections();
			mView.hideDoneButton();
			mSelectedUsers.clear();
		}
	}

	public void onLoadMore() {
		if (!mLoadingMore) {
			Log.d(TAG, "onLoadMore");
			mLoadingMore = true;
			mStargazersManager.sendLoadMoreRequest();
			mView.showLoadMoreView();
		}
	}

	public interface View extends IView {
		void updateList(@NonNull List<ItemModel> list);
		void showProgressView();
		void hideProgressView();
		void showMessageView(@NonNull String message, @NonNull String url);
		void showMessageView(@NonNull String message);
		void showSelectedUsers(@NonNull ArrayList<ItemModel> list);
		void clearSelections();
		void showDoneButton();
		void hideDoneButton();
		void showLoadMoreView();
	}
}