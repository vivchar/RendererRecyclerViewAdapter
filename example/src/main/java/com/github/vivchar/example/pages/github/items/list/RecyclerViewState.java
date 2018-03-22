package com.github.vivchar.example.pages.github.items.list;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewState;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class RecyclerViewState extends CompositeViewState<RecyclerViewHolder> implements Serializable {

	public RecyclerViewState(@NonNull final CompositeViewHolder holder) {
		super(holder);
	}

	@Override
	public void restore(@NonNull final RecyclerViewHolder holder) {
		super.restore(holder);
	}
}
