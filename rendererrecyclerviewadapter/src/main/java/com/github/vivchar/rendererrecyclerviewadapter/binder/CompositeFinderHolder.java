package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;

/**
 * Created by Vivchar Vitaly on 29.12.17.
 */

public class CompositeFinderHolder extends CompositeViewHolder implements ViewFinder {

	@NonNull
	private final ViewFinder mViewFinder;

	public CompositeFinderHolder(@IdRes final int recyclerViewID, @NonNull final View view) {
		super(view);
		mViewFinder = new ViewFinderImpl(view);
		recyclerView = find(recyclerViewID);
	}

	@NonNull
	@Override
	public <V extends View> ViewFinder find(final int ID, @NonNull final ViewProvider<V> viewProvider) {
		return mViewFinder.find(ID, viewProvider);
	}

	@NonNull
	@Override
	public <V extends View> V find(final int ID) {
		return mViewFinder.find(ID);
	}

	@NonNull
	@Override
	public <V extends View> ViewFinder getRootView(@NonNull final ViewProvider<V> viewProvider) {
		mViewFinder.getRootView(viewProvider);
		return this;
	}

	@NonNull
	@Override
	public <V extends View> V getRootView() {
		return mViewFinder.getRootView();
	}

	@NonNull
	@Override
	public ViewFinder setOnClickListener(final int ID, @NonNull final View.OnClickListener onClickListener) {
		return mViewFinder.setOnClickListener(ID, onClickListener);
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