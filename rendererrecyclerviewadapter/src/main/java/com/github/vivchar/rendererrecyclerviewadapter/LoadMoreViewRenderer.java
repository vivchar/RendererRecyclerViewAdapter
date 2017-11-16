package com.github.vivchar.rendererrecyclerviewadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

/**
 * Created by Vivchar Vitaly on 05.11.17.
 */

public class LoadMoreViewRenderer extends ViewRenderer<LoadMoreViewModel, LoadMoreViewHolder> {

	@LayoutRes
	protected final int mLayoutID;

	public LoadMoreViewRenderer(@LayoutRes final int layoutID, @NonNull final Context context) {
		super(LoadMoreViewModel.class, context);
		mLayoutID = layoutID;
	}

	@Override
	public void bindView(@NonNull final LoadMoreViewModel model, @NonNull final LoadMoreViewHolder holder) {}

	@NonNull
	@Override
	public LoadMoreViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new LoadMoreViewHolder(inflate(mLayoutID, parent));
	}
}