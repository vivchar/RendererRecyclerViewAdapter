package com.github.vivchar.example.pages.github;

import android.support.annotation.NonNull;
import android.util.Log;

import com.annimon.stream.Stream;
import com.github.vivchar.example.IView;
import com.github.vivchar.example.Presenter;
import com.github.vivchar.example.pages.github.items.category.CategoryModel;
import com.github.vivchar.example.pages.github.items.fork.ForkModel;
import com.github.vivchar.example.pages.github.items.list.RecyclerViewModel;
import com.github.vivchar.example.pages.github.items.stargazer.StargazerModel;
import com.github.vivchar.network.ForksManager;
import com.github.vivchar.network.StargazersManager;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

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
		final Observable<List<StargazerModel>> stargazers = mStargazersManager.getAll()
				.map(users -> Stream.of(users)
						.map(user -> new StargazerModel(
								user.getLogin(),
								user.getAvatarUrl(),
								user.getHtmlUrl()
						)).toList()
				);

		final Observable<List<StargazerModel>> topStargazers = mStargazersManager.getTop10()
				.map(users -> Stream.of(users)
						.map(user -> new StargazerModel(
								user.getLogin(),
								user.getAvatarUrl(),
								user.getHtmlUrl()
						)).toList()
				);

		final Observable<List<ForkModel>> forks = mForksManager.getGithubForks()
				.map(items -> Stream.of(items).map(fork -> new ForkModel(
								fork.getOwner().getLogin(),
								fork.getOwner().getAvatarUrl(),
								fork.getOwner().getHtmlUrl()
						)).toList()
				);

		final Observable<List<ViewModel>> combineLatest = Observable.combineLatest(
				stargazers,
				topStargazers,
				forks,
				(stargazerModels, topStargazersModels, forkModels) -> {

					final ArrayList<ViewModel> allModels = new ArrayList<>();


					/*
					 * vivchar: Let's change positions for the DiffUtil demonstration.
					 *
					 * I don't change the first item position because here is the bug
					 * https://stackoverflow.com/a/43461324/4894238
					 */

					Log.d(TAG, "topStargazersModels: " + topStargazersModels.size());
					if (mCount % 4 == 0 && topStargazersModels.size() >= 3) {
						topStargazersModels.remove(2);
						Log.d(TAG, "removing " + mCount);
					} else if (mCount % 2 == 0 && topStargazersModels.size() >= 3) {
						final StargazerModel removed = topStargazersModels.remove(2);
						topStargazersModels.add(1, removed);
						Log.d(TAG, "moving "  + mCount);
					} else {
						Log.d(TAG, "restoring "  + mCount);
					}


					final String firstTitle = "First Stargazers";
					allModels.add(new CategoryModel(firstTitle));
					final int firstID = firstTitle.hashCode();
					allModels.add(new RecyclerViewModel(firstID, new ArrayList<>(topStargazersModels)));


					final String forksTitle = "Forks";
					allModels.add(new CategoryModel(forksTitle));
					final int forksID = forksTitle.hashCode();
					allModels.add(new RecyclerViewModel(forksID, new ArrayList<>(forkModels)));


					final String allTitle = "All Stargazers";
					allModels.add(new CategoryModel(allTitle));
					final StargazerModel stargazer = stargazerModels.remove(0);
					stargazerModels.add(mCount % 3, stargazer);
					stargazerModels.remove(mCount % 2 == 0 ? 4 : 5);
					allModels.addAll(new ArrayList<>(stargazerModels));

					return allModels;
				}
		);

		addSubscription(combineLatest
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnNext(itemModels -> mView.hideProgressView())
				.distinctUntilChanged()
				.subscribe(
						itemModels -> {
							Log.d(TAG, "updating...");
							mLoadingMore = false;
							mView.updateList(itemModels);
						},
						throwable -> Log.d(TAG, "Can't update list: " + throwable.getMessage())
				));
	}

	public void onRefresh() {
		Log.d(TAG, "================================================");
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
		void updateList(@NonNull List<ViewModel> list);
		void showProgressView();
		void hideProgressView();
		void showMessageView(@NonNull String message, @NonNull String url);
		void showMessageView(@NonNull String message);
		void showSelectedUsers(@NonNull ArrayList<ViewModel> list);
		void clearSelections();
		void showDoneButton();
		void hideDoneButton();
		void showLoadMoreView();
	}
}