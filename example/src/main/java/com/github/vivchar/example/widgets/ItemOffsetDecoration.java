package com.github.vivchar.example.widgets;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vivchar Vitaly on 12/29/17.
 * https://gist.github.com/yqritc/ccca77dc42f2364777e1
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

	private int mItemOffset;

	public ItemOffsetDecoration(int itemOffset) {
		mItemOffset = itemOffset;
	}

	public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
		this(context.getResources().getDimensionPixelSize(itemOffsetId));
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
	}
}