package com.github.vivchar.example.pages.github.items.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.github.vivchar.example.BetweenSpacesItemDecoration;
import com.github.vivchar.example.R;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class RecyclerViewRenderer extends CompositeViewRenderer<RecyclerViewModel, RecyclerViewHolder> {

	public RecyclerViewRenderer(@NonNull final Context context) {
		super(RecyclerViewModel.TYPE, context);
	}

	@Override
	public void rebindView(final RecyclerViewModel model, final RecyclerViewHolder holder, @NonNull final List payloads) {
		holder.adapter.setItems(model.getItems(), new DefaultDiffCallback());
	}

	@NonNull
	@Override
	public RecyclerViewHolder createCompositeViewHolder(@Nullable final ViewGroup parent) {
		return new RecyclerViewHolder(inflate(R.layout.item_recycler_view, parent));
	}

	@NonNull
	@Override
	protected List<? extends RecyclerView.ItemDecoration> createItemDecorations() {
		return Collections.singletonList(new BetweenSpacesItemDecoration(0, 10));
	}
}