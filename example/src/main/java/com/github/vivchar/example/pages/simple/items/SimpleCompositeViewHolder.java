package com.github.vivchar.example.pages.simple.items;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.vivchar.example.R;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
public class SimpleCompositeViewHolder extends CompositeViewHolder {

	public SimpleCompositeViewHolder(final View itemView) {
		super(itemView);
		recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
	}
}