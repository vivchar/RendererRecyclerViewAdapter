package com.example.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Vivchar Vitaly on 1/9/17.
 */
public
class RendererRecyclerViewAdapter
		extends RecyclerView.Adapter
{

	@NonNull
	private final ArrayList<ItemModel> mItems = new ArrayList<>();
	@NonNull
	private final ArrayList<ViewRenderer> mRenderers = new ArrayList<>();

	@Override
	public
	RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		for (final ViewRenderer renderer : mRenderers) {
			if (renderer.isSupportType(viewType)) {
				return renderer.createViewHolder(parent);
			}
		}
		throw new RuntimeException("Not supported Item View Type: " + viewType);
	}

	public
	void
	registerRenderer(@NonNull final ViewRenderer renderer) {
		mRenderers.add(renderer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public
	void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		final ItemModel item = getItem(position);
		for (final ViewRenderer renderer : mRenderers) {
			if (renderer.isSupportType(item.getType())) {
				renderer.bindView(item, holder);
				return;
			}
		}
		throw new RuntimeException("Not supported View Holder: " + holder);
	}

	@Override
	public
	int getItemViewType(final int position) {
		final ItemModel item = getItem(position);
		return item.getType();
	}

	@NonNull
	ItemModel
	getItem(final int position) {
		return mItems.get(position);
	}

	@Override
	public
	int getItemCount() {
		return mItems.size();
	}

	public
	void setItems(@NonNull final ArrayList<ItemModel> items) {
		mItems.clear();
		mItems.addAll(items);
	}
}