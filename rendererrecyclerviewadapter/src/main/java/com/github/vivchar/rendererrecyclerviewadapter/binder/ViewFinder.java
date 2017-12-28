package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;

import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;

/**
 * More detail you can get there: https://github.com/vivchar/ViewFinder
 */
public class ViewFinder extends ViewHolder {

	@NonNull
	private final SparseArray<View> mCachedViews = new SparseArray<>();

	public ViewFinder(@NonNull final View view) {
		super(view);
	}

	@NonNull
	public <T extends View> ViewFinder find(@IdRes final int ID, @NonNull final ViewProvider<T> viewProvider) {
		viewProvider.provide((T) findViewById(ID));
		return this;
	}

	@NonNull
	public <T extends View> T find(@IdRes final int ID) {
		return (T) findViewById(ID);
	}

	@NonNull
	public <T extends View> ViewFinder getRootView(@NonNull final ViewProvider<T> viewProvider) {
		viewProvider.provide((T) itemView);
		return this;
	}

	@NonNull
	public <T extends View> T getRootView() {
		return (T) itemView;
	}

	@NonNull
	public ViewFinder setOnClickListener(@IdRes final int ID, @NonNull final View.OnClickListener onClickListener) {
		findViewById(ID).setOnClickListener(onClickListener);
		return this;
	}

	@NonNull
	private View findViewById(@IdRes final int ID) {
		final View cachedView = mCachedViews.get(ID);
		if (cachedView != null) {
			return cachedView;
		}
		final View view = itemView.findViewById(ID);
		mCachedViews.put(ID, view);
		return view;
	}
}
