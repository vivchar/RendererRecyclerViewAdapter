package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.ViewGroup;

/**
 * Created by Vivchar Vitaly on 05.11.17.
 * <p>
 * Use {@link com.github.vivchar.rendererrecyclerviewadapter.binder.LoadMoreViewBinder}
 */
@Deprecated
public class LoadMoreViewRenderer extends ViewRenderer<LoadMoreViewModel, LoadMoreViewHolder> {

	@LayoutRes
	protected final int mLayoutID;

	public LoadMoreViewRenderer(@LayoutRes final int layoutID) {
		super(LoadMoreViewModel.class);
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