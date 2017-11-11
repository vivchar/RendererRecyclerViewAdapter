package com.github.vivchar.example.pages.github.items;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.vivchar.example.pages.github.items.list.RecyclerViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 20.10.17.
 */

public class ItemsDiffCallback extends DefaultDiffCallback<ItemModel> {

	private static final String TAG = ItemsDiffCallback.class.getSimpleName();

	@Override
	public boolean areItemsTheSame(@NonNull final ItemModel oldItem, @NonNull final ItemModel newItem) {
		/* vivchar: Ideally you should create a BaseItemModel with the getID method */
		if (oldItem instanceof RecyclerViewModel) {
			if (newItem instanceof RecyclerViewModel) {
				return ((RecyclerViewModel) oldItem).getID() == ((RecyclerViewModel) newItem).getID();
			} else {
				return false;
			}
		} else if (newItem instanceof RecyclerViewModel) {
			return false;
		}
		return super.areItemsTheSame(oldItem, newItem);
	}

	@Override
	public boolean areContentsTheSame(@NonNull final ItemModel oldItem, @NonNull final ItemModel newItem) {
		return super.areContentsTheSame(oldItem, newItem);
	}

	@Nullable
	@Override
	public Object getChangePayload(@NonNull final ItemModel oldItem, @NonNull final ItemModel newItem) {
		if (oldItem instanceof RecyclerViewModel) {
			if (newItem instanceof RecyclerViewModel) {
				/* vivchar: I just want to call the RecyclerViewRenderer.rebindView() method */
				final ArrayList<Long> payload = new ArrayList<>();
				payload.add(System.currentTimeMillis());
				Log.d(TAG, "composite payload: " + payload);
				return payload;
			}
		}
		final Object payload = super.getChangePayload(oldItem, newItem);
		Log.d(TAG, "default payload: " + payload);
		return payload;
	}
}