package com.github.vivchar.example.pages.github.items.stargazer;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class StargazerViewState implements ViewState<StargazerViewHolder> {

	private static final String TAG = StargazerViewState.class.getSimpleName();
	@NonNull
	private final ViewModel mModel;
	private final int mVisibility;

	public StargazerViewState(@NonNull final ViewModel model, @NonNull final StargazerViewHolder holder) {
		Log.d(TAG, "save: " + model);
		mModel = model;
		mVisibility = holder.check.getVisibility();
	}

	@Override
	public void restore(@NonNull final ViewModel model, @NonNull final StargazerViewHolder holder) {
		Log.d(TAG, "restore: " + model + " equals: " + mModel.equals(model));
		if (mModel.equals(model)) { /* if it is the same item */
			holder.check.setVisibility(mVisibility);
		}
	}
}