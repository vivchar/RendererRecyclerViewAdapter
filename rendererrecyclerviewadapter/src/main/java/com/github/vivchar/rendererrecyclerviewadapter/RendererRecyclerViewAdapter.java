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
	protected final SparseArray<ViewState> mViewStates = new SparseArray<>();

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
		if (renderer != null) {
			if (payloads == null || payloads.isEmpty()) {
				/* Full bind */
				renderer.bindView(item, holder);
				restoreState(holder, position);
			} else {
				/* Partial bind */
				renderer.rebindView(item, holder, payloads);
			}
		} else {
			throw new UnsupportedViewHolderException(holder);
		}
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {}

	@Override
	public int getItemViewType(final int position) {
		final ItemModel item = getItem(position);
		return item.getType();
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

	public void clearViewStates() {
		mViewStates.clear();
	}

	@Override
	public void onViewRecycled(final RecyclerView.ViewHolder holder) {
		super.onViewRecycled(holder);
		final int position = holder.getAdapterPosition();
		if (position != NO_POSITION) {
			final ItemModel item = getItem(position);
			final ViewRenderer viewRenderer = mRenderers.get(item.getType());
			mViewStates.put(position, viewRenderer.createViewState(item, holder));
		}
	}

	protected void restoreState(@NonNull final RecyclerView.ViewHolder holder, final int position) {
		final ViewState viewState = mViewStates.get(position);
		if (viewState != null) {
			viewState.restore(holder);
		}
	}

	public abstract static class DiffCallback <BM extends ItemModel> extends DiffUtil.Callback {

		private final List<BM> mOldItems = new ArrayList<>();
		private final List<BM> mNewItems = new ArrayList<>();

		void setItems(@NonNull final List<BM> oldItems, @NonNull final List<BM> newItems) {
			mOldItems.clear();
			mOldItems.addAll(oldItems);

			mNewItems.clear();
			mNewItems.addAll(newItems);
		}

		@Override
		public int getOldListSize() {
			return mOldItems.size();
		}

		@Override
		public int getNewListSize() {
			return mNewItems.size();
		}

		@Override
		public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
			return areItemsTheSame(
					mOldItems.get(oldItemPosition),
					mNewItems.get(newItemPosition)
			);
		}

		public abstract boolean areItemsTheSame(@NonNull final BM oldItem, @NonNull final BM newItem);

		@Override
		public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
			return areContentsTheSame(
					mOldItems.get(oldItemPosition),
					mNewItems.get(newItemPosition)
			);
		}

		public abstract boolean areContentsTheSame(@NonNull final BM oldItem, @NonNull final BM newItem);

		@Nullable
		@Override
		public Object getChangePayload(final int oldItemPosition, final int newItemPosition) {
			final Object changePayload = getChangePayload(mOldItems.get(oldItemPosition), mNewItems.get(newItemPosition));
			if (changePayload == null) {
				return super.getChangePayload(oldItemPosition, newItemPosition);
			}
			return changePayload;
		}

		@Nullable
		public List getChangePayload(@NonNull final BM oldItem, @NonNull final BM newItem) {
			return null;
		}
	}

	private final static class UnsupportedViewHolderException extends RuntimeException {

		UnsupportedViewHolderException(@NonNull final RecyclerView.ViewHolder holderName) {
			super("Not supported View Holder: " + holderName.getClass().getSimpleName());
		}
	}
}