package com.github.vivchar.example.pages.github.items.list;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewState;

import java.io.Serializable;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class RecyclerViewState extends CompositeViewState<CompositeViewHolder> implements Serializable {

	public RecyclerViewState(@NonNull final CompositeViewHolder holder) {
		super(holder);
	}

	@Override
	public void restore(@NonNull final CompositeViewHolder holder) {
		super.restore(holder);
	}
}
