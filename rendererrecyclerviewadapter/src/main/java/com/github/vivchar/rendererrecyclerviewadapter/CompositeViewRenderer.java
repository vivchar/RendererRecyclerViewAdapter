package com.github.vivchar.rendererrecyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Vivchar Vitaly on 8/25/17.
 */

public abstract class CompositeViewRenderer <M extends CompositeItemModel, VH extends CompositeViewHolder>
		extends ViewRenderer<M, VH>
{
	@NonNull
	private final ArrayList<ViewRenderer> mRenderers = new ArrayList<>();
	private RendererRecyclerViewAdapter mAdapter;

	public CompositeViewRenderer(final int viewType, @NonNull final Context context) {
		super(viewType, context);
	}

	public CompositeViewRenderer(final int viewType, @NonNull final Context context, @NonNull final ViewRenderer... renderers) {
		super(viewType, context);
		Collections.addAll(mRenderers, renderers);
	}

	@Override
	public void bindView(@NonNull final M model, @NonNull final VH holder) {
		mAdapter.setItems(model.getItems());
		mAdapter.notifyDataSetChanged();
	}

	@NonNull
	@Override
	public VH createViewHolder(@Nullable final ViewGroup parent) {
		mAdapter = new RendererRecyclerViewAdapter();

		for (final ViewRenderer renderer : mRenderers) {
			mAdapter.registerRenderer(renderer);
		}

		final VH viewHolder = createCompositeViewHolder(parent);
		if (viewHolder.mRecyclerView != null) {
			viewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
			viewHolder.mRecyclerView.setAdapter(mAdapter);
		}

		return viewHolder;
	}

	public abstract VH createCompositeViewHolder(@Nullable ViewGroup parent);

	@NonNull
	public CompositeViewRenderer registerRenderer(@NonNull final ViewRenderer renderer) {
		mRenderers.add(renderer);
		return this;
	}

	protected RendererRecyclerViewAdapter getAdapter() {
		return mAdapter;
	}
}
