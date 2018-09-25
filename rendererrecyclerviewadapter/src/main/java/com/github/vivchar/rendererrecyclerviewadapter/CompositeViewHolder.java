package com.github.vivchar.rendererrecyclerviewadapter;


import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vivchar Vitaly on 8/25/17.
 */
public class CompositeViewHolder extends ViewHolder {

	public RecyclerView recyclerView;
	public RendererRecyclerViewAdapter adapter;

	public CompositeViewHolder(@NonNull final View itemView) {
		super(itemView);
	}

	public CompositeViewHolder(@IdRes final int recyclerViewID, @NonNull final View view) {
		super(view);
		recyclerView = getViewFinder().find(recyclerViewID);
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