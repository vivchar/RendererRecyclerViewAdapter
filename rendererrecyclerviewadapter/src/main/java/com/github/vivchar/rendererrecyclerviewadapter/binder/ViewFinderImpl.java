package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Vivchar Vitaly on 29.12.17.
 */

class ViewFinderImpl implements ViewFinder {

	@NonNull
	private final SparseArray<View> mCachedViews = new SparseArray<>();
	@NonNull
	private final View mItemView;

	public ViewFinderImpl(@NonNull final View itemView) {
		mItemView = itemView;
	}

	@NonNull
	@Override
	public <V extends View> ViewFinder find(final int ID, @NonNull final ViewProvider<V> viewProvider) {
		viewProvider.provide((V) findViewById(ID));
		return this;
	}

	@NonNull
	@Override
	public <V extends View> V find(final int ID) {
		return (V) findViewById(ID);
	}

	@NonNull
	@Override
	public <V extends View> ViewFinder getRootView(@NonNull final ViewProvider<V> viewProvider) {
		viewProvider.provide((V) mItemView);
		return this;
	}

	@NonNull
	@Override
	public <V extends View> V getRootView() {
		return (V) mItemView;
	}

	@NonNull
	@Override
	public ViewFinder setOnClickListener(final int ID, @NonNull final View.OnClickListener onClickListener) {
		findViewById(ID).setOnClickListener(onClickListener);
		return this;
	}

	@NonNull
	@Override
	public ViewFinder setText(final int ID, final CharSequence text) {
		((TextView)find(ID)).setText(text);
		return this;
	}

	@NonNull
	@Override
	public ViewFinder setText(final int ID, @StringRes final int textID) {
		((TextView)find(ID)).setText(textID);
		return this;
	}

	@NonNull
	private View findViewById(@IdRes final int ID) {
		final View cachedView = mCachedViews.get(ID);
		if (cachedView != null) {
			return cachedView;
		}
		final View view = mItemView.findViewById(ID);
		mCachedViews.put(ID, view);
		return view;
	}
}
