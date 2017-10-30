package com.github.vivchar.example.pages.github.items.stargazer;

import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class StargazerViewState implements ViewState<StargazerViewHolder> {

	@NonNull
	private final ItemModel mModel;
	private final int mVisibility;

	public StargazerViewState(@NonNull final ItemModel model, @NonNull final StargazerViewHolder holder) {
		mModel = model;
		mVisibility = holder.check.getVisibility();
	}

	@Override
	public void restore(@NonNull final StargazerViewHolder holder) {
		holder.check.setVisibility(mVisibility);
	}
}
