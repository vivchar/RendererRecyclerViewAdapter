package com.github.vivchar.example;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.vivchar.example.pages.github.items.category.CategoryModel;
import com.github.vivchar.example.pages.github.items.list.RecyclerViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

/**
 * Created by Vivchar Vitaly on 7/24/17.
 */

public class MyItemDecoration
		extends RecyclerView.ItemDecoration
{
	@Override
	public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {
		final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

		final int itemPosition = parent.getChildAdapterPosition(view);
		if (itemPosition != RecyclerView.NO_POSITION) {
			if (layoutManager instanceof GridLayoutManager) {
				final RendererRecyclerViewAdapter adapter = (RendererRecyclerViewAdapter) parent.getAdapter();
				final ItemModel item = adapter.getItem(itemPosition);
				if (item.getType() == RecyclerViewModel.TYPE) {
					outRect.left = dpToPixels(-10);
					outRect.right = dpToPixels(-10);
					outRect.top = dpToPixels(5);
					outRect.bottom = dpToPixels(5);
				} else if (item.getType() == CategoryModel.TYPE) {
					outRect.left = dpToPixels(5);
					outRect.right = dpToPixels(5);
					outRect.top = dpToPixels(10);
					outRect.bottom = dpToPixels(2);
				} else {
					outRect.left = dpToPixels(5);
					outRect.right = dpToPixels(5);
					outRect.top = dpToPixels(5);
					outRect.bottom = dpToPixels(5);
				}
			} else {
				throw new UnsupportedClassVersionError("Unsupported LayoutManager");
			}
		}
	}

	private static int dpToPixels(final float dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}
}
