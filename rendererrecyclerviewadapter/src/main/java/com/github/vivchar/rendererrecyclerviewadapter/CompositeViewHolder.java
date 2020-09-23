package com.github.vivchar.rendererrecyclerviewadapter;


import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vivchar Vitaly on 8/25/17.
 */
public class CompositeViewHolder<VF extends ViewFinder> extends ViewHolder<VF> {

	protected RecyclerView recyclerView;
	protected RendererRecyclerViewAdapter adapter;

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