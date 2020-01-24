package com.github.vivchar.example.pages.github;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.pages.github.items.ItemsDiffCallback;
import com.github.vivchar.example.pages.github.items.category.CategoryModel;
import com.github.vivchar.example.pages.github.items.category.CategoryViewRenderer;
import com.github.vivchar.example.pages.github.items.fork.CustomViewFinder;
import com.github.vivchar.example.pages.github.items.fork.ForkModel;
import com.github.vivchar.example.pages.github.items.fork.ForkViewRenderer;
import com.github.vivchar.example.pages.github.items.list.RecyclerViewRenderer;
import com.github.vivchar.example.pages.github.items.selected.UserViewRenderer;
import com.github.vivchar.example.pages.github.items.stargazer.StargazerModel;
import com.github.vivchar.example.pages.github.items.stargazer.StargazerViewRenderer;
import com.github.vivchar.example.widgets.EndlessScrollListener;
import com.github.vivchar.example.widgets.MyAdapter;
import com.github.vivchar.example.widgets.MyItemDecoration;
import com.github.vivchar.network.MainManager;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.LoadMoreViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewProvider;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */

public class GithubFragment extends BaseScreenFragment {

	public static final int MAX_SPAN_COUNT = 3;
	public static final String RECYCLERVIEW = "recyclerview";
	private boolean mDoneItemVisibility = false;
	private RendererRecyclerViewAdapter mRecyclerViewAdapter;
	private RecyclerView mRecyclerView;
	private GridLayoutManager mLayoutManager;
	private SwipeRefreshLayout mSwipeToRefresh;
	private GithubPresenter mGithubPresenter;
	@Nullable
	private Bundle mSavedInstanceState;

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_github, container, false);

		mGithubPresenter = new GithubPresenter(
				getUIRouter(),
				getMenuController(),
				MainManager.getInstance().getStargazersManager(),
				MainManager.getInstance().getForksManager(),
				mMainPresenterView
		);

		mSwipeToRefresh = inflate.findViewById(R.id.refresh);
		mSwipeToRefresh.setOnRefreshListener(() -> mGithubPresenter.onRefresh());

		mRecyclerViewAdapter = new MyAdapter();
		mRecyclerViewAdapter.setDiffCallback(new ItemsDiffCallback());
		mRecyclerViewAdapter.registerRenderer(new LoadMoreViewBinder(R.layout.item_load_more));
		mRecyclerViewAdapter.registerRenderer(createStargazerRenderer(R.layout.item_user_full_width));
		mRecyclerViewAdapter.registerRenderer(createListRenderer()
				.registerRenderer(new ViewRenderer<ForkModel, CustomViewFinder>(
						R.layout.item_fork,
						ForkModel.class,
						(model, finder, p) -> {
							finder.setUrl(R.id.fork_avatar, model.getAvatarUrl());
							finder.setText(R.id.fork_name, model.getName());
						}
				))
				.registerRenderer(createStargazerRenderer(R.layout.item_user_150))
		);

		mRecyclerViewAdapter.registerRenderer(new ViewRenderer<>(
				R.layout.item_category,
				CategoryModel.class,
				(model, finder, payloads) -> finder
						.find(R.id.title, (ViewProvider<TextView>) view -> view.setText(model.getName()))
						.setOnClickListener(R.id.viewAll, (v -> mGithubPresenter.onCategoryClicked(model)))
		));

		mLayoutManager = new GridLayoutManager(getContext(), MAX_SPAN_COUNT);
		mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(final int position) {
				final Type type = mRecyclerViewAdapter.getType(position);
				if (type.equals(ForkModel.class) || type.equals(StargazerModel.class)) {
					return 1;
				}
				return 3;
			}
		});

		mRecyclerView = inflate.findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mRecyclerViewAdapter);
		mRecyclerView.addItemDecoration(new MyItemDecoration());
		mRecyclerView.addOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(final int page, final int totalItemsCount) {
				mGithubPresenter.onLoadMore();
			}
		});
		return inflate;
	}

	@Override
	public void onViewStateRestored(@Nullable final Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		mSavedInstanceState = savedInstanceState;
	}

	@Override
	public void onResume() {
		super.onResume();
		mRecyclerViewAdapter.onRestoreInstanceState(mSavedInstanceState);
		mSavedInstanceState = null;
	}

	@Override
	public void onSaveInstanceState(@NonNull final Bundle outState) {
		super.onSaveInstanceState(outState);
		mRecyclerViewAdapter.onSaveInstanceState(outState);
	}

	@Override
	public void onStart() {
		super.onStart();
		mGithubPresenter.viewShown();
	}

	@Override
	public void onStop() {
		super.onStop();
		mGithubPresenter.viewHidden();
	}


	@NonNull
	private ViewRenderer createForkRenderer() {
		return new ForkViewRenderer(new Listener());
	}

	@NonNull
	private ViewRenderer createStargazerRenderer(final int layout) {
		return new StargazerViewRenderer(layout, new Listener());
	}

	@NonNull
	private ViewRenderer createCategoryRenderer() {
		return new CategoryViewRenderer(new Listener());
	}

	@NonNull
	private CompositeViewRenderer createListRenderer() {
		return new RecyclerViewRenderer();
	}

	@NonNull
	private ViewRenderer createUserRenderer() {
		/* vivchar: ideally we should use other model */
		return new UserViewRenderer();
	}

	@NonNull
	private final GithubPresenter.View mMainPresenterView = new GithubPresenter.View() {

		@Override
		public void updateList(@NonNull final List<ViewModel> list) {
			mRecyclerViewAdapter.setItems(list);
		}

		@Override
		public void showProgressView() {
			mSwipeToRefresh.setRefreshing(true);
		}

		@Override
		public void hideProgressView() {
			mSwipeToRefresh.setRefreshing(false);
		}

		@Override
		public void showMessageView(@NonNull final String message, @NonNull final String url) {
			final View view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
			Snackbar.make(view, message, Snackbar.LENGTH_LONG)
					.setAction(R.string.view, v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))))
					.show();
		}

		@Override
		public void showMessageView(@NonNull final String message) {
			final View view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
			Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
		}

		@Override
		public void showSelectedUsers(@NonNull final ArrayList<ViewModel> list) {
			final RendererRecyclerViewAdapter adapter = new MyAdapter();
			adapter.registerRenderer(createUserRenderer());

			final LayoutInflater inflater = LayoutInflater.from(getContext());
			final RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.selected_items_dialog, null);
			recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
			recyclerView.setAdapter(adapter);
			adapter.setItems(list);

			final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setView(recyclerView);
			builder.setTitle(R.string.selected_users);
			builder.setPositiveButton(R.string.ok, null);

			builder.show();
		}

		@Override
		public void clearSelections() {
			mRecyclerViewAdapter.clearViewStates();
			mRecyclerViewAdapter.notifyDataSetChanged();
		}

		@Override
		public void showLoadMoreView() {
			mRecyclerViewAdapter.showLoadMore();
		}

		@Override
		public Context getContext() {
			return GithubFragment.this.getContext();
		}
	};

	private class Listener implements StargazerViewRenderer.Listener, CategoryViewRenderer.Listener, ForkViewRenderer.Listener {

		@Override
		public void onStargazerItemClicked(@NonNull final StargazerModel model, final boolean isChecked) {
			mGithubPresenter.onStargazerClicked(model, isChecked);
		}

		@Override
		public void onCategoryClicked(@NonNull final CategoryModel model) {
			mGithubPresenter.onCategoryClicked(model);
		}

		@Override
		public void onForkItemClicked(@NonNull final ForkModel model) {
			mGithubPresenter.onForkClicked(model);
		}
	}
}
