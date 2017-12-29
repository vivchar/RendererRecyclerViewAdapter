package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;

/**
 * More detail you can get there: https://github.com/vivchar/ViewFinder
 */
public class FinderHolder extends ViewHolder implements ViewFinder {

	@NonNull
	private final ViewFinder mViewFinder;

	public FinderHolder(@NonNull final View view) {
		super(view);
		mViewFinder = new ViewFinderImpl(view);
	}

	@NonNull
	public <V extends View> ViewFinder find(@IdRes final int ID, @NonNull final ViewProvider<V> viewProvider) {
		mViewFinder.find(ID, viewProvider);
		return this;
	}

	@NonNull
	public <V extends View> V find(@IdRes final int ID) {
		return mViewFinder.find(ID);
	}

	@NonNull
	public <V extends View> ViewFinder getRootView(@NonNull final ViewProvider<V> viewProvider) {
		mViewFinder.getRootView(viewProvider);
		return this;
	}

	@NonNull
	public <V extends View> V getRootView() {
		return mViewFinder.getRootView();
	}

	@NonNull
	public ViewFinder setOnClickListener(@IdRes final int ID, @NonNull final View.OnClickListener onClickListener) {
		mViewFinder.setOnClickListener(ID, onClickListener);
		return this;
	}

	@NonNull
	@Override
	public ViewFinder setText(@IdRes final int ID, final CharSequence text) {
		return mViewFinder.setText(ID, text);
	}

	@NonNull
	@Override
	public ViewFinder setText(@IdRes final int ID, @StringRes final int textID) {
		return mViewFinder.setText(ID, textID);
	}
}