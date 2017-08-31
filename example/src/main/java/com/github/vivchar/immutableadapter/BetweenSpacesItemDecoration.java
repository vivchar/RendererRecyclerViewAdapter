package com.github.vivchar.immutableadapter;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by Vivchar Vitaly on 8/25/17.
 */

public class BetweenSpacesItemDecoration
		extends RecyclerView.ItemDecoration
{

	private static final int HORIZONTAL = 0;
	private static final int VERTICAL = 1;
	private static final int GRID = 2;

	private final int mVerticalSpace;
	private final int mHorizontalSpace;

	public BetweenSpacesItemDecoration(final int verticalSpace, final int horizontalSpace) {
		mVerticalSpace = dpToPixels(verticalSpace);
		mHorizontalSpace = dpToPixels(horizontalSpace);
	}

	@Override
	public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {
		final int position = parent.getChildViewHolder(view).getAdapterPosition();
		final int itemCount = state.getItemCount();
		final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
		setSpacingForDirection(outRect, layoutManager, position, itemCount);
	}

	/* https://gist.github.com/alexfu/f7b8278009f3119f523a */
	private void setSpacingForDirection(@NonNull final Rect outRect,
	                                    @NonNull final RecyclerView.LayoutManager layoutManager,
	                                    final int position,
	                                    final int itemCount) {
		/* Resolve display mode automatically */
		final int displayMode = resolveDisplayMode(layoutManager);

		switch (displayMode) {
			case HORIZONTAL:
				outRect.left = 0;
				outRect.right = position == itemCount - 1 ? 0 : mHorizontalSpace;
				outRect.top = 0;
				outRect.bottom = 0;
				break;
			case VERTICAL:
				outRect.left = 0;
				outRect.right = 0;
				outRect.top = 0;
				outRect.bottom = position == itemCount - 1 ? 0 : mVerticalSpace;
				break;
			case GRID:
				final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
				final int cols = gridLayoutManager.getSpanCount();
				final int rows = itemCount / cols;

				outRect.left = position % cols == 0 ? 0 : mHorizontalSpace / 2;
				outRect.right = (position + 1) % cols == 0 ? 0 : mHorizontalSpace / 2;
				outRect.top = position < cols ? 0 : mVerticalSpace / 2;
				outRect.bottom = position / cols == rows ? 0 : mVerticalSpace / 2;
				break;
		}
	}

	private int resolveDisplayMode(final RecyclerView.LayoutManager layoutManager) {
		if (layoutManager instanceof GridLayoutManager) {
			return GRID;
		}
		if (layoutManager.canScrollHorizontally()) {
			return HORIZONTAL;
		}
		return VERTICAL;
	}

	public static int dpToPixels(final float dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}
}
