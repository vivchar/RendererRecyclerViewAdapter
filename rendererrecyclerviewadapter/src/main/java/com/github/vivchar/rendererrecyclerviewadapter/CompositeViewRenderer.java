package com.github.vivchar.rendererrecyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/25/17.
 */

public abstract class CompositeViewRenderer <M extends CompositeViewModel, VH extends CompositeViewHolder> extends ViewRenderer<M, VH> {

	@NonNull
	private final ArrayList<ViewRenderer> mRenderers = new ArrayList<>();

	public CompositeViewRenderer(@NonNull final Class<M> type, @NonNull final Context context) {
		super(type, context);
	}

	public CompositeViewRenderer(@NonNull final Class<M> type, @NonNull final Context context, @NonNull final ViewRenderer... renderers) {
		super(type, context);
		Collections.addAll(mRenderers, renderers);
	}

	@Override
	public void bindView(@NonNull final M model, @NonNull final VH holder) {
		holder.adapter.setItems(model.getItems());
		holder.adapter.notifyDataSetChanged();
	}

	@NonNull
	@Override
	public VH createViewHolder(@Nullable final ViewGroup parent) {

		final RendererRecyclerViewAdapter adapter = createAdapter();
		for (final ViewRenderer renderer : mRenderers) {
			adapter.registerRenderer(renderer);
		}

		final VH viewHolder = createCompositeViewHolder(parent);
		viewHolder.adapter = adapter;

		if (viewHolder.recyclerView != null) {
			viewHolder.recyclerView.setLayoutManager(createLayoutManager());
			viewHolder.recyclerView.setAdapter(adapter);
			for (final RecyclerView.ItemDecoration itemDecoration : createItemDecorations()) {
				viewHolder.recyclerView.addItemDecoration(itemDecoration);
			}
		}

		return viewHolder;
	}

	@NonNull
	public CompositeViewRenderer registerRenderer(@NonNull final ViewRenderer renderer) {
		mRenderers.add(renderer);
		return this;
	}

	@Nullable
	@Override
	public ViewState createViewState(@NonNull final ViewModel model, @NonNull final VH holder) {
		return new CompositeViewState(holder);
	}

	@NonNull
	protected abstract VH createCompositeViewHolder(@Nullable ViewGroup parent);

	@NonNull
	protected RecyclerView.LayoutManager createLayoutManager() {
		return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
	}

	@NonNull
	protected RendererRecyclerViewAdapter createAdapter() {
		return new RendererRecyclerViewAdapter();
	}

	@NonNull
	protected List<? extends RecyclerView.ItemDecoration> createItemDecorations() {
		return new ArrayList<>();
	}
}
