package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */
public abstract class DiffCallback <BM extends ViewModel> extends DiffUtil.Callback {

	protected final List<BM> mOldItems = new ArrayList<>();
	protected final List<BM> mNewItems = new ArrayList<>();

	public void setItems(@NonNull final List<BM> oldItems, @NonNull final List<BM> newItems) {
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
	public Object getChangePayload(@NonNull final BM oldItem, @NonNull final BM newItem) {
		return null;
	}
}