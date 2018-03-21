package com.github.vivchar.example.pages.github.items.list;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewState;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

import java.util.HashMap;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class RecyclerViewState extends CompositeViewState<RecyclerViewHolder> {

	@NonNull
	private final HashMap<Integer, ViewState> mViewStates;

	public RecyclerViewState(@NonNull final CompositeViewHolder holder) {
		super(holder);
		mViewStates = holder.getAdapter().getStates();
	}

	@Override
	public void restore(@NonNull final RecyclerViewHolder holder) {
		super.restore(holder);
		holder.getAdapter().setStates(mViewStates);
	}
}
