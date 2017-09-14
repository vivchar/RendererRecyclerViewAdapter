package com.github.vivchar.immutableadapter.items.composite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.vivchar.immutableadapter.BetweenSpacesItemDecoration;
import com.github.vivchar.immutableadapter.R;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class CompositeContentViewRenderer
		extends CompositeViewRenderer<CompositeContentModel, CompositeContentViewHolder>
{

	public CompositeContentViewRenderer(final int viewType, @NonNull final Context context) {
		super(viewType, context);
	}

	@NonNull
	@Override
	public CompositeContentViewHolder createCompositeViewHolder(@Nullable final ViewGroup parent) {
		return new CompositeContentViewHolder(inflate(R.layout.item_composite, parent));
	}

	@NonNull
	@Override
	protected List<? extends RecyclerView.ItemDecoration> createItemDecorations() {
		return Collections.singletonList(new BetweenSpacesItemDecoration(0, 10));
	}

	@NonNull
	@Override
	protected RendererRecyclerViewAdapter createAdapter() {
		return new ExampleAdapter();
	}

	@NonNull
	@Override
	protected RecyclerView.LayoutManager createLayoutManager() {
		return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
	}

	private class ExampleAdapter extends RendererRecyclerViewAdapter {}
}
