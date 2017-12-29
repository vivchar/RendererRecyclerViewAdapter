package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Universal ViewRenderer without ViewHolder
 * <p>
 * More detail you can get there: https://github.com/vivchar/ViewFinder
 */
public class ViewBinder <M extends ViewModel> extends ViewRenderer<M, FinderHolder> {

	@LayoutRes
	private final int mLayoutID;
	@NonNull
	private final Binder mBinder;
	@Nullable
	private ViewStateProvider<M, FinderHolder> mViewStateProvider = null;

	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Context context,
	                  @NonNull final Binder<M> binder) {
		super(type, context);
		mLayoutID = layoutID;
		mBinder = binder;
	}

	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Context context,
	                  @NonNull final Binder<M> binder,
	                  @Nullable final ViewStateProvider<M, FinderHolder> viewStateProvider) {
		super(type, context);
		mLayoutID = layoutID;
		mBinder = binder;
		mViewStateProvider = viewStateProvider;
	}

	@Override
	public void bindView(@NonNull final M model, @NonNull final FinderHolder finder) {
		mBinder.bindView(model, finder, new ArrayList<>());
	}

	@Override
	public void rebindView(@NonNull final M model, @NonNull final FinderHolder finder, @NonNull final List<Object> payloads) {
		mBinder.bindView(model, finder, payloads);
	}

	@Nullable
	@Override
	public ViewState createViewState(@NonNull final FinderHolder holder) {
		return mViewStateProvider != null ? mViewStateProvider.createViewState(holder) : super.createViewState(holder);
	}

	@Override
	public int createViewStateID(@NonNull final M model) {
		return mViewStateProvider != null ? mViewStateProvider.createViewStateID(model) : super.createViewStateID(model);
	}

	@NonNull
	@Override
	public FinderHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new FinderHolder(inflate(mLayoutID, parent));
	}

	public interface Binder <M> {

		void bindView(@NonNull M model, @NonNull ViewFinder finder, @NonNull final List<Object> payloads);
	}
}