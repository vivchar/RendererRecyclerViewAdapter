package com.github.vivchar.immutableadapter;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vivchar Vitaly on 3/6/17.
 */

public
class EqualSpacesItemDecoration
		extends RecyclerView.ItemDecoration
{
	private final int mSpace;

	public
	EqualSpacesItemDecoration(final int space) {
		mSpace = space;
	}

	@Override
	public
	void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {

		final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

		if (layoutManager instanceof GridLayoutManager) {
			outRect.left = (int) Math.floor(mSpace / 2.0f);
			outRect.right = (int) Math.floor(mSpace / 2.0f);
			outRect.bottom = (int) Math.floor(mSpace / 2.0f);
			outRect.top = (int) Math.floor(mSpace / 2.0f);
		} else {
			throw new UnsupportedClassVersionError("Unsupported LayoutManager");
		}
	}
}