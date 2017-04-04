package com.github.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

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
	private final SparseArray<ViewRenderer> mRenderers = new SparseArray<>();

	@Override
	public
	RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		final ViewRenderer renderer = mRenderers.get(viewType);
		if (renderer != null) {
			return renderer.createViewHolder(parent);
		}

		throw new RuntimeException("Not supported Item View Type: " + viewType);
	}

	public
	void
	registerRenderer(@NonNull final ViewRenderer renderer) {
		final int type = renderer.getType();

		if (mRenderers.get(type) == null) {
			mRenderers.put(type, renderer);
		} else {
			throw new RuntimeException("ViewRenderer already exist with this type: " + type);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public
	void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position, final List payloads) {
		if (payloads.size() == 0) {
			onBindViewHolder(holder, position);
		} else {
			final ItemModel item = getItem(position);
			final ViewRenderer renderer = mRenderers.get(item.getType());

			if (renderer != null) {
				renderer.bindView(item, holder, payloads);
			} else {
				throw new UnsupportedViewHolderException(holder);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public
	void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		final ItemModel item = getItem(position);

		final ViewRenderer renderer = mRenderers.get(item.getType());
		if (renderer != null) {
			renderer.bindView(item, holder);
		} else {
			throw new UnsupportedViewHolderException(holder);
		}
	}

	@Override
	public
	int getItemViewType(final int position) {
		final ItemModel item = getItem(position);
		return item.getType();
	}

	@SuppressWarnings("unchecked")
	@NonNull
	public
	<T extends ItemModel> T
	getItem(final int position) {
		return (T) mItems.get(position);
	}

	@Override
	public
	int getItemCount() {
		return mItems.size();
	}

	public
	void setItems(@NonNull final List<? extends ItemModel> items) {
		mItems.clear();
		mItems.addAll(items);
	}

	/**
	 * @param items - your new items
	 * @param diffCallback - callback class used by DiffUtil while calculating the diff between two lists.
	 */
	@SuppressWarnings("unchecked")
	public
	void setItems(@NonNull final List<? extends ItemModel> items, @NonNull final DiffCallback diffCallback) {
		diffCallback.setItems(mItems, items);

		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

		mItems.clear();
		mItems.addAll(items);

		diffResult.dispatchUpdatesTo(this);
	}

	public abstract static
	class DiffCallback <BM extends ItemModel>
			extends DiffUtil.Callback
	{
		public abstract
		void setItems(@NonNull final List<BM> oldItems, @NonNull final List<BM> newItems);
	}

	private final static
	class UnsupportedViewHolderException
			extends RuntimeException
	{
		UnsupportedViewHolderException(final RecyclerView.ViewHolder holderName) {
			super("Not supported View Holder: " + holderName.getClass().getSimpleName());
		}
	}
}