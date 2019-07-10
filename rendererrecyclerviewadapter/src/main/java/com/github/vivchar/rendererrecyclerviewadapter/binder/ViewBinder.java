package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.ViewGroup;

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
@SuppressWarnings("deprecation")
public class ViewBinder <M extends ViewModel, VF extends ViewFinder> extends ViewRenderer<M, ViewHolder<VF>> {

	@LayoutRes
	private final int mLayoutID;
	@NonNull
	private final Binder<M, VF> mBinder;
	@Nullable
	private ViewStateProvider<M, ViewHolder<VF>> mViewStateProvider = null;

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Context context,
	                  @NonNull final Binder<M, VF> binder) {
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
	                  @NonNull final Binder<M, VF> binder,
	                  @Nullable final ViewStateProvider<M, ViewHolder<VF>> viewStateProvider) {
		super(type, context);
		mLayoutID = layoutID;
		mBinder = binder;
		mViewStateProvider = viewStateProvider;
	}

	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Binder<M, VF> binder) {
		super(type);
		mLayoutID = layoutID;
		mBinder = binder;
	}

	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Binder<M, VF> binder,
	                  @Nullable final ViewStateProvider<M, ViewHolder<VF>> viewStateProvider) {
		super(type);
		mLayoutID = layoutID;
		mBinder = binder;
		mViewStateProvider = viewStateProvider;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void bindView(@NonNull final M model, @NonNull final ViewHolder<VF> finder) {
		try {
			mBinder.bindView(model, finder.getViewFinder(), new ArrayList<>());
		} catch (ClassCastException e) {
			throw new WrongViewFinderException();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void rebindView(@NonNull final M model, @NonNull final ViewHolder<VF> finder, @NonNull final List<Object> payloads) {
		try {
			mBinder.bindView(model, finder.getViewFinder(), payloads);
		} catch (ClassCastException e) {
			throw new WrongViewFinderException();
		}
	}

	@Nullable
	@Override
	public ViewState createViewState(@NonNull final ViewHolder<VF> holder) {
		return mViewStateProvider != null ? mViewStateProvider.createViewState(holder) : super.createViewState(holder);
	}

	@Override
	public int createViewStateID(@NonNull final M model) {
		return mViewStateProvider != null ? mViewStateProvider.createViewStateID(model) : super.createViewStateID(model);
	}

	@NonNull
	@Override
	public ViewHolder<VF> createViewHolder(@Nullable final ViewGroup parent) {
		return (ViewHolder<VF>) new ViewHolder(inflate(mLayoutID, parent));
	}

	public interface Binder <M, VF extends ViewFinder> {

		void bindView(@NonNull M model, @NonNull VF finder, @NonNull final List<Object> payloads);
	}
}