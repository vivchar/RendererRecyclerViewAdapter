package com.github.vivchar.rendererrecyclerviewadapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vivchar Vitaly on 8/25/17.
 * <p>
 * Use {@link com.github.vivchar.rendererrecyclerviewadapter.binder.CompositeFinderHolder}
 */
@Deprecated
public abstract class CompositeViewHolder extends ViewHolder {

	public RecyclerView recyclerView;
	public RendererRecyclerViewAdapter adapter;

	public CompositeViewHolder(@NonNull final View itemView) {
		super(itemView);
	}

	public RecyclerView getRecyclerView() {
		return recyclerView;
	}

	@NonNull
	public RendererRecyclerViewAdapter getAdapter() {
		return adapter;
	}

	void setAdapter(@NonNull final RendererRecyclerViewAdapter adapter) {
		this.adapter = adapter;
	}
}