package com.github.vivchar.example.widgets;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.github.vivchar.rendererrecyclerviewadapter.DiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class NestedAdapter extends RendererRecyclerViewAdapter {

	private static final String TAG = NestedAdapter.class.getSimpleName();

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		final RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
		Log.d(TAG, "onCreateViewHolder: " + viewHolder.getClass().getSimpleName());
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position, @Nullable final List payloads) {
		super.onBindViewHolder(holder, position, payloads);
		Log.d(TAG, "onBindViewHolder: " + holder.getClass().getSimpleName());
	}

	@Override
	public void onViewRecycled(final RecyclerView.ViewHolder holder) {
		super.onViewRecycled(holder);
		Log.d(TAG, "onViewRecycled: " + holder.getClass().getSimpleName());
	}

	@Override
	public void setItems(@NonNull final List<? extends ItemModel> items, @NonNull final DiffCallback diffCallback) {
		Log.d(TAG, "start setItems: " + items);
		super.setItems(items, diffCallback);
		Log.d(TAG, "end setItems: " + items);
	}
}