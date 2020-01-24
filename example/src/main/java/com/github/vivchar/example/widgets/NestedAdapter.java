package com.github.vivchar.example.widgets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */

public class NestedAdapter extends RendererRecyclerViewAdapter {

	private static final String TAG = NestedAdapter.class.getSimpleName();

	public NestedAdapter() {
		super();
	}

	@NonNull
    @Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int typeIndex) {
		final ViewHolder viewHolder = super.onCreateViewHolder(parent, typeIndex);
		Log.d(TAG, "onCreateViewHolder: " + viewHolder.getClass().getSimpleName());
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @Nullable final List payloads) {
		Log.d(TAG, "onBindViewHolder: " + holder.getClass().getSimpleName());
		super.onBindViewHolder(holder, position, payloads);
	}

	@Override
	public void onViewRecycled(final ViewHolder holder) {
		Log.d(TAG, "onViewRecycled: " + holder.getClass().getSimpleName());
		super.onViewRecycled(holder);
	}
}