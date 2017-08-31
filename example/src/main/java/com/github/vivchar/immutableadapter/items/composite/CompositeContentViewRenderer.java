package com.github.vivchar.immutableadapter.items.composite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.github.vivchar.immutableadapter.BetweenSpacesItemDecoration;
import com.github.vivchar.immutableadapter.R;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;

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
		final CompositeContentViewHolder viewHolder = new CompositeContentViewHolder(inflate(R.layout.item_composite, parent));
		viewHolder.mRecyclerView.addItemDecoration(new BetweenSpacesItemDecoration(0, 10));
		return viewHolder;
	}
}
