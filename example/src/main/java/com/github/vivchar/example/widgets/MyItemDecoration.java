package com.github.vivchar.example.widgets;

import android.content.res.Resources;
import android.graphics.Rect;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.github.vivchar.example.pages.github.items.CategoryModel;
import com.github.vivchar.example.pages.github.items.RecyclerViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

/**
 * Created by Vivchar Vitaly on 7/24/17.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

	@Override
	public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {
		final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

		final int itemPosition = parent.getChildAdapterPosition(view);
		if (itemPosition != RecyclerView.NO_POSITION) {
			if (layoutManager instanceof GridLayoutManager) {
				final RendererRecyclerViewAdapter adapter = (RendererRecyclerViewAdapter) parent.getAdapter();
				final ViewModel item = adapter.getItem(itemPosition);
				if (item instanceof RecyclerViewModel) {
					outRect.left = dpToPixels(-10);
					outRect.right = dpToPixels(-10);
					outRect.top = dpToPixels(5);
					outRect.bottom = dpToPixels(5);
				} else if (item instanceof CategoryModel) {
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
