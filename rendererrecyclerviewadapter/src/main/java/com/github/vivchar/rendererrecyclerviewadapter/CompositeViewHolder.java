package com.github.vivchar.rendererrecyclerviewadapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vivchar Vitaly on 8/25/17.
 */

public abstract class CompositeViewHolder
		extends RecyclerView.ViewHolder
{
	public RecyclerView recyclerView;
	public RendererRecyclerViewAdapter adapter;

	public CompositeViewHolder(@NonNull final View itemView) {
		super(itemView);
	}
}
