package com.github.vivchar.immutableadapter.items.composite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.vivchar.immutableadapter.R;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;


/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class CompositeContentViewHolder
		extends CompositeViewHolder
{
	public CompositeContentViewHolder(@NonNull final View view) {
		super(view);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.composite_recycler_view);
	}
}
