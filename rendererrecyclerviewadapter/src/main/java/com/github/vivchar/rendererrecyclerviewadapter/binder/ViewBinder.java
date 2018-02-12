package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Universal ViewRenderer without ViewHolder
 */
public class ViewBinder <M extends ViewModel> extends ViewRenderer<M, ViewHolder> {

	@LayoutRes
	private final int mLayoutID;
	@NonNull
	private final Binder mBinder;
	@Nullable
	private ViewStateProvider<M, ViewHolder> mViewStateProvider = null;

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Context context,
	                  @NonNull final Binder<M> binder) {
		super(type, context);
		mLayoutID = layoutID;
		mBinder = binder;
	}

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Context context,
	                  @NonNull final Binder<M> binder,
	                  @Nullable final ViewStateProvider<M, ViewHolder> viewStateProvider) {
		super(type, context);
		mLayoutID = layoutID;
		mBinder = binder;
		mViewStateProvider = viewStateProvider;
	}

	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Binder<M> binder) {
		super(type);
		mLayoutID = layoutID;
		mBinder = binder;
	}

	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Binder<M> binder,
	                  @Nullable final ViewStateProvider<M, ViewHolder> viewStateProvider) {
		super(type);
		mLayoutID = layoutID;
		mBinder = binder;
		mViewStateProvider = viewStateProvider;
	}

	@Override
	public void bindView(@NonNull final M model, @NonNull final ViewHolder finder) {
		mBinder.bindView(model, finder.getViewFinder(), new ArrayList<>());
	}

	@Override
	public void rebindView(@NonNull final M model, @NonNull final ViewHolder finder, @NonNull final List<Object> payloads) {
		mBinder.bindView(model, finder.getViewFinder(), payloads);
	}

	@Nullable
	@Override
	public ViewState createViewState(@NonNull final ViewHolder holder) {
		return mViewStateProvider != null ? mViewStateProvider.createViewState(holder) : super.createViewState(holder);
	}

	@Override
	public int createViewStateID(@NonNull final M model) {
		return mViewStateProvider != null ? mViewStateProvider.createViewStateID(model) : super.createViewStateID(model);
	}

	@NonNull
	@Override
	public ViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new ViewHolder(inflate(mLayoutID, parent));
	}

	public interface Binder <M> {

		void bindView(@NonNull M model, @NonNull ViewFinder finder, @NonNull final List<Object> payloads);
	}
}