package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ViewGroup;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 29.12.17.
 */

public class CompositeViewBinder <M extends CompositeViewModel, VF extends ViewFinder>
		extends CompositeViewRenderer<M, CompositeViewHolder<VF>> {

	@IdRes
	private final int mRecyclerViewID;
	@LayoutRes
	private final int mLayoutID;
	@NonNull
	private final ViewBinder.Binder<M, VF> mBinder;
	@NonNull
	private final List<RecyclerView.ItemDecoration> mDecorations = new ArrayList<>();
	@Nullable
	private CompositeViewStateProvider<M, CompositeViewHolder<VF>> mViewStateProvider = null;

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final Context context,
	                           @NonNull final ViewBinder.Binder<M, VF> binder,
	                           @NonNull final List<? extends RecyclerView.ItemDecoration> decorations,
	                           @Nullable final CompositeViewStateProvider<M, CompositeViewHolder<VF>> viewStateProvider) {
		super(type, context);
		mLayoutID = layoutID;
		mRecyclerViewID = recyclerViewID;
		mBinder = binder;
		mViewStateProvider = viewStateProvider;
		mDecorations.addAll(decorations);
	}

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final Context context,
	                           @NonNull final ViewBinder.Binder<M, VF> binder) {
		super(type, context);
		mLayoutID = layoutID;
		mRecyclerViewID = recyclerViewID;
		mBinder = binder;
	}

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final Context context,
	                           @NonNull final ViewBinder.Binder<M, VF> binder,
	                           @NonNull final ViewRenderer... renderers) {
		super(type, context, renderers);
		mLayoutID = layoutID;
		mRecyclerViewID = recyclerViewID;
		mBinder = binder;
	}

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final Context context) {
		this(layoutID, recyclerViewID, type, context, new ViewBinder.Binder<M, VF>() {
			@Override
			public void bindView(@NonNull final M model, @NonNull final VF finder, @NonNull final List<Object> payloads) {}
		});
	}

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final Context context,
	                           @NonNull final List<? extends RecyclerView.ItemDecoration> decorations) {
		this(layoutID, recyclerViewID, type, context);
		mDecorations.addAll(decorations);
	}

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final Context context,
	                           @Nullable final CompositeViewStateProvider<M, CompositeViewHolder<VF>> viewStateProvider) {
		this(layoutID, recyclerViewID, type, context);
		mViewStateProvider = viewStateProvider;
	}

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final Context context,
	                           @NonNull final List<? extends RecyclerView.ItemDecoration> decorations,
	                           @Nullable final CompositeViewStateProvider<M, CompositeViewHolder<VF>> viewStateProvider) {
		this(layoutID, recyclerViewID, type, context);
		mViewStateProvider = viewStateProvider;
		mDecorations.addAll(decorations);
	}


	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final ViewBinder.Binder<M, VF> binder,
	                           @NonNull final List<? extends RecyclerView.ItemDecoration> decorations,
	                           @Nullable final CompositeViewStateProvider<M, CompositeViewHolder<VF>> viewStateProvider) {
		super(type);
		mLayoutID = layoutID;
		mRecyclerViewID = recyclerViewID;
		mBinder = binder;
		mViewStateProvider = viewStateProvider;
		mDecorations.addAll(decorations);
	}

	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final ViewBinder.Binder<M, VF> binder) {
		super(type);
		mLayoutID = layoutID;
		mRecyclerViewID = recyclerViewID;
		mBinder = binder;
	}

	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final ViewBinder.Binder<M, VF> binder,
	                           @NonNull final ViewRenderer... renderers) {
		super(type, renderers);
		mLayoutID = layoutID;
		mRecyclerViewID = recyclerViewID;
		mBinder = binder;
	}

	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type) {
		this(layoutID, recyclerViewID, type, new ViewBinder.Binder<M, VF>() {
			@Override
			public void bindView(@NonNull final M model, @NonNull final VF finder, @NonNull final List<Object> payloads) {}
		});
	}

	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final List<? extends RecyclerView.ItemDecoration> decorations) {
		this(layoutID, recyclerViewID, type);
		mDecorations.addAll(decorations);
	}

	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @Nullable final CompositeViewStateProvider<M, CompositeViewHolder<VF>> viewStateProvider) {
		this(layoutID, recyclerViewID, type);
		mViewStateProvider = viewStateProvider;
	}

	public CompositeViewBinder(final int layoutID,
	                           @IdRes final int recyclerViewID,
	                           @NonNull final Class<M> type,
	                           @NonNull final List<? extends RecyclerView.ItemDecoration> decorations,
	                           @Nullable final CompositeViewStateProvider<M, CompositeViewHolder<VF>> viewStateProvider) {
		this(layoutID, recyclerViewID, type);
		mViewStateProvider = viewStateProvider;
		mDecorations.addAll(decorations);
	}

	@Override
	public void bindView(@NonNull final M model, @NonNull final CompositeViewHolder<VF> holder) {
		try {
			super.bindView(model, holder);
			mBinder.bindView(model, holder.getViewFinder(), new ArrayList<>());
		} catch (ClassCastException e) {
			throw new WrongViewFinderException();
		}
	}

	@Override
	public void rebindView(@NonNull final M model, @NonNull final CompositeViewHolder<VF> holder, @NonNull final List<Object> payloads) {
		try {
			super.rebindView(model, holder, payloads);
			mBinder.bindView(model, holder.getViewFinder(), payloads);
		} catch (ClassCastException e) {
			throw new WrongViewFinderException();
		}
	}

	@NonNull
	@Override
	protected List<? extends RecyclerView.ItemDecoration> createItemDecorations() {
		return mDecorations;
	}

	@Nullable
	@Override
	public ViewState createViewState(@NonNull final CompositeViewHolder<VF> holder) {
		return mViewStateProvider != null ? mViewStateProvider.createViewState(holder) : super.createViewState(holder);
	}

	@Override
	public int createViewStateID(@NonNull final M model) {
		return mViewStateProvider != null ? mViewStateProvider.createViewStateID(model) : super.createViewStateID(model);
	}

	@NonNull
	@Override
	protected CompositeViewHolder<VF> createCompositeViewHolder(@Nullable final ViewGroup parent) {
		return new CompositeViewHolder<>(mRecyclerViewID, inflate(mLayoutID, parent));
	}
}