package com.github.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by Vivchar Vitaly on 1/9/17.
 */
@SuppressWarnings("unchecked")
public class RendererRecyclerViewAdapter extends RecyclerView.Adapter {

	@NonNull
	protected final ArrayList<ItemModel> mItems = new ArrayList<>();
	@NonNull
	protected final SparseArray<ViewRenderer> mRenderers = new SparseArray<>();
	@NonNull
	protected SparseArray<ViewState> mViewStates = new SparseArray<>();
	@NonNull
	protected final ArrayList<RecyclerView.ViewHolder> mBoundViewHolders = new ArrayList<>();

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		final ViewRenderer renderer = mRenderers.get(viewType);
		if (renderer != null) {
			return renderer.createViewHolder(parent);
		}

		throw new RuntimeException("Not supported Item View Type: " + viewType);
	}

	public void registerRenderer(@NonNull final ViewRenderer renderer) {
		final int type = renderer.getType();

		if (mRenderers.get(type) == null) {
			mRenderers.put(type, renderer);
		} else {
			throw new RuntimeException("ViewRenderer already exist with this type: " + type);
		}
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position, @Nullable final List payloads) {
		final ItemModel item = getItem(position);
		final ViewRenderer renderer = mRenderers.get(item.getType());
		if (payloads == null || payloads.isEmpty()) {
			/* Full bind */
			renderer.bindView(item, holder);
			restoreViewState(position, holder);
		} else {
			/* Partial bind */
			renderer.rebindView(item, holder, payloads);
		}
		mBoundViewHolders.add(holder);
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {}

	@Override
	public int getItemViewType(final int position) {
		final ItemModel item = getItem(position);
		return item.getType();
	}

	@Override
	public void onViewRecycled(final RecyclerView.ViewHolder holder) {
		super.onViewRecycled(holder);
		final int position = holder.getAdapterPosition();
		if (position != NO_POSITION) {
			final boolean hasChildren = holder instanceof CompositeViewHolder;
			if (hasChildren) {
				onChildrenViewsRecycled((CompositeViewHolder) holder);
			}
			saveViewState(position, holder);
		}
		mBoundViewHolders.remove(holder);
	}

	private void onChildrenViewsRecycled(@NonNull final CompositeViewHolder holder) {
		final RendererRecyclerViewAdapter nestedAdapter = holder.adapter;
		final ArrayList<RecyclerView.ViewHolder> boundViewHolders = nestedAdapter.getBoundViewHolders();
		Log.d("###", "onChildrenViewsRecycled size: " + boundViewHolders.size());
		for (final RecyclerView.ViewHolder viewHolder : boundViewHolders) {
			Log.d("###", "onViewRecycled size: " + boundViewHolders.size());
			nestedAdapter.onViewRecycled(viewHolder);
		}
	}

	@NonNull
	public <T extends ItemModel> T getItem(final int position) {
		return (T) mItems.get(position);
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	public void setItems(@NonNull final List<? extends ItemModel> items) {
		mItems.clear();
		mItems.addAll(items);
	}

	/**
	 * @param items        - your new items
	 * @param diffCallback - callback class used by DiffUtil while calculating the diff between two lists.
	 */
	public void setItems(@NonNull final List<? extends ItemModel> items, @NonNull final DiffCallback diffCallback) {
		diffCallback.setItems(mItems, items);

		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

		mItems.clear();
		mItems.addAll(items);

		diffResult.dispatchUpdatesTo(this);
	}

	@NonNull
	public ArrayList<RecyclerView.ViewHolder> getBoundViewHolders() {
		return new ArrayList<>(mBoundViewHolders);
	}

	@NonNull
	public SparseArray<ViewState> getViewStates() {
		return mViewStates;
	}

	public void setViewStates(@NonNull final SparseArray<ViewState> states) {
		mViewStates = states;
	}

	protected void saveViewState(final int position, @NonNull final RecyclerView.ViewHolder holder) {
		final ItemModel item = getItem(position);
		final ViewRenderer viewRenderer = mRenderers.get(item.getType());
		mViewStates.put(position, viewRenderer.createViewState(item, holder));
	}

	protected void restoreViewState(final int position, @NonNull final RecyclerView.ViewHolder holder) {
		final ViewState viewState = mViewStates.get(position);
		if (viewState != null) {
			viewState.restore(holder);
		}
	}
}