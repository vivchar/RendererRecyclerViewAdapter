package com.github.vivchar.example.widgets;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class NestedAdapter extends RendererRecyclerViewAdapter {

	private static final String TAG = NestedAdapter.class.getSimpleName();

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int typeIndex) {
		final RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, typeIndex);
		Log.d(TAG, "onCreateViewHolder: " + viewHolder.getClass().getSimpleName());
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position, @Nullable final List payloads) {
		Log.d(TAG, "onBindViewHolder: " + holder.getClass().getSimpleName());
		super.onBindViewHolder(holder, position, payloads);
	}

	@Override
	public void onViewRecycled(final RecyclerView.ViewHolder holder) {
		Log.d(TAG, "onViewRecycled: " + holder.getClass().getSimpleName());
		super.onViewRecycled(holder);
	}
}