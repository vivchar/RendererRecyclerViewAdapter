package com.github.vivchar.example.pages.github.items.stargazer;

import androidx.annotation.NonNull;

import com.github.vivchar.example.R;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

import java.io.Serializable;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class StargazerViewState implements ViewState<ViewHolder>, Serializable {

	private int mVisibility;

	public StargazerViewState(@NonNull final ViewHolder holder) {
		holder.getViewFinder().find(R.id.check, view -> mVisibility = view.getVisibility());
	}

	@Override
	public void restore(@NonNull final ViewHolder holder) {
		holder.getViewFinder().setVisibility(R.id.check, mVisibility);
	}
}