package com.github.vivchar.example.pages.github.items;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.vivchar.example.pages.github.items.list.RecyclerViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 20.10.17.
 */

public class ItemsDiffCallback extends DefaultDiffCallback<ItemModel> {

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

	@Nullable
	@Override
	public List getChangePayload(@NonNull final ItemModel oldItem, @NonNull final ItemModel newItem) {
		if (oldItem instanceof RecyclerViewModel) {
			if (newItem instanceof RecyclerViewModel) {
				/* vivchar: I just want to call the RecyclerViewRenderer.rebindView() method */
				return new ArrayList();
			}
		}
		return super.getChangePayload(oldItem, newItem);
	}
}