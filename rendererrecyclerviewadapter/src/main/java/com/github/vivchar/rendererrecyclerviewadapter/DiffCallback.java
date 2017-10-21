package com.github.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */
public abstract class DiffCallback <BM extends ItemModel> extends DiffUtil.Callback {

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