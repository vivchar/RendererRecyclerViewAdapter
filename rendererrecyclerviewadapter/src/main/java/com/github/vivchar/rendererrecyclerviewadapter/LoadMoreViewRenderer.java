package com.github.vivchar.rendererrecyclerviewadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

/**
 * Created by Vivchar Vitaly on 05.11.17.
 */

public class LoadMoreViewRenderer extends ViewRenderer<LoadMoreItemModel, LoadMoreViewHolder> {

	@LayoutRes
	private final int mLayout;

	public LoadMoreViewRenderer(final int layout, @NonNull final Context context) {
		super(LoadMoreItemModel.TYPE, context);
		mLayout = layout;
	}

	@Override
	public void bindView(@NonNull final LoadMoreItemModel model, @NonNull final LoadMoreViewHolder holder) {}

	@NonNull
	@Override
	public LoadMoreViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new LoadMoreViewHolder(inflate(mLayout, parent));
	}
}
