package com.github.vivchar.example.pages.github.items;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.github.vivchar.example.pages.github.items.list.RecyclerViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import java.util.ArrayList;

/**
 * Created by Vivchar Vitaly on 20.10.17.
 */

public class ItemsDiffCallback extends DefaultDiffCallback<ViewModel> {

	private static final String TAG = ItemsDiffCallback.class.getSimpleName();

	@Override
	public boolean areItemsTheSame(@NonNull final ViewModel oldItem, @NonNull final ViewModel newItem) {
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
	public boolean areContentsTheSame(@NonNull final ViewModel oldItem, @NonNull final ViewModel newItem) {
		return super.areContentsTheSame(oldItem, newItem);
	}

	@Nullable
	@Override
	public Object getChangePayload(@NonNull final ViewModel oldItem, @NonNull final ViewModel newItem) {
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