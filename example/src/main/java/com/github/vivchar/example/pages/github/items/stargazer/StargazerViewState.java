package com.github.vivchar.example.pages.github.items.stargazer;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

import java.io.Serializable;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class StargazerViewState implements ViewState<StargazerViewHolder>, Serializable {

	private static final String TAG = StargazerViewState.class.getSimpleName();
	private final int mVisibility;

	public StargazerViewState(@NonNull final StargazerViewHolder holder) {
		mVisibility = holder.check.getVisibility();
	}

	@Override
	public void restore(@NonNull final StargazerViewHolder holder) {
		holder.check.setVisibility(mVisibility);
	}
}