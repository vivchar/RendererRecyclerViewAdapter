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
 * <p>
 * Use {@link com.github.vivchar.rendererrecyclerviewadapter.binder.CompositeViewBinder}
 */
@Deprecated
public abstract class CompositeViewRenderer <M extends CompositeViewModel, VH extends CompositeViewHolder> extends ViewRenderer<M, VH> {

	@NonNull
	protected final ArrayList<ViewRenderer> mRenderers = new ArrayList<>();

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public CompositeViewRenderer(@NonNull final Class<M> type, @NonNull final Context context) {
		super(type, context);
	}

	/**
	 * Please use a constructor without Context
	 */
	@Deprecated
	public CompositeViewRenderer(@NonNull final Class<M> type, @NonNull final Context context, @NonNull final ViewRenderer... renderers) {
		super(type, context);
		Collections.addAll(mRenderers, renderers);
	}

	public CompositeViewRenderer(@NonNull final Class<M> type) {
		super(type);
	}

	public CompositeViewRenderer(@NonNull final Class<M> type, @NonNull final ViewRenderer... renderers) {
		super(type);
		Collections.addAll(mRenderers, renderers);
	}

	@Override
	public void bindView(@NonNull final M model, @NonNull final VH holder) {
		holder.getAdapter().setItems(model.getItems());
		holder.getAdapter().notifyDataSetChanged();
	}

	@NonNull
	@Override
	public VH createViewHolder(final ViewGroup parent) {
		final RendererRecyclerViewAdapter adapter = createAdapter();
		for (final ViewRenderer renderer : mRenderers) {
			adapter.registerRenderer(renderer);
		}

		final VH viewHolder = createCompositeViewHolder(parent);
		viewHolder.setAdapter(adapter);

		if (viewHolder.getRecyclerView() != null) {
			viewHolder.getRecyclerView().setLayoutManager(createLayoutManager());
			viewHolder.getRecyclerView().setAdapter(adapter);
			for (final RecyclerView.ItemDecoration itemDecoration : createItemDecorations()) {
				viewHolder.getRecyclerView().addItemDecoration(itemDecoration);
			}
		}

		return viewHolder;
	}

	@NonNull
	public CompositeViewRenderer registerRenderer(@NonNull final ViewRenderer renderer) {
		mRenderers.add(renderer);
		return this;
	}

	/**
	 * Use {@link CompositeViewState} if you want to save scroll state of Nested RecyclerView
	 */
	@Nullable
	@Override
	public ViewState createViewState(@NonNull final VH holder) {
		return super.createViewState(holder);
	}

	@NonNull
	protected abstract VH createCompositeViewHolder(ViewGroup parent);

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
