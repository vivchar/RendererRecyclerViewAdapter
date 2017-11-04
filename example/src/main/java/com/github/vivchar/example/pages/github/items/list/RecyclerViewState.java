package com.github.vivchar.example.pages.github.items.list;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewState;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class RecyclerViewState extends CompositeViewState<RecyclerViewHolder> {

	@NonNull
	private final SparseArray<ViewState> mViewStates;

	public RecyclerViewState(@NonNull final CompositeViewHolder holder) {
		super(holder);
		mViewStates = holder.adapter.getViewStates();
	}

	@Override
	public void restore(@NonNull final ItemModel model, @NonNull final RecyclerViewHolder holder) {
		super.restore(model, holder);
		holder.adapter.setViewStates(mViewStates);
	}
}
